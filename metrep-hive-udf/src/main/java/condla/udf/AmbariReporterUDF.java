package condla.udf;

import java.io.IOException;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

import condla.reporter.MetricsReporter;
import condla.reporter.SimpleMetricsReporter;

@Description(name = "ambarimetricsreporter", value = "_FUNC_(string) - sends a value to an Ambari Metrics Collector")
public final class AmbariReporterUDF extends UDF {

	int response;
	MetricsReporter mr;

	public int evaluate(String ambariMetricsCollector, String metricName, String appId, String hostname, String value)
			throws IOException {
		mr = new SimpleMetricsReporter(ambariMetricsCollector, metricName, appId, hostname, Double.parseDouble(value));
		response = mr.post();
		return response;
	}

	public int evaluate(String ambariMetricsCollector, String metricName, String appId, String value)
			throws IOException {
		return evaluate(ambariMetricsCollector, metricName, appId, "", value);
	}

	public int evaluate() throws IOException {
		mr = new SimpleMetricsReporter("http://condla2.field.hortonworks.com:6188", "thishivemetric", "thishiveapp",
				"thishivehostname", 10.0);

		response = mr.post();
		return response;
	}

}
