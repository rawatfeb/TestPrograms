package multithreading;

import java.util.Iterator;
import java.util.Vector;

public class ProducerConsumerProblemTest {

	private static Vector data = new Vector();

	public static void main(String[] args) throws Exception {
		new Producer().start();
		new Consumer().start();
	}

	static class Consumer extends Thread {
		Consumer() {
			super("Consumer");
		}

		public void run() {
			for (;;) {
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				synchronized (data) {
					Iterator it = data.iterator();
					while (it.hasNext()) {
						System.out.println(it.next());
					}
				}
			}
		}
	}

	static class Producer extends Thread {
		Producer() {
			super("Producer");
		}

		public void run() {
			for (;;) {
				try {
					Thread.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				data.addElement(new Object());
				if (data.size() > 10)
					data.removeAllElements();
			}
		}
	}
}
