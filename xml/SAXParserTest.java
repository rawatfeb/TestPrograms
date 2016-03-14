package xml;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SAXParserTest {
	//private String file="theFile.xml";
	//private String file="LargeDoc-55MB.txt";
	//private String file="626855830.text.D-NEWSTEX-T001.1.rpx.xml";
	
	private String file="commentary_documents_aoc_full_20151001_080258_link.xml";  //Sai_Claim-Policy_Payload.xml
	
	//private String file="PHLEGXML_MAF_C_20150212_201015.xml";
//	private String file="w_ud2_dataroom-items-b-1424452876499.load";
	
	
	//626855830.text.D-NEWSTEX-T001.1.rpx.xml
	
	
public class SaxHandler extends DefaultHandler {
	
@Override
public void startDocument() throws SAXException {
	System.out.println("Start Document:");
	super.startDocument();
}
@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
	System.out.print("qName="+qName);//qName is equivalent to element name   and attributes.getLocalName(0) attribute name and getValue for value
	for (int i = 0; i < attributes.getLength(); i++) {
		System.out.print("  "+attributes.getLocalName(i)+"="+attributes.getValue(i)+"  ");
	}
	System.out.println("");
	super.startElement(uri, localName, qName, attributes);
	}
@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
	String val = new String(ch, start, length);//value between elements	
	System.out.println("value="+val);
	super.characters(ch, start, length);
	}

@Override
	public void endDocument() throws SAXException {
	System.out.println("End Document:");
		super.endDocument();
	}



	}

public static void main(String...args) throws Exception, Exception, Exception{
	SAXParserTest spt = new SAXParserTest();
	//spt.parseXmlFileSaxParser();
	spt.parseXmlFileXercesParser();
	//spt.validateXMLWithSchema();
}



private void validateXMLWithSchema() throws ParserConfigurationException, SAXException, IOException {
	Schema schema = null;

	try {
	  String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
	  SchemaFactory factory = SchemaFactory.newInstance(language);
	  String name="schema.xml";
	schema = factory.newSchema(new File(name));
	} catch (Exception e) {
	    e.printStackTrace();
	}
	SAXParserFactory spf = SAXParserFactory.newInstance();
	//spf.setSchema(schema);
	SAXParser parser = spf.newSAXParser();
	 InputStream    xmlInput  = new FileInputStream("theFile.xml");
	    DefaultHandler handler   = new SaxHandler();
	parser.parse(xmlInput, handler);
	
}

private  void parseXmlFileSaxParser() {
	SAXParserFactory factory = SAXParserFactory.newInstance();
	try {
		InputStream    xmlInput  = new FileInputStream(file);
	    SAXParser      saxParser = factory.newSAXParser();
	    DefaultHandler handler   = new SaxHandler();
	    saxParser.parse(xmlInput, handler);

	} catch (Throwable err) {
	    err.printStackTrace ();
	}
}
	
	
	private  void parseXmlFileXercesParser() {
		try {
			//InputStream    xmlInput  = new FileInputStream(file);
			org.apache.xerces.parsers.SAXParser saxParser = new org.apache.xerces.parsers.SAXParser();
		    DefaultHandler handler   = new SaxHandler();    //inner class  handler class  
		    saxParser.setContentHandler(handler);
			saxParser.parse(file);
			//saxParser.parse(arg0);
		   // saxParser.parse(xmlInput, handler);

		} catch (Throwable err) {
		    err.printStackTrace ();
		}
	
}
}
