package multithreading;
import java.util.Map;


public class ThreadTest {
public static void main(String ... args) throws InterruptedException{

	
	startAnonymousThread();
	Thread.currentThread().sleep(1000);
	System.out.println(Thread.currentThread().getName());
	Thread.dumpStack();
	Map<String, String> env = System.getenv();
	System.out.println(env);
}

private static void startAnonymousThread() {
	Thread thread = new Thread(){
	    public void run(){
	      System.out.println("new Thread is Running");
	      System.out.println(Thread.getAllStackTraces());
	      System.out.println(Thread.currentThread().getName());
	      Thread.dumpStack();
	    }
	  };
	 
 thread.start();	
}
}
