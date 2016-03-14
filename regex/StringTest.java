package regex;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class StringTest {

	public static void main(String... args) {

		//indexOfTest();
		//arrayMaxLengthTest();
		//	isEmptytest();
		//replaceTest();
		//	convertString2Server("ns9590");
		//		stringBuliderReplcaeTest();

		splitByColon();

	}

	private static void splitByColon() {
		String s ="multilne string"


+"line2 : value9"

+""

+"line: 3 \r\n"

+""

+"line....";

		String[] sr = s.split(":");
		//String[] sr = s.split(System.getProperty("line.separator"));

		for (int i = 0; i < sr.length; i++) {
			System.out.println(i+"  "+sr[i]);
		}

	}

	private static void stringBuliderReplcaeTest() {

		String poolCache = "[AGENTMANAGER, ATLASJMS_TITANJMS, CASTORJMS_POLLUXJMS, CSLOC.PROD, CSLOC.PROD.POOLA, CSLOC.PROD.POOLB, CSLOC.PROD.POOLC, Cannondale/Cannonford]";
		String parseable = poolCache.substring(1, poolCache.length() - 1);
		String[] pools = parseable.split(",");
		StringBuilder htmlBuilder = new StringBuilder();
		for (String pool : pools) {
			htmlBuilder.append("<OPTION value=\"" + pool.trim() + "\">" + pool + "</OPTION>");
		}
		htmlBuilder.append("</SELECT>");

		String requestPool = "CSLOC.PROD";
		System.out.println(htmlBuilder);
		System.out.println(htmlBuilder.indexOf("<OPTION value=\"" + requestPool + "\">"));
		System.out.println(("<OPTION value=\"" + requestPool + "\">").length());

		int index = htmlBuilder.indexOf("<OPTION value=\"" + requestPool + "\">");
		htmlBuilder.replace(index, index + ("<OPTION value=\"" + requestPool + "\">").length(),
				"<OPTION selected value=\"" + requestPool + "\">");

		System.out.println(htmlBuilder);

	}

	private static Set<String> convertString2Server(String serversString) {
		Set<String> serversSet = new HashSet<String>();
		String[] servers = serversString.split(",");
		System.out.println(servers.length);
		for (String server : servers) {
			serversSet.add(server);
		}
		System.out.println(serversSet);
		return serversSet;
	}

	private static void replaceTest() {
		String jdbcConnectionURL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=NO)(ADDRESS=(PROTOCOL=TCP)(HOST=nortriver-vip)(PORT=1521))(ADDRESS=(PROTOCOL=TCP)(HOST=nortcreak-vip)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=nop44a.westlan.com)))";

		jdbcConnectionURL.replace("vip", "vip.westlan.com");
		System.out.println(jdbcConnectionURL);

		jdbcConnectionURL = jdbcConnectionURL.replace("vip", "vip.westlan.com");
		System.out.println(jdbcConnectionURL);

		//replcae returns the new string

	}

	private static void isEmptytest() {

		String testString = null;
		String testString2 = "";
		String testString3 = "  ";
		String testString4 = " hello  ";
		String testString5 = "String";

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			if (testString != null && !testString.isEmpty() && !testString.trim().isEmpty()) {
			} else {
			}
			if (testString2 != null && !testString2.isEmpty() && !testString2.trim().isEmpty()) {
			} else {
			}
			if (testString3 != null && !testString3.isEmpty() && !testString3.trim().isEmpty()) {
			} else {
			}
			if (testString4 != null && !testString4.isEmpty() && !testString4.trim().isEmpty()) {
			} else {
			}
			if (testString5 != null && !testString5.isEmpty() && !testString5.trim().isEmpty()) {
			} else {
			}
		}

		long EndTime = System.currentTimeMillis();
		System.out.println("total Elaspsed time JAVA API:" + (EndTime - startTime) + "ms");

		startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			if (!StringUtils.isBlank(testString)) {
			} else {
			}
			if (!StringUtils.isBlank(testString2)) {
			} else {
			}
			if (!StringUtils.isBlank(testString3)) {
			} else {
			}
			if (!StringUtils.isBlank(testString4)) {
			} else {
			}
			if (!StringUtils.isBlank(testString5)) {
			} else {
			}
		}

		EndTime = System.currentTimeMillis();
		System.out.println("total Elaspsed time COMMONS API:" + (EndTime - startTime) + "ms");

		/*
		 * if(testString!=null && !testString.trim().isEmpty()){
		 * System.out.println("test string is not a null"); }else{
		 * System.out.println("String is null"); }
		 */

	}

	private static void arrayMaxLengthTest() {
		System.out.println(Integer.MAX_VALUE);
		long mb = ((2147483647L * 4L) / 1024L) / 1024L;
		System.out.println("MB=" + mb);
		System.out.println("GB=" + mb / 1024); // 7 GB max string size
		int array[] = new int[Integer.MAX_VALUE];
		System.out.println(array.length);
	}

	private static void indexOfTest() {
		String test = "hello:nib";

		System.out.println(test.indexOf(":nib"));
		System.out.println(test.substring(0, test.indexOf(":nib")));

	}

}
