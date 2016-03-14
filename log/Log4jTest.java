/*package log;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jTest {

	//logger name   "log4j.rootLogger","TRACE,stdout,MyFile")

	public static void main(String... args) {

		System.out.println("starting.....");
		//simpleTest();
		propertiesTest();

		System.out.println("end");
	}

	public static void propertiesTest() {
		
		//TRACE-> DEBUG -> INFO -> WARN -> ERROR -> FATAL 
		Logger logger = Logger.getLogger("dbConnectionUtility");
		
		logger.fatal("This is a FATAL message.");
		logger.error("This is an ERROR message.");
		logger.warn("This is a WARN message.");
		logger.info("This is an INFO message.");
		logger.debug("This is a DEBUG message.");
		logger.trace("This is a TRACE message.");
	}

	public static void simple_On_The_Fly_Test() {
		
		//set properties here
		Properties properties = new Properties();
		properties.setProperty("log4j.rootLogger", "TRACE,stdout,MyFile");
		properties.setProperty("log4j.rootCategory", "TRACE");

		properties.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
		properties.setProperty("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
		properties.setProperty("log4j.appender.stdout.layout.ConversionPattern",
				"%d{yyyy/MM/dd HH:mm:ss.SSS} [%5p] %t (%F) - %m%n");

		properties.setProperty("log4j.appender.MyFile", "org.apache.log4j.RollingFileAppender");
		properties.setProperty("log4j.appender.MyFile.File", "my_example.log");
		properties.setProperty("log4j.appender.MyFile.MaxFileSize", "100KB");
		properties.setProperty("log4j.appender.MyFile.MaxBackupIndex", "1");
		properties.setProperty("log4j.appender.MyFile.layout", "org.apache.log4j.PatternLayout");
		properties.setProperty("log4j.appender.MyFile.layout.ConversionPattern",
				"%d{yyyy/MM/dd HH:mm:ss.SSS} [%5p] %t (%F) - %m%n");

		PropertyConfigurator.configure(properties);

		Logger logger = Logger.getLogger("MyFile");

		logger.fatal("This is a FATAL message.");
		logger.error("This is an ERROR message.");
		logger.warn("This is a WARN message.");
		logger.info("This is an INFO message.");
		logger.debug("This is a DEBUG message.");
		logger.trace("This is a TRACE message.");
	}

}
*/