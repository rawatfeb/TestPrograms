package xml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.xml.sax.helpers.DefaultHandler;

public class MessageFormator3 {

	public static String getUniqueID(String message) {
		String id = "";

		try {
			System.out.println(message);

			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLEventReader eventReader = factory.createXMLEventReader(new StringReader(message));

			XPath xpath = XPathFactory.newInstance().newXPath();
			// XPath Query for showing all nodes value
			XPathExpression expr = xpath.compile("//ResponseControl/*/text()");
			Object result = expr.evaluate(new StringReader(message), XPathConstants.NODESET);
			NodeList nodes = (NodeList) result;
			for (int i = 0; i < nodes.getLength(); i++) {
				System.out.println(nodes.item(i).getNodeValue());
			}

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.getEventType() == XMLStreamConstants.CHARACTERS) {
					System.out.println(event.asCharacters());
				}
				if (event.getEventType() == XMLStreamConstants.START_ELEMENT) {
					StartElement startElement = event.asStartElement();
					System.out.print(startElement.getName().getLocalPart());
					System.out.print(" ");
					System.out.println(event.getLocation());
				}
			}
			if (message.contains("<PolicyNumber>")) {
				System.out.println("yes");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}

	public static void main(String[] args) {
		//String path="D:/Rawat/SOA/Test Cases/V3 IIB Notification BW Testing/Notifications/V3/PolicyNotification_AutoPolicy_Agent_type_Test.xml";
		String path="D:/Rawat/SOA/Test Cases/V3 IIB Notification BW Testing/Notifications/ID_414d51204d514e54414954333842202055d16f5620005c10.txt";
		parse(path);
	}

	public static void parse(String path) {
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			InputStream xmlInput = new FileInputStream(path);
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler handler = new SaxHandler();
			saxParser.parse(xmlInput, handler);
		} catch (Throwable err) {
			err.printStackTrace();
		}
	}

}
