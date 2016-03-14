package multithreading;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
 
public class PrepareProduction implements Runnable{
  private final BlockingQueue<String> queue;
 
  PrepareProduction(BlockingQueue<String> q) { queue = q; }
 
  public void run() {
    String thisLine;
    System.out.println("Start PrepareProduction");
    try {
       FileInputStream fin =  new FileInputStream("C:/tmp/bag.ser");
       BufferedReader input = new BufferedReader
           (new InputStreamReader(fin));
       while ((thisLine = input.readLine()) != null) {
         // Thread.sleep(100);
    	   queue.put(thisLine);
       }
       fin.close();
       input.close();
       // special marker for the consumer threads
       // to mark the EOF
       queue.put("*");
       queue.put("*");
       queue.put("*");
       queue.put("*");
       queue.put("*");
       queue.put("*");
       queue.put("*");
       queue.put("*");
       queue.put("*");
       //System.out.println(queue);
    }
    catch (Exception e) {
       e.printStackTrace();
    }
  }
}