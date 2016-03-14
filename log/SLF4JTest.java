/*package log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;





public class SLF4JTest {
public static void main(String...args) throws Exception{
	
	
	//BasicConfigurator.configure();
	//consoleOutputTest();
	//fileOutputTest();
	//fileOutputConfigByonFlyProp();
	
//	sendLogToKafka();
	
	
}

private static void sendLogToKafka() throws IOException {
	//kafka was not workin with slf4j use log4j
	//Logger log = LoggerFactory.getLogger(SLF4JTest.class);
	Logger log=Logger.getLogger(SLF4JTest.class);
	Properties props = new Properties(); 
	FileOutputStream fos = new FileOutputStream("kafaka.log",true);
	props.setProperty("log4j.rootLogger","INFO, stdout, KAFKA");
	props.setProperty("log4j.logger.kafka","INFO, stdout");
	props.setProperty("log4j.appender.stdout","org.apache.log4j.ConsoleAppender");
	props.setProperty("log4j.appender.stdout.layout","org.apache.log4j.PatternLayout");
	props.setProperty("log4j.appender.stdout.layout.ConversionPattern","%5p [%t] (%F:%L) - %m%n");
	
	props.setProperty("log4j.appender.KAFKA","kafka.producer.KafkaLog4jAppender");
	props.setProperty("log4j.appender.KAFKA.layout","org.apache.log4j.PatternLayout");
	props.setProperty("log4j.appender.KAFKA.layout.ConversionPattern","%-5p: %c - %m%n");
	props.setProperty("log4j.appender.KAFKA.BrokerList","localhost:9092");
	props.setProperty("log4j.appender.KAFKA.Topic","test");
	
	

		//	#log4j.appender.KAFKA.SerializerClass=kafka.log4j.AppenderStringEncoder
	
	PropertyConfigurator.configure(props);
	
	fos.close();
	for(int i=0;i<10;i++){
    log.info(i+" this message sent    via kafka");
    log.debug("debug");
    log.error("debug");
	}
	
}

private static void fileOutputConfigByonFlyProp() throws IOException {
	//Logger log = LoggerFactory.getLogger(SLF4JTest.class);
	Properties props = new Properties(); 
	FileOutputStream fos = new FileOutputStream("logTest.log",true);
	props.setProperty("log4j.rootLogger","INFO, file");
	props.setProperty("log4j.appender.file","org.apache.log4j.RollingFileAppender");
	props.setProperty("log4j.appender.file.File","C:\\Rawat\\MyWorkspace\\Novus14.7\\logTest.log");
	props.setProperty("log4j.appender.file.MaxFileSize","10MB");
	props.setProperty("log4j.appender.file.MaxBackupIndex","10");
	props.setProperty("log4j.appender.file.layout","org.apache.log4j.PatternLayout");
	props.setProperty("log4j.appender.file.layout.ConversionPattern","%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
	PropertyConfigurator.configure(props);
	
	fos.close();
	for(int i=0;i<10;i++){
  //  log.info(i+"  properties setted in");
	}
	
}

private static void fileOutputTest() throws FileNotFoundException, IOException {
	//Logger log = LoggerFactory.getLogger(SLF4JTest.class);
	//taken configuration from property file
	Properties props = new Properties(); 
	File file = new File("log4j property file path.properties");
	if(!file.exists()){new FileOutputStream(file);}
	new FileOutputStream("logTest.log",true);
	props.load(new FileInputStream(file));
	PropertyConfigurator.configure(props);
	for(int i=0;i<100;i++){
//    log.info("Hello World");
//    log.debug("DEBUG");
//    log.warn("Warning....");
//    log.error("ERROR ?????????????????");
	}
}




private static void consoleOutputTest() {
	//Logger log = LoggerFactory.getLogger(SLF4JTest.class);
//    log.info("Hello World");
//    log.debug("DEBUG");
//    log.warn("Warning....");
//    log.error("ERROR ?????????????????");
 //   log.error("Error");
	System.out.println("from meth1");
	
	
	//either use  BasicConfigurator.configure();   or use below
	props.setProperty("log4j.appender.stdout","org.apache.log4j.ConsoleAppender");
	props.setProperty("log4j.appender.stdout.Target","System.out");
	props.setProperty("log4j.appender.stdout.layout","org.apache.log4j.PatternLayout");
	props.setProperty("log4j.appender.stdout.layout.ConversionPattern","%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
	
	
	
	
	


}
}
*/