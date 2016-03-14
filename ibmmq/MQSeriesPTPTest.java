package ibmmq;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import com.ibm.jms.JMSMessage;
import com.ibm.jms.JMSTextMessage;
import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnection;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQQueueReceiver;
import com.ibm.mq.jms.MQQueueSender;
import com.ibm.mq.jms.MQQueueSession;

/**
 * SimplePTP: A minimal and simple testcase for Point-to-point messaging (1.02
 * style).
 *
 * Assumes that the queue is empty before being run.
 *
 * Does not make use of JNDI for ConnectionFactory and/or Destination
 * definitions.
 *
 * @author saket
 */
public class MQSeriesPTPTest {
	private static final boolean DEBUG = true;

	/**
	 * Main method
	 *
	 * @param args
	 * @throws JMSException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws JMSException, InterruptedException, Exception {

		//local_webSphere_test();

		//prism_client_issue();
		
	//	client_issue_search_client_wl();
		
		novusuk_issue();

	}
	
	
	
	private static void novusuk_issue() throws Exception {
		MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
		cf.setHostName("c578sydnsa.int.thomsonreuters.com");  //c578sydnsa.int.thomsonreuters.com  //c755bdensa.int.thomsonreuters.com
		cf.setPort(1414);
		cf.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
	//	cf.setTransportType(JMSC.MQJMS_CLIENT_NONJMS_MQ);
		cf.setQueueManager("UK1LOADJMS");  //UK1PRISMAPI01JMS
		MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection("mqm", "mqm");
		MQQueueSession session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		MQQueue queue = (MQQueue) session.createQueue("queue:///PUBLISHING.PROD.UK"); //PUBLISHING.PROD.UK  //AGENTMANAGER

		MQQueueSender sender = (MQQueueSender) session.createSender(queue);
		MQQueueReceiver receiver = (MQQueueReceiver) session.createReceiver(queue);
		Message msg = receiver.receive(500);
		
		System.out.println(sender.getQueue()+" "+receiver.getQueue()+" msg="+msg);
		receiver.close();
		session.close();
		connection.close();
	}
	
	private static void client_issue_search_client_wl() throws Exception {
		MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
		cf.setHostName("wonewoc.int.westgroup.com");  //santafe   //munch  //clientloadjms.int.westgroup.com
		cf.setPort(1414);
		cf.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
	//	cf.setTransportType(JMSC.MQJMS_CLIENT_NONJMS_MQ);
		cf.setQueueManager("SRCHEXCLT2JMS");  //SRCHEXCLT1JMS  //SRCHEXCLTJMS   //PITTSDALEJMS
		MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection("mqm", "mqm");
		MQQueueSession session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		MQQueue queue = (MQQueue) session.createQueue("queue:///SEARCH.CLIENT.WL"); //SEARCH.CLIENT.WL	  //SEARCH.CLIENT.1

		MQQueueSender sender = (MQQueueSender) session.createSender(queue);
		MQQueueReceiver receiver = (MQQueueReceiver) session.createReceiver(queue);
		System.out.println(sender.getQueue()+" "+receiver.getQueue());
		receiver.close();
		session.close();
		connection.close();
	}
	

	private static void prism_client_issue() throws Exception {
		MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
		cf.setHostName("volente.int.westgroup.com");//quitaque  //volente //perth
		cf.setPort(1414);
		cf.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
		//  cf.setTransportType(JMSC.MQJMS_CLIENT_NONJMS_MQ);
		cf.setQueueManager("VOLENTEJMS"); //  QUITAQUEJMS  //VOLENTEJMS
		MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection("mqm", "mqm");
		MQQueueSession session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		MQQueue queue = (MQQueue) session.createQueue("queue:///REPLICATION.RESULTHISTORY.ALERTROOM.B0.P0"); //queue:///UTILITY.NIB.1  //ReplicationControl  //REPLICATION.CLIENT.ALERT.WEST1.P1  //AGENTMANAGER

		//on quitaque host and QM QUITAQUEJMS  queue is there queue:///REPLICATION.RESULTHISTORY.ALERTROOM.B0.P0
		MQQueueSender sender = (MQQueueSender) session.createSender(queue);
		MQQueueReceiver receiver = (MQQueueReceiver) session.createReceiver(queue);
		System.out.println(sender.getQueue()+" "+receiver.getQueue());
		
		/*String msg = "Test Message";
		JMSTextMessage message = (JMSTextMessage) session.createTextMessage(msg);
		connection.start();
		sender.send(message);
		System.out.println("\nSent message: " + message);
		JMSMessage receivedMessage = (JMSMessage) receiver.receive(100);
		System.out.println("\nReceived message: " + receivedMessage);
		sender.close();*/
		receiver.close();
		session.close();
		connection.close();

	}

	private static void local_webSphere_test() throws Exception {

		System.out.println("start...");
		MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
		cf.setHostName("nibuk2-mq.int.westgroup.com");
		cf.setPort(1414);
		cf.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
		//  cf.setTransportType(JMSC.MQJMS_CLIENT_NONJMS_MQ);
		cf.setQueueManager("NIBJMS");
		MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection("mqm", "mqm");
		MQQueueSession session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		MQQueue queue = (MQQueue) session.createQueue("queue:///SEARCHENGINE.NIB"); //queue:///UTILITY.NIB.1
		MQQueueSender sender = (MQQueueSender) session.createSender(queue);
		MQQueueReceiver receiver = (MQQueueReceiver) session.createReceiver(queue);
		String msg = "<SearchEngine Source=\"U6025719-TPL-A\" More=\"true\" RequestId=\"0a1e909f14d46bb14783ab71d5e\" EventId=\"\" UserId=\"\" Version=\"1\" UseMore=\"true\" route=\"ibmjms://NIBJMS/SEARCHENGINE.NIB\"><SearchEngineRequest Version=\"70\" CollectionName=\"UK_SMG_CASES\" CollectionSetName=\"uk-cases-all\" IndexSetName=\"UK_SMG_CASES0002\" SearchType=\"SRCH\" StageId=\"458\" DebugNumber=\"8891\" DupeDocIDKey=\"md.doc.family.uuid\" DupeDocRemovalPref=\"dupPref\" RespIndex=\"0\" DebugData=\"8891\" SearchEngineRequestData=\"0\" NumIndexSets=\"1\"></SearchEngineRequest><ServerParmsQuery Query=\"startTime,markInterval\"></ServerParmsQuery></SearchEngine>";

		//JMSTextMessage message = (JMSTextMessage) session.createTextMessage(msg);

		//	<Utility Source=\"nibuk2-cbnv\" Product=\"WLUK\" RequestId=\"0a3a894514d43c018fa3695dde8\" RootId=\"i0a3a89450000014d43c018f770dad98a\" EventId=\"i0a3a89450000014d43c018f770dad98a\" Version=\"1\"><getDocsInRangeRequest searchGUID=\"i7f0000020000014d44bcafd3e1ea7055\" beginDoc=\"1\" endDoc=\"50\"></getDocsInRangeRequest><ServerParmsQuery Query=\"startTime,markInterval\"></ServerParmsQuery></Utility>
		// Start the connection
		BytesMessage message = session.createBytesMessage();
		byte[] buffer = msg.getBytes();
		message.clearBody();
		message.writeBytes(buffer);

		connection.start();
		sender.send(message);
		System.out.println("\\nSent message:\\n" + message);
		for (int i = 0; i < 10; i++) {
			JMSMessage receivedMessage = (JMSMessage) receiver.receive(100);
			boolean gotOne = true;
			try {
				System.out.println("\\nReceived message:\\n" + receivedMessage.toString() + " " + receivedMessage);
			} catch (Exception e) {
				gotOne = false;
				System.out.println(e.getMessage());
			}
			if (gotOne)
				break;

		}

		sender.close();
		receiver.close();
		session.close();
		connection.close();

	}

	public static void localhost_main(String[] args) {
		try {
			MQQueueConnectionFactory cf = new MQQueueConnectionFactory();

			// Config
			cf.setHostName("localhost"); // cf.setHostName("nibuk1-mq");
			cf.setPort(1414);
			// cf.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
			// cf.setTransportType(JMSC.MQJMS_CLIENT_NONJMS_MQ);
			cf.setQueueManager("MYQM"); //NIBJMS
			//  cf.setChannel("DEV.APP.SVRCONN");  //SYSTEM.DEF.SVRCONN

			/*
			 * Connection con = cf.createConnection("MUSR_MQADMIN","Swat@2015");
			 * Session ses = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
			 * Queue q = ses.createQueue("queue:///Q1"); MessageConsumer cons =
			 * ses.createConsumer(q); MessageProducer produ =
			 * ses.createProducer(q); Message
			 * msg=ses.createTextMessage("yor msg is here"); con.start();
			 * produ.send(msg); Message mrec = cons.receive(1000);
			 * System.out.println(mrec);
			 */

			System.out.println("****************");

			MQQueueConnection connection = (MQQueueConnection) cf.createQueueConnection("MUSR_MQADMIN", "Swat@2015");
			System.out.println(" got the mq connection from factory");
			MQQueueSession session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			System.out.println(" created session");
			MQQueue queue = (MQQueue) session.createQueue("queue:///REQUEST");
			System.out.println(" created queue " + queue.getQueueName());
			MQQueueSender sender = (MQQueueSender) session.createSender(queue);
			System.out.println(" created sender on queue");
			MQQueueReceiver receiver = (MQQueueReceiver) session.createReceiver(queue);
			System.out.println(" created receiver on the queue");

			long uniqueNumber = System.currentTimeMillis() % 1000;
			System.out.println("uniqueNumber" + uniqueNumber);
			JMSTextMessage message = (JMSTextMessage) session.createTextMessage("SimplePTP " + uniqueNumber + " "
					+ "My custom messgae is here");

			// Start the connection
			connection.start();

			sender.send(message);
			System.out.println("Sent message:\\n" + message);

			if (DEBUG)
				System.out.println("Sent message Text:\\n" + message.getText());

			JMSMessage receivedMessage = (JMSMessage) receiver.receive(10000);
			System.out.println("\\nReceived message:\\n" + receivedMessage);

			if (DEBUG)
				System.out.println("\\nReceived message Text:\\n" + receivedMessage.toString());

			sender.close();
			receiver.close();
			session.close();
			connection.close();

			System.out.println("\\nSUCCESS\\n");
		} catch (JMSException jmsex) {
			System.out.println(jmsex);
			System.out.println("\\nFAILURE\\n");
			jmsex.printStackTrace();

		} catch (Exception ex) {
			System.out.println(ex);
			System.out.println("\\nFAILURE\\n");
		}
	}
}
