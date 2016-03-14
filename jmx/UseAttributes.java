package jmx;

import java.lang.management.ManagementFactory;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public class UseAttributes {
	public static void main(String... args) throws MalformedObjectNameException, IntrospectionException, InstanceNotFoundException, ReflectionException, AttributeNotFoundException, MBeanException {

		MBeanServer mbs = JMXTest.mbs;
		int ir=JMXTest.i;
		System.out.println(ir);
		ir=30;
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		JMXTest.i=40;
		
		
		ObjectName name = new ObjectName("com.rwt.jmx:type=ApplicationCacheMBean");		
		System.out.println("mbs="+mbs+"  "+mbs.isRegistered(name));
		
	//	MBeanInfo minfo = mbs.getMBeanInfo(name);
		//Object v = mbs.getAttribute(name, "maxCacheSize");
		

		while (true) {
		//	System.out.println(v);
		}

	}
}
