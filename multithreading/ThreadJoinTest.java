package multithreading;

public class ThreadJoinTest {

	public static void main(String[] args) throws InterruptedException {
		long startTime = System.currentTimeMillis();
		WorkerThread workerThread = new WorkerThread();
		Thread t1 = new Thread(workerThread);
		t1.start();
		t1.setName("t1");
		Thread.sleep(1000);
		Thread t2 = new Thread(workerThread);
		t2.start();
		t2.setName("t2");
		try {
			// Waits at most millis milliseconds for this thread to die. // t1
			// thread will wait 4000 ms max to switchover to other thread. after
			// timeout it wo'nt kill the t1 just switch the control to other
			// thread or main.
			t1.join(4000);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis() - startTime);
	}
}

class WorkerThread implements Runnable {
	@Override
	public void run() {
		try {
			Thread.sleep(3000);
			System.out.println(Thread.currentThread().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}