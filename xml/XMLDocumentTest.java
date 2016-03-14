package xml;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLDocumentTest {

	

	public static void main(String...args) throws Exception{
		
		
		documentTest();
		
		
	}

	private static void documentTest() throws ParserConfigurationException, SAXException, IOException {
		 
		String XMLRequest = "<hello attr1=\"value\" ><text>hi</text></hello>";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		 DocumentBuilder builder = factory.newDocumentBuilder();
		 Document doc = builder.parse(new InputSource(new StringReader(XMLRequest)));
		 Element root = (Element) doc.getDocumentElement();
		 doc.createElement("elem");
		 System.out.println(root);
		 System.out.println(doc.CDATA_SECTION_NODE);		 
	}
	
	
	
	
}
