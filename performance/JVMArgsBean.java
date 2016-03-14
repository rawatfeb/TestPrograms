package performance;

public class JVMArgsBean {

	private String host = null;
	private String pid = null;
	private String name = null;
	private String JMXPort = null;

	public JVMArgsBean(String host, String pid, String name, String JMXPort) {
		this.host = host;
		this.pid = pid;
		this.name = name;
		this.JMXPort = JMXPort;
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj)
			return false;
		if (!(obj instanceof JVMArgsBean)) {
			return false;
		} else {
			if (((JVMArgsBean) obj).getName().equalsIgnoreCase(this.getName()))
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return host + " " + pid + " " + name + " " + JMXPort;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJMXPort() {
		return JMXPort;
	}

	public void setJMXPort(String jMXPort) {
		JMXPort = jMXPort;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}
