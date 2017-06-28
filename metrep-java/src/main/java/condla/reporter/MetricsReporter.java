package condla.reporter;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sdunkler on 6/26/17.
 */
public abstract class MetricsReporter {

	final static String PATH = "/ws/v1/timeline/metrics";
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	private String urlBase;
	private String metricName;
	private String appId;
	private String hostname;
	MultipartBody request;

	String url;
	String value;
	Map metricsWrapper;

	public MetricsReporter(String urlBase, String metricName, String appId, String hostname) {
		this.urlBase = urlBase;
		this.metricName = metricName;
		this.appId = appId;
		this.hostname = hostname;
		this.url = urlBase + PATH;
	}

	public String getUrlBase() {
		return urlBase;
	}

	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}

	public String getMetricName() {
		return metricName;
	}

	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppid(String appId) {
		this.appId = appId;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int post() throws IOException {

		Double value = calculateMetric();
		generateMetricsMap(value);
		url = urlBase + PATH;
		Gson gson = new Gson();

		String metricsJson = gson.toJson(metricsWrapper);

		OkHttpClient client = new OkHttpClient();

		RequestBody body = RequestBody.create(JSON, metricsJson);
		Request request = new Request.Builder().url(url).post(body).build();
		Response response = null;
		response = client.newCall(request).execute();
		return response.code();

	}

	public abstract Double calculateMetric();

	private void generateMetricsMap(Double value) {
		metricsWrapper = new HashMap();
		List metrics = new ArrayList();
		Map metric = new HashMap();
		Map valueMap = new HashMap();
		Instant tsNow = Instant.now();
		System.out.println(tsNow);
		Long timeStampMillis = tsNow.toEpochMilli();
		valueMap.put(timeStampMillis.toString(), value);
		metric.put("timestamp", timeStampMillis);
		metric.put("metricname", metricName);
		metric.put("appid", appId);
		metric.put("hostname", hostname);
		metric.put("starttime", timeStampMillis);
		metric.put("metrics", valueMap);
		metrics.add(metric);
		metricsWrapper.put("metrics", metrics);
	}
}