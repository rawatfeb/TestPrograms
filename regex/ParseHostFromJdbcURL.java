package regex;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ParseHostFromJdbcURL {

	public static void main(String... args) {

		parseHostFromJdbcURLTest();
		//	parseHostFromJdbcURL_using_URI_Test();
	}

	private static String parseHostFromJdbcURLTest() {
			String jdbcurl = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=NO)(ADDRESS=(PROTOCOL=TCP)(HOST=buchtel-vip)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=buckner-vip)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=nop34z.westlan.com)))";
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
		hostsString=hostsString.replace(",", " /");
		hostsString = hostsString.substring(1, hostsString.length() - 1);
		System.out.println(hostsString);
		return hostsString;
	}

	private static void parseHostFromJdbcURL_using_URI_Test() {
		String jdbcURL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=NO)(ADDRESS=(PROTOCOL=TCP)(HOST=buchtel-vip)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=buckner-vip)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=nop34z.westlan.com)))";

		System.out.println(jdbcURL);

		URI uri = URI.create(jdbcURL);

		System.out.println(uri.getHost());
	}

}
