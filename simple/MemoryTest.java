package simple;


import java.util.ArrayList;

public class MemoryTest {

	
	public static void main(String...args){

		
		
		StackSpaceTest();
		heapSpaceaTest();
			
		}

	private static void heapSpaceaTest() {
		MemoryTest mt = new MemoryTest();
		ArrayList<String> strlist=new ArrayList<String>();
		while(true){
			strlist.add("this is the String going to add in strlist multiple times");
		}
		
	}

	private static void StackSpaceTest() {
		while(true){
			
		    new Thread(new Runnable(){
		        public void run() {
		            try {
		                Thread.sleep(10000000);
		            } catch(InterruptedException e) { }        
		        }    
		    }).start();
		}
		
	}
	
}
