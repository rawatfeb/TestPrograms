package performance;

import java.util.HashMap;
import java.util.Map;

public class BeanTest {

	static Map<String,JVMArgsBean>  simpleBeanMap=new HashMap<String,JVMArgsBean>();
	static Map<String,JVMArgsBeanWithRes>  beanMapWithResourse=new HashMap<String,JVMArgsBeanWithRes>();
	
	
	public static void main(String...args){
		
		createSimpleBean();
		
		createSimpleBeanWithRes();
		
		retriveTest();
		
		
	}

	private static void retriveTest() {

System.out.println("Testing retrive performance of simple");
long startTimeofSimple = System.currentTimeMillis();
String pidKey="pid";
for (int i = 0; i < 50000; i++) {
	simpleBeanMap.get(pidKey+1);
	simpleBeanMap.get(pidKey+1);
	simpleBeanMap.get(pidKey+1);
	simpleBeanMap.get(pidKey+1);
}
System.out.println((System.currentTimeMillis()-startTimeofSimple)+" ms *************************");


System.out.println("Testing retrive performance of simpleWithRes");
long startTimeofSimpleWithRes = System.currentTimeMillis();
for (int i = 0; i < 50000; i++) {
	beanMapWithResourse.get(pidKey+1);
	beanMapWithResourse.get(pidKey+1);
	beanMapWithResourse.get(pidKey+1);
	beanMapWithResourse.get(pidKey+1);
}	
System.out.println((System.currentTimeMillis()-startTimeofSimpleWithRes)+" ms <<<<<<<<<<<<<<<<<<<<");	
	}

	private static void createSimpleBeanWithRes() {
		System.out.println("starting createSimpleBeanWithRes...");
		long startTime = System.currentTimeMillis();
		String host="host";
		String pid="pid";
		String name="name";
		String JMXPort="jmxPort";	
		for (int i = 0; i < 50000; i++) {
			JVMArgsBeanWithRes jVMArgsBeanWithRes = new JVMArgsBeanWithRes(host+i, pid+i, name+i, JMXPort+i);
			Map<String, String> resConn=new HashMap<String, String>();
			for (int j = 0; j < 500; j++) {
				resConn.put("Resource"+i, "opened"+i);
			}
			jVMArgsBeanWithRes.setResConn(resConn);
			beanMapWithResourse.put(pid+i, jVMArgsBeanWithRes);
		}
		System.out.println("Filled the simpleWithRes Map:"+(System.currentTimeMillis()-startTime)+" ms");			
	}

	private static void createSimpleBean() {
		
		System.out.println("starting createSimpleBean...");
		long startTime = System.currentTimeMillis();
		String host="host";
		String pid="pid";
		String name="name";
		String JMXPort="jmxPort";	
		for (int i = 0; i < 50000; i++) {
			JVMArgsBean jVMArgsBean = new JVMArgsBean(host+i, pid+i, name+i, JMXPort+i);
			simpleBeanMap.put(pid+i, jVMArgsBean);
		}
		System.out.println("Filled the Simple Map:"+(System.currentTimeMillis()-startTime)+" ms");	
		
	}
	
	
}
