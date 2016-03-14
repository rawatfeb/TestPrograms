package log;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2Test {

	public static void main(String...args){
		
		System.out.println("starting.....");
		int c=0;
		int max=10;
		while(c<max){propertiesTest();c++;}
		
		System.out.println("=========================================");

		c=0;
		while(c<max){propertiesTest();c++;}
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("end");
	}

	private static void propertiesTest() {
		//TRACE-> DEBUG -> INFO -> WARN -> ERROR -> FATAL 
		Logger logger = LogManager.getRootLogger();
		logger.fatal("This is a FATAL message.");
		logger.error("This is an ERROR message.");
		logger.warn("This is a WARN message.");
		logger.info("This is an INFO message.");
		logger.debug("This is a DEBUG message.");
		logger.trace("This is a TRACE message.");
		logger.trace("=============================");
			
	}
	
	
	
}
