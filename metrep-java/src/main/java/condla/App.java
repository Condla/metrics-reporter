package condla;

import java.io.IOException;
import java.util.Map;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import com.google.gson.Gson;

import condla.reporter.MetricsReporter;
import condla.reporter.RandomMetricsReporter;
import condla.reporter.SimpleMetricsReporter;
import condla.zookeeper.ZookeeperConnection;
/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
    	
    	String zookeeperQuorum = "condla0.field.hortonworks.com,condla1.field.hortonworks.com,condla2.field.hortonworks.com:2181";
        String ambariMetricsCollectorPort = "6188";
        String appId = "mynewapp";
    	String metricName = "mynewmetric";
        String hostname = "myhost";
        
        MetricsReporter mr = new RandomMetricsReporter(zookeeperQuorum,  ambariMetricsCollectorPort, appId, metricName, hostname);
        try {
			System.out.println(mr.post());
		} catch (IOException e) {
		
			e.printStackTrace();
		}
        
//		mr = new SimpleMetricsReporter(zookeeperQuorum, ambariMetricsCollectorPort, appId, metricName, hostname, 3.4);
//        try {
//			System.out.println(mr.post());
//		} catch (IOException e) {
//
//			e.printStackTrace();
//		}

    	
    	
    	
    	
    }
}