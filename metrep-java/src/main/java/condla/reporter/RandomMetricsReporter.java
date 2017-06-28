package condla.reporter;

import java.util.Random;

/**
 * Created by sdunkler on 6/26/17.
 */
public class RandomMetricsReporter extends MetricsReporter {


    public RandomMetricsReporter(String urlBase, String metricName, String appId, String hostname) {
        super(urlBase, metricName, appId, hostname);
    }

    @Override 
    public Double calculateMetric() {
        Random r = new Random();
        Double d = r.nextDouble();
        return d;
    }

}
