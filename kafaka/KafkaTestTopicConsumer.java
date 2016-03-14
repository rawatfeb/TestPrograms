package kafaka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

// incluse kafka and scala jar 
/**
 * 
 * 

 */
public class KafkaTestTopicConsumer extends Thread {
	private final ConsumerConnector consumer;
	private final String topic;

	public KafkaTestTopicConsumer(String topic) {
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig());
		this.topic = topic;
	}

	/**
	 * Creates the consumer config.
	 *
	 * @return the consumer config
	 */
	private static ConsumerConfig createConsumerConfig() {
		Properties props = new Properties();
		props.put("zookeeper.connect", "127.0.0.1:2181");
		props.put("group.id","group1");
		props.put("zookeeper.session.timeout.ms", "400");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");

		return new ConsumerConfig(props);

	}

	public void run() {
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(1));
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		KafkaStream<byte[], byte[]> stream = consumerMap.get(topic).get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		while (it.hasNext())
			System.out.println(new String(it.next().message()));
	}
	
	
	public static void main(String...args){
		String topic ="test";
			KafkaTestTopicConsumer test = new KafkaTestTopicConsumer(topic);
			test.run();
	}
}