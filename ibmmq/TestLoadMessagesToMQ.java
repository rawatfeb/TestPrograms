package ibmmq;


import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.CMQC;

public class TestLoadMessagesToMQ {

	//PROGRAM TO LOAD MESSAGES TO REG 38A REGION
	
	public int port = 2415;
	public static final String HOST_NAME = "FAAPCDD1438V.amer.zurich.dev";
	public static final String CHANNEL = "MQNTAIT38B.CLNT";
	public static final String Q_MANAGER = "MQNTAIT38B";
	public static final String USER_ID ="dpuser";  //uswtlug
	public static final String PASSWORD = "dpuser"; //Farmers1
	public static final String OUT_PUT_Q_NAME = "MDM.NOTIFICATION.XML";
	public static String MESSAGE_DIR = "D:/Rawat/SOA/Test Cases/IIB Notification BW Test Results For AIT2/TestCases";
	
	private MQQueueManager queueManager = null;
	private MQQueue queue;
	private MQMessage sendmsg;
	private MQPutMessageOptions pmo = new MQPutMessageOptions();

	public static void main(String[] args) {

		TestLoadMessagesToMQ mqMessageLoader = new TestLoadMessagesToMQ();
		try {
			mqMessageLoader.init();
			mqMessageLoader.intializeQManager();
			mqMessageLoader.putAllMessages();
			mqMessageLoader.closeQManager();
		} catch (IllegalArgumentException e) {
			System.exit(1);
		} catch (MQException e) {
			System.out.println(e);
			System.exit(1);
		} catch (Exception e) {
			System.out.println("URI Exception");
		}
	}

	public void init() {
		// Set MQ connection credentials to MQ Envorinment.
		MQEnvironment.hostname = HOST_NAME;
		MQEnvironment.channel = CHANNEL;
		MQEnvironment.port = port;
		MQEnvironment.userID = USER_ID;
		MQEnvironment.password = PASSWORD;
		// set transport properties.
		MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY,
				MQC.TRANSPORT_MQSERIES_CLIENT);
		try {
			// Set 64 bit MQ library path if there is any compatibility Issue.
			//Runtime.getRuntime()
					//.exec("cmd set MQ_JAVA_LIB_PATH=C:\\Program Files (x86)\\IBM\\WebSphere MQ\\java\\lib64;");

		} catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	private void intializeQManager() throws MQException {
		queueManager = new MQQueueManager(Q_MANAGER);
		int openOptions = CMQC.MQOO_OUTPUT + CMQC.MQOO_FAIL_IF_QUIESCING;
		// Access Remote Queue
		queue = queueManager.accessQueue(OUT_PUT_Q_NAME, openOptions, 
				null, // default q manager
				null, // no dynamic q name
				null); // no alternate user id

		System.out.println("MQ Queues connected");

		// Define a simple MQ message
		sendmsg = new MQMessage();
		formatMQMessage();
	}

	private void formatMQMessage() {
		sendmsg.format = CMQC.MQFMT_STRING;
		sendmsg.feedback = CMQC.MQFB_NONE;
		sendmsg.messageType = CMQC.MQMT_DATAGRAM;
		sendmsg.replyToQueueManagerName = Q_MANAGER;
	}


	private void closeQManager() throws MQException, IOException {
		queue.close();
		queueManager.disconnect();
		System.out.println("QManager Connection Closed and disconnected");
	}

	private String readFile(String path, Charset encoding) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();
		return new String(data, encoding);
	}

	private void putAllMessages() throws IOException, MQException {
		File messageDir = new File(MESSAGE_DIR);
		File[] listOfMessages = messageDir.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getAbsolutePath().endsWith(".xml");
			}
		});
		Charset charset = Charset.forName("UTF-8");
		int count = 0;
		for (; count < listOfMessages.length; count++) {
			String message = readFile(listOfMessages[count].getAbsolutePath(),
					charset);

			sendmsg.writeString(message);
			queue.put(sendmsg, pmo);
			sendmsg.clearMessage();
			formatMQMessage();
		}

		System.out.println("Successful written " + count++);
	}
}

