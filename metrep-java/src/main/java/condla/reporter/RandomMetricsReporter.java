package condla.reporter;

import java.util.Random;

/**
 * Created by sdunkler on 6/26/17.
 */
public class RandomMetricsReporter extends MetricsReporter {


    public RandomMetricsReporter(String zookeeperQuorum, String ambariMetricsCollectorPort, String appId, String metricName, String hostname) {
        super(zookeeperQuorum, ambariMetricsCollectorPort, appId, metricName, hostname);
    }

    @Override 
    public Double calculateMetric() {
        Random r = new Random();
        Double d = r.nextDouble();
        return d;
    }

}
