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

	public int evaluate(String zookeeperQuorum, String ambariMetricsCollectorPort, String appId, String metricName,
			String hostname, String value) throws IOException {
		mr = new SimpleMetricsReporter(zookeeperQuorum, ambariMetricsCollectorPort, appId, metricName, hostname,
				Double.parseDouble(value));
		response = mr.post();
		return response;
	}

	public int evaluate(String zookeeperQuorum, String ambariMetricsCollectorPort, String appId, String metricName,
			String value) throws IOException {
		return evaluate(zookeeperQuorum, ambariMetricsCollectorPort, appId, metricName, "", value);
	}

}
