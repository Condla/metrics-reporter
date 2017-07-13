package condla.reporter;

public class SimpleMetricsReporter extends MetricsReporter {

	Double value;

	public SimpleMetricsReporter(String zookeeperQuorum, String ambariMetricsCollectorPort, String appId, String metricName, String hostname, Double value) {
        super(zookeeperQuorum, ambariMetricsCollectorPort, appId, metricName, hostname);
		this.value = value;
	}

	@Override
	public Double calculateMetric() {
		return value;
	}
}