import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/*
import org.apache.xerces.jaxp.SAXParserImpl;*/


public class CountDocHandlerTest extends DefaultHandler{
	private static int document_cnt = 0;
	private static int view_cnt = 0;
	private static int vnode_cnt = 0;
	
	private boolean cancelFlag = false;
	private boolean langFound = false;
	
	private static String VNODE_TAG = "n-vnode";
    private static String defaultLang = "en";
	private String loadLang = "";
	
	private Set<String> guids = new HashSet<String>();
	
	public static void main(String...args) throws ParserConfigurationException, SAXException, IOException{
		System.out.println("starting....");
		String path="626855830.text.D-NEWSTEX-T001.1.rpx.xml";
		CountDocHandlerTest handler = new CountDocHandlerTest();
		 SAXParserFactory spf = SAXParserFactory.newInstance();
	        spf.setNamespaceAware(true);
	        spf.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
	        
	   //     if (!nonValidating)
	          spf.setFeature("http://xml.org/sax/features/validation", true);
	        
	    SAXParser saxParser = spf.newSAXParser();   			//javax.xml.parsers.SAXParser;
		saxParser.parse(new InputSource(path), handler);
		
		
		/*org.apache.xerces.parsers.SAXParser saxParser = new org.apache.xerces.parsers.SAXParser();    //xerces parser
	    saxParser.setContentHandler(handler);
		saxParser.parse(path);*/
		
		System.out.println("total documents counts:="+document_cnt);
		System.out.println("total view_cnt:="+view_cnt);
		System.out.println("total vnode_cnt:="+vnode_cnt);
		System.out.println("ends.");
	}
	

	/**
	 * Handler's event method which picks document count, guids and language from load file
	 * 
	 * @param namespaceURI String
	 * @param localname String
	 * @param qname String
	 * @param attrs Attributes
	 */
    public void startElement (String namespaceURI, String localname, String qname, Attributes attrs)
    throws SAXException
    {
        if (cancelFlag)
            throw new SAXException("Load Canceled");
    	if(qname.equalsIgnoreCase("n-load")){
    			String lang_value = attrs.getValue("lang");
				loadLang = (lang_value == null || "".equals(lang_value.trim())) ? defaultLang : lang_value.trim();
				langFound = true;
    	} else if(qname.equalsIgnoreCase("n-document")){
    		String guid = attrs.getValue("guid");
    		if (guid != null) {
    			guids.add(guid.trim());    		
    			document_cnt++;
    			System.out.println("found 1 document");
    		}
    		else {
    			throw new SAXException("No guid specified for a document in load file");
    		}
    	} else if(qname.equalsIgnoreCase("n-view-set")){
    		view_cnt++;
    	} else if(qname.equalsIgnoreCase(VNODE_TAG)){
    		vnode_cnt++;
    	}
    }

    /**
     * Sets the cancel flag to true
     * 
     * @param cancel
     */
    public void setCancelFlag(boolean cancel){
    	cancelFlag = cancel;
    }

    /**
     * Returns the number of documents
     * 
     * @return document count
     */
    public int getDocCount(){
    	return (document_cnt + view_cnt + vnode_cnt );
    }

    /**
     * Returns the number of views
     * 
     * @return view count
     */
    public int getViewCount(){
    	return view_cnt;
    }

    /**
     * Returns if language was found
     * 
     * @return language found
     */
    public boolean getLangFound(){
    	return langFound;
    }

    /**
     * Returns the language that was found
     * 
     * @return language
     */
    public String getLanguage(){
    	return loadLang;
    }

    /**
     * Returns the set of guids picked from load file(s)
     * 
     * @return guids set
     */
    public Set<String> getGuids(){
    	return guids;
    }
}
