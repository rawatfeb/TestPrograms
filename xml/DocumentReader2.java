package xml;

import java.io.ByteArrayInputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class DocumentReader2 {
	public static void main(String[] args) {

	}

	public static boolean matchNotification(String id, byte[] byteArray, String xPath) throws Exception {
		xPath = "MDMPublish/customerNotification/customer/legalName/suffix";
//		xPath = "MDMPublish/policyNotification/header/businessUnit";

		String[] xPathTokens = xPath.split("/");
		int xptl = xPathTokens.length;
		boolean[] xPathTokensPresentFlagArray = new boolean[xptl];

		String[] idToken = id.split(":");
		String notificationType = null;
		String identificationType = null;

		switch (idToken[0]) {
		case "PolicyNotificationBObj":
			notificationType = "policyNotification";
			identificationType = "policyNumber";
			break;
		case "PersonNotificationBObj":
			notificationType = "customerNotification";
			identificationType = "ecn";
			break;

		}

		XMLInputFactory factory = XMLInputFactory.newInstance();
		ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
		XMLEventReader eventReader = factory.createXMLEventReader(inputStream);

		boolean found = false;
		boolean toggle = false;

		int xPathTokensNumber = 0;
		while (eventReader.hasNext()) {
			XMLEvent event = (XMLEvent) eventReader.next();

			if (event.isStartElement()) {
				StartElement element = event.asStartElement();
//				System.out.print(" " + element.getName().getLocalPart());

				if (xPathTokensNumber < xptl
						&& element.getName().getLocalPart().equals(xPathTokens[xPathTokensNumber])) {
					xPathTokensPresentFlagArray[xPathTokensNumber] = true;
					xPathTokensNumber++;
				}

				if (!toggle) {
					if (element.getName().getLocalPart().equals(notificationType)) {
						toggle = true;
						continue;
					}
				}

				if (toggle) {
					if (element.getName().getLocalPart().equals(identificationType)) {
						event = (XMLEvent) eventReader.next();
						if (event.isCharacters()) {
							String data = event.asCharacters().getData();
							System.out.println(data);
							if (data.equals(idToken[1])) {
								found = true;
								System.out.println(">>>>>>>>Found<<<<<<<<<");
							}
						}
						continue;
					}
				}

			}
		}

		boolean finalFlag = toggle & found;
		for (boolean xPathTokensPresentFlag : xPathTokensPresentFlagArray) {
			finalFlag = finalFlag & xPathTokensPresentFlag;
		}

		return finalFlag;
	}

}
