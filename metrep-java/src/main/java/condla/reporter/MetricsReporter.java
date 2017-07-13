package condla.reporter;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import com.google.gson.Gson;

import condla.zookeeper.ZookeeperConnection;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by sdunkler on 6/26/17.
 */
public abstract class MetricsReporter {

	private static final String ID = "id";
	private static final String UTF_8 = "UTF-8";
	private static final String AMBARI_METRICS_CLUSTER_CONTROLLER_LEADER = "/ambari-metrics-cluster/CONTROLLER/LEADER";
	final static String PATH = "/ws/v1/timeline/metrics";
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	private String zkConnectionString;
	private String metricName;
	private String appId;
	private String hostname;
	private String metricsCollectorPort;

	String url;
	String value;
	Map metricsWrapper;

	public MetricsReporter(String zkConnectionString, String metricsCollectorPort, String appId, String metricName,
			String hostname) {
		this.zkConnectionString = zkConnectionString;
		this.metricsCollectorPort = metricsCollectorPort;
		this.metricName = metricName;
		this.appId = appId;
		this.hostname = hostname;

	}

	public String getZkConnectionString() {
		return zkConnectionString;
	}

	public void setZkConnectionString(String zkConnectionString) {
		this.zkConnectionString = zkConnectionString;
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

		url = obtainUrl();
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
		System.out.println(timeStampMillis);
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

	public String obtainUrl() {

		String leader = obtainMetricsCollectorLeader();
		return "http://" + leader + ":" + metricsCollectorPort + PATH;
	}

	public String obtainMetricsCollectorLeader() {
		ZookeeperConnection zkConnection = new ZookeeperConnection();
		String ambariMetricsCollectorLeader = null;
		try {
			ZooKeeper zk = zkConnection.connect(zkConnectionString);
			try {
				byte[] b = zk.getData(AMBARI_METRICS_CLUSTER_CONTROLLER_LEADER, null, null);
				String leaderData = new String(b, UTF_8);
				Map<String, Object> result = new Gson().fromJson(leaderData, Map.class);
				ambariMetricsCollectorLeader = (String) result.get(ID);
			} catch (KeeperException e) {
				e.printStackTrace();
			}
			zk.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return ambariMetricsCollectorLeader;
	}
}