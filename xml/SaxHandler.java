package xml;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandler extends DefaultHandler {
	private Stack<String> elementStack = new Stack<String>();
	private boolean isECN;

	public void startDocument() throws org.xml.sax.SAXException {
		System.out.println("Document Started. I am from startDocument");
	}

	public void endDocument() throws org.xml.sax.SAXException {
		System.out.println("Document Ends. I am from endDocument");
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws org.xml.sax.SAXException {
		this.elementStack.push(qName);

	}

	public void characters(char ch[], int start, int length) throws org.xml.sax.SAXException {
		String value = new String(ch, start, length).trim();
		if (!value.isEmpty()) {
			switch (currentElement()) {
			case "PolicyNotificationBObj":
				System.out.println("its PolicyNotificationBObj");
				break;
			case "PersonNotificationBObj":
				System.out.println("its PersonNotificationBObj");
				break;
			case "PolicyNumber":
				if ("PolicyNotificationBObj".equals(currentElementParent()))
					System.out.println("its PolicyNumber " + value);
				break;
			case "IdentificationValue":
				if ("TCRMPartyIdentificationBObj".equals(currentElementParent()))
					if ("ECN".equals(value)) {
						isECN = true;
						System.out.println("its IdentificationValue " + value);
					}
				// System.out.println("its IdentificationValue " + value);
				break;
			case "IdentificationNumber":
				if ("TCRMPartyIdentificationBObj".equals(currentElementParent()))
					if (isECN) {
						System.out.println("its IdentificationNumber " + value);
						isECN = false;
					}
				break;
			}
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		this.elementStack.pop();
	}

	private String currentElement() {
		return this.elementStack.peek();
	}

	private String currentElementParent() {
		if (this.elementStack.size() < 2)
			return null;
		return this.elementStack.get(this.elementStack.size() - 2);
	}
}
