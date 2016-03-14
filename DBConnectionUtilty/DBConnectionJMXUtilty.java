package DBConnectionUtilty;

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

public class DBConnectionJMXUtilty {

	private static final String CIR_JDBC_URL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=NO)(ADDRESS=(PROTOCOL=TCP)(HOST=dansfield.int.thomsonreuters.com)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=nvp32a.int.thomsonreuters.com)))";
	private static final String CIR_USER = "cirr";
	private static final String CIR_PASSWORD = "n0vu5r";
	private static final boolean DEBUG = true;
	static String tempServers = null;
	static String poolName = null;
	private static String likelyServicesHint;

	public static void main2(String... args) throws Exception {
		Set<String> openedConBuckets = new HashSet<String>();
		openedConBuckets.add("WLNV.RAC12.N1");
		openedConBuckets.add("BNP.RAC8.N2");
		getBucketConnectionToDB((Map<String, Integer>) openedConBuckets);
	}

	public static void main(String... args) throws Exception {
		if (DEBUG)
			System.out.println("DBConnectionJMXUtilty Started....");

		//args format pool name service  i.e.  CSLOC.PROD SharedCSLocClone 
		try {
			poolName = args[0];
			if (args.length > 1) {
				likelyServicesHint = args[1];
			}

		} catch (Exception e) {
			System.out
					.println("USES:  java -cp . JMX.PoolToDBUtility <POOL NAME(e.g CSLOC.PROD)> <likely services (SharedCSLocClone)>");
			throw e;
		}

		Connection cirCon = null;

		try {
			if (null == likelyServicesHint) {
				try {
					likelyServicesHint = poolName.split("\\.")[0].toLowerCase(); //args[2];
				} catch (Exception e) {
					System.out.println("");
				}
			}

			cirCon = getCirDBConnetion();
			Set<String> servers = getServerInPool(cirCon);
			Map<String, Integer> openedConBuckets = getDBConnectionToServices(servers, likelyServicesHint);
			System.out.println(openedConBuckets);
			Map<String, String> dbInPool = getBucketConnectionToDB(openedConBuckets);
			System.out.println("DB_In_Pool=" + dbInPool);
		} finally {
			if (null != cirCon)
				cirCon.close();
		}
	}


	private static Map<String, String> getBucketConnectionToDB(Map<String, Integer> openedConBuckets) throws Exception {
		DirContext dctx = getDirectoryContext();
		HashMap<String, String> dbInPool = new HashMap<String, String>();
		SearchControls controls = new SearchControls();
		controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		controls.setReturningAttributes(new String[] { "wgDatabaseHost", "wgJDBCConnectionURL", "wgjdbcconnurl2" });
		String searchDn = "cn=resources,CN=PROD,OU=NOVUS,O=WESTGROUP.COM";

		//	String resourceName = openedConBuckets.iterator().next();
		try {
			StringBuilder line = new StringBuilder();
			for (String resourceName : openedConBuckets.keySet()) {
				String filter = "(&(objectclass=wgDataStore)(wgresourcename=" + resourceName + "))";
				// Search Results
				NamingEnumeration<SearchResult> results = dctx.search(searchDn, filter, controls);
				Attribute DatabaseHost = null;
				if (results.hasMore()) {
					SearchResult searchResult = (SearchResult) results.next();
					Attributes attrs = searchResult.getAttributes();
					DatabaseHost = attrs.get("wgDatabaseHost");
					Attribute jdbcurl = attrs.get("wgjdbcconnurl2");
					if (jdbcurl == null) {
						jdbcurl = attrs.get("wgjdbcconnectionurl");
					}
					String hosts = extractHostFromJdbcURL((String) jdbcurl.get());
					dbInPool.put(resourceName, (String) DatabaseHost.get() + "  ( " + hosts + ")");
					line.append(resourceName + "," + (String) DatabaseHost.get() + "(" + hosts + ")" + "\r\n");
				} else {
					throw new Exception("No results found for " + resourceName + "  " + searchDn);
				}
				writeOutToFile(line.toString());
			}
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
		try (FileWriter fw = new FileWriter(new File(poolName+"_"+likelyServicesHint+"_"+"Pool_To_DB_Utility_Output.csv"))) {
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

	private static Map<String, Integer> getDBConnectionToServices(Set<String> servers, String likelyServicesHint)
			throws Exception {
		if (null == likelyServicesHint || "".equals(likelyServicesHint)) {
			likelyServicesHint = "all";
		}
		String serversString = convertServersSetToString(servers);
		String[] args = new String[] { serversString, likelyServicesHint, "all" };
		return JVMRuntimeClient.returning_main(args);
	}

	private static String convertServersSetToString(Set<String> servers) {
		String serversString = null;
		serversString = servers.toString();
		serversString = serversString.substring(1, serversString.length() - 1);
		return serversString;
	}

	private static Set<String> getServerInPool(Connection cirCon) throws IOException {
		HashSet<String> servers = new HashSet<String>();
		if (DEBUG) {
			servers.add("ns0818-13.westlan.com");
			/*
			 * ns0818-13 ns0820-13 ns0895-12 ns0897-12
			 */
		} else {
			if (null == cirCon) {
				//use lscir
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
			} else {
				// cir DB code here
				//SELECT S.SRVR_NAME as serverName FROM DEPLOY.SERVER S where S.SRVR_STATUS = 'A' AND S.SRVR_NAME IN (select SRVR_NAME from DEPLOY.SRVR_DOMAIN_POOL where POOL_ID=(select P.POOL_ID from DEPLOY.POOL_VALUES P where P.POOL_NAME='CSLOC.PROD') );
			}
		}
		///use lscir servers set list here until from cir db ready
		return servers;
	}

	private static Connection getCirDBConnetion() throws Exception {
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
