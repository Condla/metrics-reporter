package condla.reporter;

public class SimpleMetricsReporter extends MetricsReporter {

	Double value;

	public SimpleMetricsReporter(String urlBase, String metricName, String appId, String hostname, Double value) {
		super(urlBase, metricName, appId, hostname);
		this.value = value;
	}

	@Override
	public Double calculateMetric() {
		return value;
	}
}