package multithreading;

public class ThreadNameTest {

	public static void main(String[] args) {
		ThreadClass tc = new ThreadClass();
		Thread t1 = new Thread(tc);
		t1.setName("Hello");
		t1.start();
	}

}

class ThreadClass implements Runnable {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
	}

}