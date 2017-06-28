package condla;

import java.io.IOException;

import condla.reporter.MetricsReporter;
import condla.reporter.RandomMetricsReporter;
import condla.reporter.SimpleMetricsReporter;
/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        String ambariMetricsCollector = "http://condla2.field.hortonworks.com:6188";
        String metricName = "hivey_count";
        String appId = "myreporter";
        String hostname = "";
        MetricsReporter mr = new RandomMetricsReporter(ambariMetricsCollector, metricName, appId, hostname);
        try {
			System.out.println(mr.post());
		} catch (IOException e) {
		
			e.printStackTrace();
		}
        
		mr = new SimpleMetricsReporter(ambariMetricsCollector, metricName, appId, hostname, 3.4);
        try {
			System.out.println(mr.post());
		} catch (IOException e) {

			e.printStackTrace();
		}
    }
}