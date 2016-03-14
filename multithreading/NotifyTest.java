package multithreading;

public class NotifyTest extends Thread {

	@Override
	public void run() {
		synchronized (this) {
			try {
				this.currentThread().sleep(500);
				System.out.println("from user thread");
				notify();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}

	public static void main(String[] args) {
		System.out.println("main start");
		
		NotifyTest notifyTest = new NotifyTest();
		notifyTest.start();
		
		synchronized(notifyTest){
			try {
				System.out.println("will wait");
				notifyTest.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Done");
	}

}
