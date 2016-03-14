package utility;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.xml.parsers.DocumentBuilderFactory;


public class Utilities {

	public static String stack2string(Exception e) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return "------\r\n" + sw.toString() + "------\r\n";
		} catch (Exception e2) {
			return "bad stack2string";
		}
	}

	public static String resultSet2HtmlTable(ResultSet rs) {
		StringBuilder sb = new StringBuilder();
		sb.append("<P ALIGN='center'><TABLE BORDER=1>");
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			// table header
			sb.append("<TR>");
			for (int i = 0; i < columnCount; i++) {
				sb.append("<TH>" + rsmd.getColumnLabel(i + 1) + "</TH>");
			}
			sb.append("</TR>");
			// the data
			while (rs.next()) {
				sb.append("<TR>");
				for (int i = 0; i < columnCount; i++) {
					sb.append("<TD>" + rs.getString(i + 1) + "</TD>");
				}
				sb.append("</TR>");
			}
			sb.append("</TABLE></P>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static org.w3c.dom.Document resultSet2xmlDocument(ResultSet rs) {
		org.w3c.dom.Document doc = null;
		try {
			ResultSetMetaData resultSetMetaData = rs.getMetaData();
			 doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			org.w3c.dom.Element results = doc.createElement("Results");
			doc.appendChild(results);
			while (rs.next()) {
				org.w3c.dom.Element row = doc.createElement("Row");
				results.appendChild(row);
				for (int ii = 1; ii <= resultSetMetaData.getColumnCount(); ii++) {
					String columnName = resultSetMetaData.getColumnName(ii);
					Object value = rs.getObject(ii);
					org.w3c.dom.Element node = doc.createElement(columnName);
					node.appendChild(doc.createTextNode(value.toString()));
					row.appendChild(node);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
}
