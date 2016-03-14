package jmx;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import sun.jvmstat.monitor.HostIdentifier;
import sun.jvmstat.monitor.MonitorException;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.MonitoredVmUtil;
import sun.jvmstat.monitor.VmIdentifier;

public class Test {

	private static String resource="all";

	public static void main(String... args) throws Exception, URISyntaxException {

		getMonitoredHostTest();

	}

	private static void getMonitoredHostTest() throws MonitorException, URISyntaxException, Exception {
		String host = "ns0895-12.westlan.com";
		Set<?> jvms;
		MonitoredHost monitoredHost = MonitoredHost.getMonitoredHost(new HostIdentifier(host.trim()));
		jvms = monitoredHost.activeVms();
		System.out.println(jvms);
		System.out.println(monitoredHost.getHostIdentifier().getHost());

		String name = ((Integer)jvms.iterator().next()).toString();

		MonitoredVm mvm = monitoredHost.getMonitoredVm(new VmIdentifier(name));
		// use the command line as the display name
		name = MonitoredVmUtil.jvmArgs(mvm);
		Properties prop = getVMProperties(name);

		JMXServiceURL target = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"
				+ monitoredHost.getHostIdentifier().getHost() + ":"
				+ prop.getProperty("-Dcom.sun.management.jmxremote.port") + "/jmxrmi");
		JMXConnector connector = JMXConnectorFactory.connect(target);
		MBeanServerConnection mBeanServer = connector.getMBeanServerConnection();
		
		/**
		 * this is the part where you MUST know which MBean to get
		 * com.digitalscripter.search.statistics:name=
		 * requestStatistics,type=RequestStatistics YOURS WILL VARY!
		 */
		ObjectName mbean = new ObjectName("com.westgroup.novus.conncop:type=ConnCopStats");
		getResourcesFromMbean2(mBeanServer, mbean);
		connector.close();
		
		/*ObjectName mbean = new ObjectName("com.westgroup.novus.conncop:type=ConnCopStats");
		 * MBeanInfo info = mBeanServer.getMBeanInfo(mbean);
		MBeanAttributeInfo[] attributes = info.getAttributes();
		List<String> problemList = new ArrayList();	
		getResourcesFromMbean(mBeanServer, mbean, attributes);
		connector.close();
		*/

	}

	private static Map<String, Integer> getResourcesFromMbean2(MBeanServerConnection mBeanServer, ObjectName mbean) throws Exception {
		Map<String, Integer> resourceConnectionMap = new TreeMap<String, Integer>();
		String[] resourceNames;
		try {
			resourceNames = (String[]) mBeanServer.getAttribute(mbean, "ResourceNames");
		} catch (AttributeNotFoundException | InstanceNotFoundException | MBeanException | ReflectionException
				| IOException e1) {
			e1.printStackTrace();
			throw e1;
		}
		for (String rn : resourceNames) {
			System.out.print(" " + rn);
		try {
			if (resource.equalsIgnoreCase(rn) || resource.equalsIgnoreCase("all")) {
				int openedConnections = (Integer) mBeanServer.invoke(mbean, "findNumOpenConnections",
						new Object[] { rn }, new String[] { String.class.getName() });
				if (openedConnections > 0)
					//	if (DEBUG)System.out.print(rn+"="+val+",");
					resourceConnectionMap.put(rn, openedConnections);
			}
		} catch (Exception e) {
			resourceConnectionMap.put(rn + " " + e.getMessage(), 0);
		}
	}
		return resourceConnectionMap;
	}

	
	
	
	
	
	
	private static Map<String, Integer> getResourcesFromMbean(MBeanServerConnection mBeanServer, ObjectName mbean,
			MBeanAttributeInfo[] attributes) throws Exception {

		Map<String, Integer> resourceConnectionMap = new TreeMap<String, Integer>();
		for (MBeanAttributeInfo attr : attributes) {
			if (attr.getName() != null && attr.getName().equalsIgnoreCase("ResourceNames")) {
				String[] resourceNames;
				try {
					resourceNames = (String[]) mBeanServer.getAttribute(mbean, attr.getName());
				} catch (AttributeNotFoundException | InstanceNotFoundException | MBeanException | ReflectionException
						| IOException e1) {
					e1.printStackTrace();
					throw e1;
				}
				for (String rn : resourceNames) {
						System.out.print(" " + rn);
					try {
						if (resource.equalsIgnoreCase(rn) || resource.equalsIgnoreCase("all")) {
							int openedConnections = (Integer) mBeanServer.invoke(mbean, "findNumOpenConnections",
									new Object[] { rn }, new String[] { String.class.getName() });
							if (openedConnections > 0)
								//	if (DEBUG)System.out.print(rn+"="+val+",");
								resourceConnectionMap.put(rn, openedConnections);
						}
					} catch (Exception e) {
						resourceConnectionMap.put(rn + " " + e.getMessage(), 0);
						//problemList.add(rn+": "+e.getMessage());
					}
				}
			}
				System.out.print(".");
		}
		return resourceConnectionMap;
	
	}

	private static Properties getVMProperties(String name) {
		Properties prop = new Properties();
		String[] keyValue = name.split(" ");
		for (String kv : keyValue) {
			String[] kvs = kv.split("=");

			if (kvs[0] != null
					&& (kvs[0].equalsIgnoreCase("-Dcom.sun.management.jmxremote.port") || kvs[0]
							.equalsIgnoreCase("-Dvisualvm.display.name")))
				prop.setProperty(kvs[0], kvs[1]);
		}
		System.out.println(prop);
		return prop;
	}

}
