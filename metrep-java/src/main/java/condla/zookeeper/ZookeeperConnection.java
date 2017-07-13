package condla.zookeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperConnection {

	private ZooKeeper zk;
	final CountDownLatch connectedSignal = new CountDownLatch(1);

	public ZooKeeper connect(String host) throws IOException, InterruptedException {

		zk = new ZooKeeper(host, 5000, new Watcher() {

			public void process(WatchedEvent we) {

				if (we.getState() == KeeperState.SyncConnected) {
					connectedSignal.countDown();
				}
			}
		});

		connectedSignal.await();
		return zk;
	}

	public void close() throws InterruptedException {
		zk.close();
	}
}
