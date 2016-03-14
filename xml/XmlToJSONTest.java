package xml;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;



public class XmlToJSONTest {
	static String path = "";
	static String xmlFileName = "CleanupServ.NIB.1.03-09-2015";

	public static void main(String... args) throws Throwable {

		BufferedReader br = getFileReader();
		BufferedWriter fr = getFileWriter();
		generateJSON(br, fr);
		test();
		System.exit(0);
	}

	private static BufferedWriter getFileWriter() {
		File file = new File(xmlFileName + ".JSON");
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return writer;
	}

	private static BufferedReader getFileReader() {
		File file = new File(xmlFileName);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return br;
	}

	private static void test() throws Exception {
		String line = "<event><date>2015-03-09-00:04:14.419</date><key>QueryCleanupRequestQueue</key><machine>nibc1-load.amers1.cis.trcloud</machine><timestamp>1425859454419</timestamp><thread>MiddlewareReader Application</thread><product></product><novusid>1</novusid><application>CleanupServ</application><environment>NIB</environment><processtarget></processtarget><eventId>0ace496f14bfbd8a5d0ca8d60</eventId><requestId>0ace496f14bfbd8a5d0ca8d60</requestId><description></description><userid></userid><stageid>-1</stageid><collection></collection><loadstarttime></loadstarttime><eventlevel>debug</eventlevel><builder>CLEANUP</builder><cleaner>CLEANUP</cleaner><method>CleanupServImpl.QueryCleanupRequestQueue</method><text>Request to query cleanup request queue received</text><eventguid>I0ace496f0000014bfbd8a5d40f0d2bb0</eventguid></event>";
		line = XmlToJSON(line);
		System.out.println(line);
	}

	private static void writeToFile(String jSONLine, BufferedWriter writer)
			throws IOException {
		writer.write(jSONLine);
		writer.newLine();
	}

	private static void generateJSON(BufferedReader br, BufferedWriter fr)
			throws IOException, Exception {
		String line = null;
		try {
			StringBuilder multiline = new StringBuilder("");
			while (null != (line = br.readLine())) {
				System.out.println(line);
				String JSONLine = null;
				try {
					JSONLine = XmlToJSON(line);
					writeToFile(JSONLine, fr);
				} catch (Exception e) {
					multiline.append(line);
					try {
						JSONLine = XmlToJSON(multiline.toString());
						writeToFile(JSONLine, fr);
					} catch (Exception e1) {
						System.out.println("appending");
					}
					e.printStackTrace();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		fr.flush();
		fr.close();
		br.close();
	}

	private static String XmlToJSON(String line) throws JSONException {
		JSONObject xmlJSONObj = XML.toJSONObject(line);
		return xmlJSONObj.toString();
	}
}
