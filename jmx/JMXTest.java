package jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class JMXTest {
	
	public static MBeanServer mbs=ManagementFactory.getPlatformMBeanServer();
	
	public static int i=10;
	
	public static void main(String[] args) throws Exception {
		
		ApplicationCache cache = new ApplicationCache();
		ObjectName name = new ObjectName("com.rwt.jmx:type=ApplicationCacheMBean");		
		System.out.println("mbs="+mbs+"  "+mbs.isRegistered(name));
		mbs.registerMBean(cache, name);
		System.out.println("mbs="+mbs+"  "+mbs.isRegistered(name));
		imitateActivity(cache);
	}
	private static void imitateActivity(ApplicationCache cache) {
		while(true) {
			try {
				cache.cacheObject(new Object());
				Thread.sleep(1000);
				System.out.println(i);
			}
			catch(InterruptedException e) { }
		}
	}
}