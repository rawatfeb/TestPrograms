package xml;

import java.io.FileReader;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

public class StAXTest {
	public static void main(String[] args) {
		parseXMLFile();
	}

	private static void parseXMLFile() {
		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();

/*			XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(
					"C:\\Rawat\\Luna_Workspace\\DBConnectionUtility\\dailyResourceConnectionsReport.xml"));
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				System.out.println(event.asCharacters());
//				System.out.println(eventReader.getElementText());
			}*/
			
			
			XMLStreamReader streamReader = factory.createXMLStreamReader(new FileReader(
					"C:\\Rawat\\Luna_Workspace\\DBConnectionUtility\\dailyResourceConnectionsReport.xml"));
			
			while(streamReader.hasNext()){
				streamReader.next();
				System.out.println(streamReader.getName());
			}
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
