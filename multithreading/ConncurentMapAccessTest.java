package multithreading;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConncurentMapAccessTest implements Callable {
	private String threadType = "";
	private static Map<String, String> resConn = new HashMap<>();

	ConncurentMapAccessTest(String threadType) {
		this.threadType = threadType;
	}
	
	
	ConncurentMapAccessTest(String threadType,Map<String,String> resConn) {
		this.threadType = threadType;
		this.resConn = resConn;
	}

	public static void main(String... args) {
		System.out.println("Main Thread started");
		onlyGetOnlyPutThreadTest();
		
		
		//passSharedMapTest();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			System.out.println(resConn.size());
	}

	private void passSharedMapTest() {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.submit(new ConncurentMapAccessTest("Populate",resConn));
		executor.submit(new ConncurentMapAccessTest("Retrieve",resConn));
		executor.shutdown();
		
	}


	private static void onlyGetOnlyPutThreadTest() {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.submit(new ConncurentMapAccessTest("Populate"));
		executor.submit(new ConncurentMapAccessTest("Retrieve"));
		executor.shutdown();
		
	}

	@Override
	public Object call() throws Exception {
		System.out.println(Thread.currentThread().getName() + " Thread started "+resConn.size());

		if ("Populate".equalsIgnoreCase(threadType)) {
			for (int i = 0; i < 10000; i++) {
				resConn.put("Key" + i, "value" + i);
			}
		}
		if ("Retrieve".equalsIgnoreCase(threadType)) {
			for (int i = 0; i < 10000; i++) {
				resConn.get("Key" + i);
			}
		}
		System.out.println(Thread.currentThread().getName() + " Thread Completed  "+resConn.size());
		return null;
	}
}
