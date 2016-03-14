package jmx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class PoolToDBUtility {

	private static final String CIR_JDBC_URL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=NO)(ADDRESS=(PROTOCOL=TCP)(HOST=dansfield.int.thomsonreuters.com)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=nvp32a.int.thomsonreuters.com)))";
	private static final String CIR_USER = "cirr";
	private static final String CIR_PASSWORD = "n0vu5r";
	static String tempServers = null;
	static String poolName = null;
	static String likelyServicesHint;
	private static boolean DEBUG = false;

	public static void main2(String... args) throws Exception {
		System.out.println("dummy main");
		Set<String> openedConBuckets = new HashSet<String>();
		openedConBuckets.add("WLNV.RAC12.N1");
		openedConBuckets.add("BNP.RAC8.N2");
		Map<String, String> dbInPool = getBucketConnectionToDB(openedConBuckets);
		System.out.println(dbInPool);
	}

	public static void main(String... args) throws Exception {
		System.out.println("PoolToDBUtility Started....");

		for (String ar : args) {
			if (ar.equalsIgnoreCase("-v"))
				DEBUG = true;
		}

		//args format pool name servers  i.e.  CSLOC.PROD SharedCSLocClone 
		try {
			poolName = args[0];
			if (args.length > 1) {
				likelyServicesHint = args[1];
			}

		} catch (Exception e) {
			System.out.println("USES:  java -cp . JMX.PoolToDBUtility <POOL NAME(e.g CSLOC.PROD)> ");
			
			System.out.println("Output would be genearated in a csv file at the same location ");
			throw e;
		}

		Connection cirCon = null;

		try {
			if (null == likelyServicesHint) {
				likelyServicesHint = poolName.split("\\.")[0].toLowerCase(); //args[2];
			}

			cirCon = getCirConnetion();
			Set<String> servers = getServerInPool(cirCon);
			HashSet<String> openedConBuckets = getDBConnectionToServices(servers, likelyServicesHint);
			Map<String, String> dbInPool = getBucketConnectionToDB(openedConBuckets);
			System.out.println("DB_In_Pool=" + dbInPool);
		} finally {
			if (null != cirCon)
				cirCon.close();
		}
	}

	private static Map<String, String> getBucketConnectionToDB(Set<String> openedConBuckets) throws Exception {
		if (DEBUG)
			System.out.println("resolving resources to DB Host");
		DirContext dctx = getDirectoryContext();
		HashMap<String, String> dbInPool = new HashMap<String, String>();
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setReturningAttributes(new String[] { "cn", "wgDatabaseHost", "wgJDBCConnectionURL", "wgjdbcconnurl2" });
		String searchDn = "cn=resources,CN=PROD,OU=NOVUS,O=WESTGROUP.COM";

		//	String resourceName = openedConBuckets.iterator().next();
		try {
			StringBuilder line = new StringBuilder();
			for (String resourceName : openedConBuckets) {
				String filter = "(&(objectclass=wgDataStore)(wgresourcename=" + resourceName + ")(cn=*.r))";
				// Search Results
				NamingEnumeration<SearchResult> results = dctx.search(searchDn, filter, controls);

				StringBuilder prepareHosts = new StringBuilder();
				String databaseHost = null;
				while( results.hasMore()) {
					SearchResult searchResult = (SearchResult) results.next();
					Attributes attrs = searchResult.getAttributes();
					String cn = (String) attrs.get("cn").get();
					databaseHost = (String) attrs.get("wgDatabaseHost").get();
					Attribute jdbcurl = attrs.get("wgjdbcconnurl2");
					if (jdbcurl == null) {
						jdbcurl = attrs.get("wgjdbcconnectionurl");
					}
					String hosts = extractHostFromJdbcURL((String) jdbcurl.get());
					if (cn.contains("master.r") || cn.contains("Site-A.r")) {
						prepareHosts.append("," + databaseHost + "  ( " + hosts + ")");
						dbInPool.put(databaseHost, "  ( " + hosts + ")");
					} else if (cn.contains("slave.r") || cn.contains("Site-B.r")) {
						prepareHosts.append("," + databaseHost + "  ( " + hosts + ")");
						dbInPool.put(databaseHost, "  ( " + hosts + ")");
					}
					if (DEBUG)
						System.out.print(".");
				}
				line.append(resourceName + prepareHosts + "\r\n");
			}
			writeOutToFile(line.toString());
		} catch (Exception e) {
			//throw e;
			System.out.println(e.getMessage());
		} finally {
			if (null != dctx) {
				dctx.close();
			}
		}
		return dbInPool;

	}

	private static void writeOutToFile(String line) {
		if (DEBUG)
			System.out.println(" generating file");
		try (FileWriter fw = new FileWriter(new File(poolName + "_" + likelyServicesHint + "_"
				+ "Pool_To_DB_Utility_Output.csv"))) {
			fw.write(line);
			fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String extractHostFromJdbcURL(String jdbcurl) {
		//		String jdbcurl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=NO)(ADDRESS=(PROTOCOL=TCP)(HOST=buchtel-vip)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=buckner-vip)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=nop34z.westlan.com)))";
		String[] temp = jdbcurl.toLowerCase().split("host");
		List<String> hosts = new ArrayList<String>();
		String hostsString = null;
		for (String string : temp) {
			if (string.startsWith("=")) {
				String t = string.split("\\)")[0].substring(1);
				hosts.add(t);
			}
		}
		hostsString = hosts.toString();
		hostsString = hostsString.replace(",", " /");
		hostsString = hostsString.substring(1, hostsString.length() - 1);
		return hostsString;
	}

	private static DirContext getDirectoryContext() throws NamingException {
		Hashtable<String, String> envContext = new Hashtable<String, String>();
		envContext.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		envContext.put(Context.PROVIDER_URL, "ldap://ldap.westlan.com:389");
		DirContext dctx = new InitialDirContext(envContext);
		return dctx;
	}

	private static HashSet<String> getDBConnectionToServices(Set<String> servers, String likelyServicesHint)
			throws Exception {
		if (null == likelyServicesHint || "".equals(likelyServicesHint)) {
			likelyServicesHint = "all";
		}
		String serversString = null;
		serversString = servers.toString();
		serversString = serversString.substring(1, serversString.length() - 1);
		String[] args = new String[] { serversString, likelyServicesHint, "all" };
		return JVMRuntimeClient.returning_main(args);
	}

	private static Set<String> getServerInPool(Connection cirCon) throws IOException {
		HashSet<String> servers = new HashSet<String>();
		if (null == cirCon) {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec("/usr/local/bin/lscir --pool=" + poolName);
			try (InputStream is = process.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
				String line = null;
				while (null != (line = br.readLine())) {
					servers.add(line);
					System.out.println(line);
				}
			}
		}
		///use lscir servers set list here until from cir db ready
		return servers;
	}

	private static Connection getCirConnetion() throws Exception {
		Connection cirConn = null;
		/*
		 * try { Class.forName("oracle.jdbc.driver.OracleDriver"); } catch
		 * (ClassNotFoundException e) { e.printStackTrace(); System.out.println(
		 * "can not find oracle driver make sure oracle driver jar exist in your classpath"
		 * ); throw new RuntimeException(
		 * "can not find oracle driver make sure oracle driver jar exist in your classpath"
		 * ); } try { cirConn = DriverManager.getConnection(CIR_JDBC_URL,
		 * CIR_USER, CIR_PASSWORD); } catch (SQLException e) {
		 * e.printStackTrace(); System.out.println(
		 * "Not able get connection plase check JDBC URL or Credential(Username and Password)"
		 * ); throw new RuntimeException(
		 * "Not able get connection plase check JDBC URL or Credential(Username and Password)"
		 * ); }
		 */return cirConn;
	}
}
