package xml;
	import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

	public class XStreamTest {
		String s="inst";
		public static void main(String[] ar){
		String st="hello";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name","chris");
		map.put("island","faranga");

		// convert to XML
		XStream xStream = new XStream(new DomDriver());  //new XStream(new XtaxDriver());
		xStream.alias("CustomElementNameMAP", java.util.Map.class);
		String xml = xStream.toXML(map);
		
		
//		String xml = "<map><entry><string><name>Gaurav Rawat</name></string><string>chris</string></entry><entry><string>island</string><string>faranga</string></entry></map>";

		// from XML, convert back to map
		Map<String,Object> map2 = (Map<String,Object>) xStream.fromXML(xml);
		
		System.out.println("original map="+map);
		System.out.println("xml after xsteam conversion=\n"+xml);
		System.out.println("map back from xml="+map2);
		
		
/*		
		XStreamTest xstest = new XStreamTest();
		String objectXml = xStream.toXML(xstest);
		System.out.println(objectXml);
*/		
		
		
		}
		
	/*	
		private static XStream getXstreamObject() {
			XStream xstream = new XStream(); // DomDriver and StaxDriver instances also can be used with constructor
			xstream.alias("country", Country.class); // this will remove the Country class package name
			xstream.alias("state", State.class); // this will remove the State class package name
			xstream.useAttributeFor(Country.class, "isoCode"); // make the isoCode to attribute from element
			xstream.aliasField("code", Country.class, "isoCode"); // change 'isoCode' to code
			xstream.addImplicitCollection(Country.class, "states"); // don't want all states inside .
			return xstream;
		}*/
	}

