package kafaka;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

 
public class KafakaProducerTest {
    public static void main(String[] args) {
    //	BasicConfigurator.configure();
    	long events = Long.parseLong("10");
     //   long events = Long.parseLong(args[0]);
        Random rnd = new Random();
 
        Properties props = new Properties();
        props.put("metadata.broker.list", "localhost:9092");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "SimplePartitioner");
        props.put("request.required.acks", "1");
 
        ProducerConfig config = new ProducerConfig(props);
 
        Producer<String, String> producer = new Producer<String, String>(config);//The first is the type of the Partition key, the second the type of the message.
 
        for (long nEvents = 0; nEvents < events; nEvents++) { 
               long runtime = new Date().getTime();  
               String ip = "192.168.2." + rnd.nextInt(255); 
               String msg = runtime + ",www.example.edu," + ip; 
               KeyedMessage<String, String> data = new KeyedMessage<String, String>("test", ip, msg); //The “page_visits” is the Topic to write to  and we are passing the IP as the partition key
               producer.send(data);//page_visits
        }
        producer.close();
    }
}


//Before running this, make sure you have created the Topic page_visits. From the command line:
//bin/kafka-create-topic.sh --topic page_visits --replica 3 --zookeeper localhost:2181 --partition 5
//To confirm you have data, use the command line tool to see what was written:
//bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic page_visits --from-beginning