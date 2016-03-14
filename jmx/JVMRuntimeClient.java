package jmx;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import sun.jvmstat.monitor.HostIdentifier;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.MonitoredVmUtil;
import sun.jvmstat.monitor.VmIdentifier;

public class JVMRuntimeClient {
	public static HashSet<String> returning_main(String[] args) throws Exception {

		if (null == args) {
			System.out.println("Usage: java JVMRuntimeClient HOST PORT");
		}

		HashSet<String> openedConBuckets = new HashSet<String>();

		String hosts[] = args[0].split(",");

		for (String ho : hosts) {
			System.out.println(ho + " :");
			if (!ho.endsWith(".westlan.com")) {
				ho = ho + ".westlan.com";
			}
			MonitoredHost host=null;
			Set vms;
			try {
				host = MonitoredHost.getMonitoredHost(new HostIdentifier(ho.trim()));
				vms = host.activeVms();
			} catch (Exception e) {
				System.out.println(ho+"  "+e.getMessage());
				continue;
			}
			for (Object vmid : vms) {
				boolean exceptionOccured=false;
				if (vmid instanceof Integer) {

					int pid = ((Integer) vmid).intValue();
					String name = vmid.toString(); // default to pid if name not available
					boolean attachable = false;
					String address = null;
					try {

						MonitoredVm mvm = host.getMonitoredVm(new VmIdentifier(name));
						// use the command line as the display name
						name = MonitoredVmUtil.jvmArgs(mvm);
						Properties prop = new Properties();

						String[] keyValue = name.split(" ");

						for (String kv : keyValue) {
							String[] kvs = kv.split("=");
							if (kvs[0] != null
									&& (kvs[0].equalsIgnoreCase("-Dcom.sun.management.jmxremote.port") || kvs[0]
											.equalsIgnoreCase("-Dvisualvm.display.name")))
								prop.setProperty(kvs[0], kvs[1]);
						}

						mvm.detach();

						if (prop.getProperty("-Dcom.sun.management.jmxremote.port") == null)
							continue; //skip the vm which are not JMX enabled

						if (!prop.getProperty("-Dvisualvm.display.name").toLowerCase().startsWith(args[1].toLowerCase())) {
							continue; //skip the vm which do not match args 1 criteria of similarity
						}else{
							//System.out.println("82:"+prop.getProperty("-Dvisualvm.display.name"));
						}

						MBeanServerConnection remote = null;
						JMXConnector connector = null;

						JMXServiceURL target = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + ho + ":"
								+ prop.getProperty("-Dcom.sun.management.jmxremote.port") + "/jmxrmi");
						connector = JMXConnectorFactory.connect(target);
						remote = connector.getMBeanServerConnection();

						/**
						 * this is the part where you MUST know which MBean to
						 * get com.digitalscripter.search.statistics:name=
						 * requestStatistics,type=RequestStatistics YOURS WILL
						 * VARY!
						 */

						ObjectName bean = new ObjectName("com.westgroup.novus.conncop:type=ConnCopStats");

						MBeanInfo info = remote.getMBeanInfo(bean);

						MBeanAttributeInfo[] attributes = info.getAttributes();

						List<String> problemList = new ArrayList();

						for (MBeanAttributeInfo attr : attributes) {
							/*
							 * if(attr.getName()!=null &&
							 * attr.getName().equalsIgnoreCase("ClientName")){
							 * 
							 * 
							 * Perform operation only at a particular service
							 * level.
							 * 
							 * if(!args[1].equalsIgnoreCase((String)
							 * remote.getAttribute(bean,attr.getName())) &&
							 * !args[1].equalsIgnoreCase("all")) break;
							 * 
							 * 
							 * System.out.println(attr.getName()+
							 * " : "+remote.getAttribute
							 * (bean,attr.getName())+"("
							 * +prop.getProperty("-Dvisualvm.display.name"
							 * )+")"); }
							 */

							if (attr.getName() != null && attr.getName().equalsIgnoreCase("ClientName")) {

//								System.out.println(attr.getName() + " : " + remote.getAttribute(bean, attr.getName())+ "(" + prop.getProperty("-Dvisualvm.display.name") + ")");
							}
							if (attr.getName() != null && attr.getName().equalsIgnoreCase("ResourceNames")) {
								String[] resourceNames = (String[]) remote.getAttribute(bean, attr.getName());
								for (String rn : resourceNames) {

									try {

										if (args[2].equalsIgnoreCase(rn) || args[2].equalsIgnoreCase("all")) {
											int val = (Integer) remote.invoke(bean, "findNumOpenConnections",
													new Object[] { rn }, new String[] { String.class.getName() });
											if (val > 0)
												//	System.out.print(rn+"="+val+",");
												openedConBuckets.add(rn);
										}
									} catch (Exception e) {
										//problemList.add(rn+": "+e.getMessage());
									}
								}
							}
						}

						System.out.println("");

						for (String pr : problemList) {
							// System.err.println(pr);
						}

						connector.close();
					} catch (Exception e) {
						 exceptionOccured = true;
						//System.out.println(e.getMessage());
					}
				}
				if(!exceptionOccured)break;
			}

		//	System.out.println(args[1] + " services opened resources" + openedConBuckets);

		}
		System.out.println("Done with all code");
		return openedConBuckets;
	}
}