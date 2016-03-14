package xml;
/*
 * Copyright 2014 Thomson Global Resources. All Rights Reserved.
 * Proprietary and Confidential information of TGR.
 * Disclosure, Use or Reproduction without the authorization of TGR is
 * prohibited.
 * 
 

*//**
 * SaxTranslator implements both the DocumentHandler and ErrorHandler of IBM's
 * SAX parser.  This object serves as a translator between the SAX parser and
 * the transcription into reusable EventDoc object used by the ns.
 * All loaders are created and stored within this object, each
 * loader is responsible to register the tags it wishes to be notified when
 * they arrive through the parser.
 *
 * @author MDM
 * @version 1.0.0
 * @since 10.7.1999
 *
 * @see ErrorHandler
 *//*

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

import com.westgroup.novus.authority.DocumentVersionInfo;
import com.westgroup.novus.cci.CciException;
import com.westgroup.novus.cci.CciRecordNotFoundException;
import com.westgroup.novus.cci.Collection;
import com.westgroup.novus.cci.DocLocUpdateProcess;
import com.westgroup.novus.cci.LoadAttribute;
import com.westgroup.novus.cci.LoadElement;
import com.westgroup.novus.cci.LoadElementSet;
import com.westgroup.novus.cci.LoadUpdateProcess;
import com.westgroup.novus.commonutils.IntArrayList;
import com.westgroup.novus.commonutils.NovusException;
import com.westgroup.novus.commonutils.ParmReader;
import com.westgroup.novus.commonutils.micgroup.MICDocumentCounter;
import com.westgroup.novus.commonutils.micgroup.MICGroup;
import com.westgroup.novus.datamanagement.DataManagement;
import com.westgroup.novus.doclocator.GUIDUniqueness;
import com.westgroup.novus.load.cci.CciInfo;
import com.westgroup.novus.load.eventq.EventDoc;
import com.westgroup.novus.load.eventq.EventDocDefs;
import com.westgroup.novus.load.eventq.EventDocQueue;
import com.westgroup.novus.load.partialupdates.PartialUpdateDefs;
import com.westgroup.novus.log.Event;
import com.westgroup.novus.log.Log;
import com.westgroup.novus.slim.SLIMDataWarningException;

public class SaxTranslator extends DefaultHandler implements ContentHandler, LexicalHandler, ErrorHandler
 
{
    private String      collectionName = null;
    private int         stageId = -1;
    private static int  documentsProcessed = 0;
    private static int  documentsAdded = 0;
    private static int  documentsSkipped = 0;
    private static int  documentsDeleted = 0;
    private static int  documentsInternallyDeleted = 0;
    private static HashMap <String, MICDocumentCounter> micDocCounter = new HashMap<String, MICDocumentCounter>();
    private boolean  micLoad = false;
    private String micTargetCollection = null;
    private static Object docsProcessedSynchObject = new Object();
    private static Object docsAddedSynchObject = new Object();
    private static Object docsDeletedSynchObject = new Object();
    private int         dataErrorCount = 0;
    private int         builderAllTypes = 0;
    private int         builderType = 0;
    private int         builderCount = 0;
    private IntArrayList interestedBuilders = new IntArrayList(32);
	private LoaderParms loaderParms = null;    

    private String dataIdentifier = null;	// load identifier, displayed in pub gui
    private String notification = null;		// notification option
	private String language = null;			// language of the document
	private String country = null;			// country code
    private String loadcontenttype = LOAD_CONTENT_TYPE_ALL;     // loadcontenttype defaults to ALL

	private String[] reservedTerms = null;  // reserved terms to index
	
	private String control = "";	// value of control attribute
    private String      docGuid = ""; // used for exception reporting.
    private String loadGuid = null;
    private String alert = ""; // initialize with default value empty string
    private boolean     documentStarted = false; // track if events are within a novus document 
    private boolean     isDeleteDoc=false;  //check for control="DEL" in the n-document element
    private boolean     loadStarted = false; // track if the novus load start element was found
    private boolean     reindexFlag = false;
	private boolean	nDocumentEncountered = false;
	private boolean	nViewSetEncountered = false;
    private static boolean     cancelFlag =false;
	public static boolean	isSubjectiveView = false;
	public boolean 		inContentFlag = false;
	public boolean		contentInDocFlag = false;
	public boolean 		inMetadataFlag = false;
    public boolean      inMetaDocFlag = false;	
	private boolean		inDocBodyFlag = false;
    private IntArrayList       elementRulesStack = new IntArrayList(100);
    private EventDocQueue eventDocQueue = null;
    private EventDoc eventDoc = null;
    
    private Map requiredElements = null;  //the required elements map from the builders
    private Map actualElements = null;    //the actualElements map set in the startElement
    private int startTagElementCounter = 0;  //counter for n-docbody, n-blobref, or n-mmfile per doc
    private int metaTagElementCounter = 0; //count the n-metadata tags per doc
    private int metaDocTagElementCounter = 0;
    private int docTagElementCounter = 0;
    private int mmTagElementCounter = 0;
    
    private LoadProcessingRulesThreadSafe loadProcessingRules = null;

    private String mLoadFileName = null;
    
    // Error messages are taylored to the type of load: doc, toc, or relationship.  
    private static final String DOCACTION = "doc";
    private static final String TOCACTION = "toc";
    private static final String RELACTION = "rel";
    private static final String NIMSACTION = "nims";
	private static final String VIEWACTION = "view";        
    private static String loadActionType = DOCACTION;

    // Default values for document element and attributes.
    // Reset by values retrieved from the CCI.
    public static String LOAD_WRAPPER = "n-load";
    private static String LOAD_IDENTIFIER = "dataid";
    private static String LOAD_CLIENT_DEFINED_COLLECTION = "verifycollection";
    private static String LOAD_NOTIFY = "notify";    
    public static final String LOAD_LANGUAGE = "lang";
    private static final String LOAD_COUNTRY = "country";
    public static final String LOAD_CONTENT_TYPE = "loadcontenttype";    
    private static final String LOAD_TYPE = "loadtype";         // no longer used
    public static final String LOAD_REINDEX_ID_ATTR = "reindexid";
    private static final String ENGLISH = "en";
    
    public static final String LOAD_CONTENT_TYPE_ALL = "ALL";
    public static final String LOAD_CONTENT_TYPE_METADOC = "METADOC";
    public static final String LOAD_CONTENT_TYPE_SECTIONAL = "SECTIONAL";
    public static final String LOAD_CONTENT_TYPE_EXTRACTLOAD = "ExtractLoad";    // Added for MMS Extract Load
    
    public static final String ENCRYPTION_URL = "encryptionURL";
    public static final String DECRYPTION_URL = "decryptionURL";
    public static final String ENCRYPTED_CONTENT = "encryptedcontent";
    public static final String ENCRYPTION_TOKEN = "encryptiontoken";

    public static String START_DOCUMENT_ELEMENT = "n-document";
    public static String DOCUMENT_IDENTIFIER = "guid";
    public static String DOCUMENT_CONTROL = "control";
    public static String DOCUMENT_ADD = "ADD";
    public static String DOCUMENT_DELETE = "DEL";
    private String DOCUMENT_DELETEBRANCH = "DELBRANCH";
	private String DOCUMENT_REINDEX = "REINDEX";
	// n-document - alert attribute
	public static String DOCUMENT_ALERT = "alert";
	private String DOCUMENT_ALERT_INHERIT = "INHERIT";
	private String DOCUMENT_ALERT_NEW = "NEW";
	private String DOCUMENT_ALERT_HIDE = "HIDE";
//	private String SUBJECTIVE_WRAPPER = "n-view-define";
	public static String START_SUBJECTIVE_VIEW = "n-view-set";
	private String VIEW_NAME = "view-name";
	private String VIEW_CONTROL = "control";
	private String PARTIAL_UPD = "PARTIAL";
	private String VIEW_TARGET_COLLECTION ="target-collection";
	
	public static String TAG_BLOB_REFERENCE_ELEMENT = "n-blobref";
    public static String TAG_MMFILE_ELEMENT = "n-mmfile";
    
    // attributes related to collection family loading
	public static final String DOCUMENT_VERSION = "version";
	private static int collID = 0;
	private static String rel2Prtnr = "";
    private static String familyName = "";
    private String[] guids = new String[1];
    private static Map<Integer, String> mCFCollections = null;
    private boolean bVersionInvalid = false;
    
    private static boolean elementsIdentified = false;
    
    private boolean isLowerVersionPurge = false;
    private static String LOWER_VERSION_PURGE = "lowerVersionPurge";
    
    public static final String LOAD_SOURCE_PURGE = "PURGE";
    
    private boolean isOutsideFamily = false;
    private  String firstSubjectiveViewTargetCollection = null;
    
    *//**
     * Constructor that will create the loader objects and initialize
     * a static tag to loader mapping for each data identifier for 
     * use by all subsequent loads of the same type of data.
     * 
     * @param lp LoaderParms object.
     * @param eventDocQueue	object that manages the the EventDocs.
     * @param reqElems map containing elements that are required to be present
     * 				   in the load file.
     * @throws LoadErrorException
     *//*
    public SaxTranslator(LoaderParms lp, EventDocQueue eventDocQueue, Map reqElems)
    throws LoadErrorException
    {
        this.init(lp, eventDocQueue, reqElems);
    }
    
    public SaxTranslator(LoaderParms lp, EventDocQueue eventDocQueue, Map reqElems, LoadProcessingRulesThreadSafe loadProcessingRules  )
    throws LoadErrorException
    {
        this.loadProcessingRules = loadProcessingRules;
 
    	this.init(lp, eventDocQueue, reqElems);
    }
	*//**
	 * Initializes all the class attributes within the SaxTranslator.
	 * <p>
	 * Pulls the values from the Load_Update_Process in CCI.
	 * </p>
	 * 
     * @param lp LoaderParms object.
	 * @param eventDocQueue	object that manages the the EventDocs.
	 * @param reqElems map containing elements that are requried to be present
	 * 				   in the load file.
	 * @throws LoadErrorException
	 *//*
    public void init(LoaderParms lp, EventDocQueue eventDocQueue, Map reqElems)
    throws LoadErrorException
    {
        // Set reference to EventDocQueue used to allocate EventDoc objects
        this.eventDocQueue = eventDocQueue;

		loaderParms = lp;
        stageId = lp.getStageId();
        collectionName = lp.getCollectionName();
        
        micLoad = false;
        if (lp.isMicLoad()) {
        	micLoad = true;
        	micTargetCollection = lp.getMicTargetCollection();
        }  
        //set the requiredElements map
        this.requiredElements = reqElems;
            
        dataErrorCount = 0;
        dataIdentifier = null;
        notification = null;
        docGuid = "";
        documentStarted = false;
        isDeleteDoc = false;
        loadStarted = false;
        cancelFlag = false;
        reindexFlag = false;
        language = null;
        country = null;
		SaxTranslator.isSubjectiveView = false;
		nDocumentEncountered = false;
		nViewSetEncountered = false;
        
        // Get the count of builders
        builderAllTypes = loadProcessingRules.getBuilderCount(EventDocDefs.BUILDER_TYPE_ALL);
        
        interestedBuilders.clear();
        double tmpAllBuilders = Math.pow(2, (builderAllTypes - 1));
        for (int builderType = 1; builderType <= tmpAllBuilders; builderType *= 2)
        {
            //if((LoadProcessingRules.LOADING_BUILDER_MASK & builderType) > 0)
            if((loadProcessingRules.getLOADING_BUILDER_MASK() & builderType) > 0)
            {
                interestedBuilders.add(builderType);
            }
        }
        builderCount = interestedBuilders.size();
        
         Get the elements for this collection and their    
         attributes for use in SAX events.                 
        if(!elementsIdentified){
	        try {        	
	            //LoadUpdateProcess lup =LoadUpdateProcess.retrieve(collectionName);
	            LoadUpdateProcess lup = CciInfo.getLoadUpdateProcessInfo(collectionName).getLoadUpdateProcessObject();            
	            //LoadElementSet xmlElementSet = LoadElementSet.retrieve(lup.getSetName());
	            LoadElementSet xmlElementSet = CciInfo.getLoadElementSetInfo(lup.getSetName()).getLoadElementSetObject();
	            LoadElement xmlElements[] = xmlElementSet.getLoadElements();
	            for(int i = 0; i < xmlElements.length; i++)
	            {
	                // START_DOC and START_NODE are valid load actions in CCI. 
	                if(xmlElements[i].getLoadAction().equalsIgnoreCase("START_LOAD"))
	                {
	                    LOAD_WRAPPER = xmlElements[i].getElementName();
	                    LoadAttribute attributes[] = xmlElements[i].getLoadAttributes();
	                    for(int j=0 ; j < attributes.length; j++)
	                    {
	                        if(attributes[j].getContentType().equalsIgnoreCase("LOAD_ID"))
	                        {
	                            LOAD_IDENTIFIER = attributes[j].getAttributeName();
	                        }
	                        else if(attributes[j].getContentType().equalsIgnoreCase("LOAD_VERIFY_COLLECTION"))
	                        {
	                            LOAD_CLIENT_DEFINED_COLLECTION = attributes[j].getAttributeName();
	                        }
	                        else if(attributes[j].getContentType().equalsIgnoreCase("LOAD_NOTIFY"))
	                        {
	                            LOAD_NOTIFY = attributes[j].getAttributeName();
	                        }
	                    }
	                }
	
	                // Loads will have either documents, nodes, NIMS or relationships, not any combination.
	                // So the same element name and attribute name variables can be used.
	                else if(xmlElements[i].getLoadAction().equalsIgnoreCase("START_DOC"))
	                {
	                    START_DOCUMENT_ELEMENT = xmlElements[i].getElementName();
	                    LoadAttribute attributes[] = xmlElements[i].getLoadAttributes();
	                    for(int j=0 ; j < attributes.length; j++)
	                    {
	                        if(attributes[j].getContentType().equalsIgnoreCase("DOC_ID"))
	                        {
	                            DOCUMENT_IDENTIFIER = attributes[j].getAttributeName();
	                        }
	                        else if(attributes[j].getContentType().equalsIgnoreCase("DOC_CNTRL"))
	                        {
	                            DOCUMENT_CONTROL = attributes[j].getAttributeName();
	                        }
	                        else if(attributes[j].getContentType().equalsIgnoreCase("DOC_ALERT"))
	                        {
	                        	DOCUMENT_ALERT = attributes[j].getAttributeName();
	                        }
	                    }
	                    loadActionType = DOCACTION;
	                }
	                else if(xmlElements[i].getLoadAction().equalsIgnoreCase("START_NODE"))
	                {
	                    START_DOCUMENT_ELEMENT = xmlElements[i].getElementName();
	                    LoadAttribute attributes[] = xmlElements[i].getLoadAttributes();
	                    for(int j=0 ; j < attributes.length; j++)
	                    {
	                        if(attributes[j].getContentType().equalsIgnoreCase("NODE_ID"))
	                        {
	                            DOCUMENT_IDENTIFIER = attributes[j].getAttributeName();
	                        }
	                        else if(attributes[j].getContentType().equalsIgnoreCase("NODE_CNTRL"))
	                        {
	                            DOCUMENT_CONTROL = attributes[j].getAttributeName();
	                        }
	                    }
	                    loadActionType = TOCACTION;
	                }
	                else if(xmlElements[i].getLoadAction().equalsIgnoreCase("START_REL"))
	                {
	                    START_DOCUMENT_ELEMENT = xmlElements[i].getElementName();
	                    LoadAttribute attributes[] = xmlElements[i].getLoadAttributes();
	                    for(int j=0 ; j < attributes.length; j++)
	                    {
	                        if(attributes[j].getContentType().equalsIgnoreCase("REL_ID"))
	                        {
	                            DOCUMENT_IDENTIFIER = attributes[j].getAttributeName();
	                        }
	                        else if(attributes[j].getContentType().equalsIgnoreCase("REL_CNTRL"))
	                        {
	                            DOCUMENT_CONTROL = attributes[j].getAttributeName();
	                        }
	                    }
	                    loadActionType = RELACTION;
	                }
	                else if(xmlElements[i].getLoadAction().equalsIgnoreCase("START_NIMS"))
	                {
	                	// CCI Load_update_proc table contains a row for each collection. This row contain a field called
	                	//LOAD_ELEMSET_NAME. For NIMS this field is set to be "nims".
	                	
	                	//CCI load_element table contains names of load_elements for each load_elemset. For NIMS, this table 
	                	//contains a single load_element called "n-nims". This table also contains a load_action for each
	                	//load_element. For "n-nims", the load_action is defined to be "START_NIMS".
	                	
	                	//String constants used in this code segment are defined (or hard coded) in CCI tables.
	                	//"NIMS_ID" and "NIMS_CNTRL" are values of the CONTENT_TYPE field in rows for which value of the ATTRIBUTE_NAME
	                	//column is equal to "control" and "guid" respectively. For these rows, ELEMENT_NAME column has the value "n-nims"
	                	
	                	START_DOCUMENT_ELEMENT = xmlElements[i].getElementName();
	                    LoadAttribute attributes[] = xmlElements[i].getLoadAttributes();
	                    for(int j=0 ; j < attributes.length; j++)
	                    {
	                        if(attributes[j].getContentType().equalsIgnoreCase("NIMS_ID"))
	                        {
	                            DOCUMENT_IDENTIFIER = attributes[j].getAttributeName();
	                        }
	                        else if(attributes[j].getContentType().equalsIgnoreCase("NIMS_CNTRL"))
	                        {
	                            DOCUMENT_CONTROL = attributes[j].getAttributeName();
	                        }
	                    }
	                    loadActionType = NIMSACTION;       
					} else if (xmlElements[i].getLoadAction().equalsIgnoreCase("START_VIEW")) {
						START_SUBJECTIVE_VIEW = xmlElements[i].getElementName();
	                }
	            }
	        }
	        catch(CciException ex)
	        {
	            LoadDMError le = new LoadDMError("CCIException", ex);
	            le.log("LOADER", "SaxTranslator.init", "");
	            
				throw new LoadErrorException("SaxTranslator.init","LOAD0000",ex.getMessage());            
	        }
	        
	        try{
	        	//Collection col = Collection.retrieve(collectionName);
	        	Collection col = CciInfo.getCollectionInfo(collectionName).getCollectionObject();
	        	collID = col.getId();
	        	rel2Prtnr = col.getRelation2Partner();
	        	familyName = col.getCollectionFamilyName().trim();
	        }catch(CciRecordNotFoundException e){
                throw new LoadErrorException("SaxTranslator.init","LOAD0000","Collection "+ collectionName +" does not have exist");                                        
	        }catch(CciException ce){
	    		Event e = new Event("RetrieveCollectionFamilyCollections");
	            e.put(Event.EVENTLEVEL, Event.CRITICAL);
	            e.put("method", "SaxTranslator.init");
	            e.put(Event.TEXT, ce.getMessage());
	            e.putException(ce);
	            Log.print(e);
	            e = null;
	            
                throw new LoadErrorException("SaxTranslator.init","LOAD0000",ce.getMessage());                                        
	        }

	        if(!"".equalsIgnoreCase(familyName)){
	        	try{
		        	Collection[] familyCollections = Collection.retrieveAllCollectionsForMainCollection(familyName);
		        	mCFCollections = new HashMap<Integer, String>(familyCollections.length);
		        	for(int cp = 0; cp < familyCollections.length; cp++){
		        		mCFCollections.put(familyCollections[cp].getId(), familyCollections[cp].getRelation2Partner().trim());
		        	}
		        	if(loaderParms != null){
		        		loaderParms.setCollectionFamilyMap(mCFCollections);
		        	}
	        	}catch(CciRecordNotFoundException ce){
	                throw new LoadErrorException("SaxTranslator.init","LOAD0000","Collection family "+ familyName +" does not have collections");                                        
	        	}catch(CciException ce){
	
	        		Event e = new Event("RetrieveCollectionFamilyCollections");
	                e.put(Event.EVENTLEVEL, Event.CRITICAL);
	                e.put("method", "SaxTranslator.init");
	                e.put(Event.TEXT, ce.getMessage());
	                e.putException(ce);
	                Log.print(e);
	                e = null;
	                
	                throw new LoadErrorException("SaxTranslator.init","LOAD0000",ce.getMessage());                                        
	        	}
	        }
        }
        
    }

    *//**----------- Document Handler Interface Methods ---------------**//*

    *//**
     * The XML file may contain multiple logical documents.
     * The filter object keys off a well-known element as the
     * start of a document and controls the loader methods.
     *//*
    public void startDocument() throws SAXException 
    { 
    }


    *//**
     * Method is called when the START_LOAD tag is encountered within the load file.
     * <p>
     * Processing of the start load tag consists of getting the data identifier, email 
     * notification identifier, language and country code attributes from the load file.
     * A check to ensure that the load file is going to the proper collection is also
     * performed if the 'verifycollection' attribute is present.
     * <p>
     * 
     * @param attr object containing the attributes associated with the current element tag.
     * @throws SAXException
     *//*
    private void processStartLoad(Attributes attr)
    throws SAXException
    {
        loadStarted = true;
        
        isLowerVersionPurge = false;
        
		// Get the email notification if there is one.
		notification = attr.getValue(LOAD_NOTIFY);

		// Start of the load contains attributes that we use to identify and
		// use in reporting status to user.
        dataIdentifier = attr.getValue(LOAD_IDENTIFIER);
                
		// Get the language from the n-load tag.  Make sure that it is
		// only two characters in length.
        language = attr.getValue(LOAD_LANGUAGE);
        if (language == null) {
            language = ENGLISH;
        } else {
            if (language.length() != 2) {
                throw new SAXException("Invalid lang attribute in n-load element - length is not two chars-" + language + " ");
            }
        }
        
		// Get the country code from the n-load tag.  Make sure that it is
		// only two characters in length.
        country = attr.getValue(LOAD_COUNTRY);
        if (country == null) {
            country = "";
        } else {
            if (country.length() != 2) {
                throw new SAXException("Invalid country attribute in n-load element - length is not two chars-" + country + " ");
            }
        }
        
        //Check whether it is a piggy back load
        if(attr.getValue(LOWER_VERSION_PURGE) != null && 
        		attr.getValue(LOWER_VERSION_PURGE).equals("TRUE")) {
        	isLowerVersionPurge = true;
        }
        
        Event e = new Event("locale");
        e.put(Event.EVENTLEVEL, Event.DEBUG);
        e.put("dataid", dataIdentifier);
        e.put("language", language);
        e.put("country", country);
        e.put("isLowerVersionPurge", isLowerVersionPurge);
        Log.print(e);
        e = null;
        
        //for lower version delete load set the data identifier to null.
        if(isLowerVersionPurge)
        	dataIdentifier = null;

        String clientSpecifiedCollection = attr.getValue(LOAD_CLIENT_DEFINED_COLLECTION);
        if(clientSpecifiedCollection != null && !clientSpecifiedCollection.equalsIgnoreCase(collectionName) )
        {
            if ( ! (micLoad && (loaderParms.getMicGroup()) != null &&
            		(loaderParms.getMicGroup()).isMICGroupCollection(clientSpecifiedCollection)) ){
                	throw new SAXException("Collection name in data " + clientSpecifiedCollection +
                			" does not match novus configuration " + collectionName);    
            }
        }

		// Set the loadtype attribute value on the builders.
		String loadType = attr.getValue(LOAD_TYPE);
		if (loadType != null) {
			Map builderMap = eventDocQueue.getBuildersRef();
			for (Iterator builderIt = builderMap.values().iterator(); builderIt.hasNext(); ) {
				LoadBuilder builder = (com.westgroup.novus.load.LoadBuilder)builderIt.next();
				builder.setLoadType(loadType);
			}
			
			builderMap = null;
		}
		
	    int reindexAttrIndex = attr.getIndex(LOAD_REINDEX_ID_ATTR); 
	    if (reindexAttrIndex != -1) {
	        String id = attr.getValue(reindexAttrIndex);
	        if (id != null && id.length() > 0) {
	            String[] terms = new String[] { "REINDEX" + id };
	            reservedTerms = terms;
	        }                   
	    }      
        
        // loadcontenttype defaults to ALL
	    String temp = attr.getValue(LOAD_CONTENT_TYPE);
	    if (temp != null) {
	    	if (temp.equals(LOAD_CONTENT_TYPE_METADOC)) {
	    		loadcontenttype = LOAD_CONTENT_TYPE_METADOC;
	    	} else if (temp.equals(LOAD_CONTENT_TYPE_SECTIONAL)) {
	    		loadcontenttype = LOAD_CONTENT_TYPE_SECTIONAL;
	    	}
	    }
        
	    eventDocQueue.setLoadContentType(loadcontenttype);
        
        Map builderMap = eventDocQueue.getBuildersRef();
        for (Iterator builderIt = builderMap.values().iterator(); builderIt.hasNext(); ) {
            LoadBuilder builder = (LoadBuilder)builderIt.next();
            builder.setLoadContentType(loadcontenttype);
        }
    }

    *//**
     *  Process the start of a document
     *//*
    private void processStartDocument(Attributes attr)
    throws LoadErrorException, LoadWarningException, SAXException
    {
    	long dVersion = 0;
        this.reindexFlag = false;
		boolean bGenerateQD = false;
		bVersionInvalid = false;
    	String existingColName = null;
    	List<DocumentVersionInfo> lDocVersionInfo = null;
    	
    	long maxVersionQuick = 0;
    	long mainCollnVersion = 0;
    	
    	String loadSource = null;
        *//**
        * Note: The START DOCUMENT element will change in the future and there will be attributes
        * associated with it as well.  The attributes will be passed to the loaders.
        *//*
        // The filter serves to dice the input file into logical
        // documents.  These logical documents all start with
        // a well defined tag (START_DOCUMENT_ELEMENT).
        // The filter calls into the loader to start a document
        // each time this logical document start element is encountered.
        
        // Keep count of the documents for the load
        synchronized(docsProcessedSynchObject) {
    		
        	if(!isLowerVersionPurge) {
        		if (!micLoad) {
        			documentsProcessed++;
        		} else {
        			(micDocCounter.get(micTargetCollection)).incrementDocumentsProcessed();
	        	}
        	}
        }
            
        // Get the element attributes from the attribute object.
        String guid = attr.getValue(DOCUMENT_IDENTIFIER);
        if (guid != null) 
        {
            guid = guid.trim();
        }
        control = attr.getValue(DOCUMENT_CONTROL);
        alert = attr.getValue(DOCUMENT_ALERT);
        
        String strVersion = attr.getValue(DOCUMENT_VERSION);

        if (control != null && control.equalsIgnoreCase(DOCUMENT_REINDEX)){
        	reindexFlag = true;        	
        }
        
        if((Collection.CollectionTypeEnum.QUICK.toString().equalsIgnoreCase(rel2Prtnr) || Collection.CollectionTypeEnum.MAIN.toString().equalsIgnoreCase(rel2Prtnr))){
	        
	        dVersion = versionValue(strVersion, guid);
	        
	        Map<String, List<DocumentVersionInfo>> mDocVersionInfo = this.eventDocQueue.getGUIDVersions();
	        
	        if(mDocVersionInfo != null && !reindexFlag){
	        	lDocVersionInfo = mDocVersionInfo.get(guid);	        	
	        }
	       
	        DocLocUpdateProcess currentFamilyDLC = null;
	        String currentFamilyOwner = null;
	        
	        if(lDocVersionInfo != null && lDocVersionInfo.size() > 0){
	        	Iterator<DocumentVersionInfo> itDocVersionInfos = lDocVersionInfo.iterator();
	        	isOutsideFamily = false;
	        	while(itDocVersionInfos.hasNext() && !isOutsideFamily){
	        		
	        		DocumentVersionInfo docVersionInfo = (DocumentVersionInfo)itDocVersionInfos.next();
	        		long docVersion = docVersionInfo.getVersion();
	        		int collectionId = docVersionInfo.getCollectionId();
	        		
	        		if(isLowerVersionPurge){
	        			loadSource = LOAD_SOURCE_PURGE;
                        //if the version is in MAIN collection(if it is already migrated), the guid can be deleted
                        if((Collection.CollectionTypeEnum.MAIN.toString().equalsIgnoreCase(mCFCollections.get(collectionId))
                                    && docVersion == dVersion)
                                    //if higher version guid exist in any of the quick collection, guild is eligible for deletion
                                    || (Collection.CollectionTypeEnum.QUICK.toString().equalsIgnoreCase(mCFCollections.get(collectionId))
                                                && docVersion > dVersion)) {
                              //if higher version guid exist in the same quick collection, skip the piggy back load
                              if(collectionId == collID ) {
                                    bVersionInvalid = true;
                                    break;
                              }                              
                                                       
                        }

                        //get the highest version among the quicks
                        if(Collection.CollectionTypeEnum.QUICK.toString().equalsIgnoreCase(mCFCollections.get(collectionId)) && docVersion > maxVersionQuick) {
                        	maxVersionQuick = docVersion;
                        }
                        
                      //get the main collection version
                        if(Collection.CollectionTypeEnum.MAIN.toString().equalsIgnoreCase(mCFCollections.get(collectionId)))
                              mainCollnVersion = docVersion;

	        		} else if(!mCFCollections.containsKey(docVersionInfo.getCollectionId())){
	        			
	        			if(currentFamilyDLC == null){
	        				try {
	        			    	//currentFamilyDLC = 	DocLocUpdateProcess.retrieve(familyName);
	        			    	currentFamilyDLC = 	CciInfo.getDocLocUpdateProcessInfo(familyName).getDocLocUpdateProcessObject();	
	        			    	//currentFamilyOwner = Collection.retrieve(familyName).getOwnerName();
	        			    	currentFamilyOwner = CciInfo.getCollectionInfo(familyName).getCollectionObject().getOwnerName();
	        				} catch (CciException e) {		
	        					Event event = new Event("validateGuidUniquenessForCollectionFamilyLoad");
	        					event.put(Event.EVENTLEVEL, Event.WARNING);
	        					event.put("method", "SaxTranslator.validateGuidUniquenessForCollectionFamilyLoad");
	        					event.put(Event.COLLECTION, familyName);
	        					event.put(Event.TEXT, e.getMessage());
	        					event.putException(e);
	        					Log.print(event);
	        					throw new LoadErrorException("SaxTranslator.validateGuidUniquenessForCollectionFamilyLoad","LOAD0000", "Error while retrieving the DLC for collection "+familyName);
	        				}
	        			}
	        			validateGuidUniquenessForCollectionFamilyLoad(currentFamilyDLC,currentFamilyOwner,guid, docVersionInfo, existingColName);
	        			continue;
	        			
	        		} else {
	        			if (loaderParms.isAdrForEhaEnabled()) {
		        			// Validate that the version being loaded to a collection in a Collection Family is valid
		        			// A version for a document being loaded to a MAIN collection in a Collection Family is invalid if the following condition is met:
		        			//    1. there is a version for the document being loaded that already exists in the Collection Family that is higher than the version being loaded 
		        			// A version for a document being loaded to a QUICK collection in a Collection Family is invalid if any of the following conditions is met:
		        			//    1. there is a version for the document being loaded that already exists in the Collection Family that is higher than the version being loaded 
		        			//	  2. there is a version for the document being loaded that already exists in the MAIN collection that is equal to the version being loaded
		        			
		        			//Check for invalid version condition 1
		        			if(docVersion > dVersion) {	  
			        			bVersionInvalid = true;
			        			break;
				        	} 
		        			//Check for invalid version condition 2
			        		else if (Collection.CollectionTypeEnum.QUICK.toString().equalsIgnoreCase(rel2Prtnr) &&
			        				 docVersion == dVersion && 
			        				 Collection.CollectionTypeEnum.MAIN.toString().equalsIgnoreCase(mCFCollections.get(docVersionInfo.getCollectionId()))) {   
					        			bVersionInvalid = true;
					        			break;		        			
			        		}
		        			
		        			// Determine if a QD entry should be generated for a collection in a Collection Family
		        			// A QD entry needs to be generated for a Quick collection if the following condition is met:
		        			//    1. an older version of the document being loaded exists in another collection, Quick or Main, in the Collection Family
		        			// A QD entry needs to be generated for a Main collection if the following condition is met:
		        			//    1. an older version of a document being loaded exists in any Quick collection in the Collection Family
			        		else if(docVersion < dVersion && ((Collection.CollectionTypeEnum.QUICK.toString().equalsIgnoreCase(rel2Prtnr) && docVersionInfo.getCollectionId() != collID)
		        					||(Collection.CollectionTypeEnum.MAIN.toString().equalsIgnoreCase(rel2Prtnr) 
		        							&& !Collection.CollectionTypeEnum.MAIN.toString().equalsIgnoreCase(mCFCollections.get(docVersionInfo.getCollectionId()))))){
			        			bGenerateQD = true;
			        		}
	        			}
	        			else {
	        				if(docVersion > dVersion){
			        			bVersionInvalid = true;
			        			break;
				        	} 
	        				else if(docVersion < dVersion && ((Collection.CollectionTypeEnum.QUICK.toString().equalsIgnoreCase(rel2Prtnr) && docVersionInfo.getCollectionId() != collID)
		        					||(Collection.CollectionTypeEnum.MAIN.toString().equalsIgnoreCase(rel2Prtnr) 
		        							&& !Collection.CollectionTypeEnum.MAIN.toString().equalsIgnoreCase(mCFCollections.get(docVersionInfo.getCollectionId()))))){
	        					bGenerateQD = true;
		        		
		        			}					        	
	        			}	        			
	        		}
	        	}
	        	
	        	//For lower version purge, if the purgeable record version is highest in the quick 
                //and if it not migrated to main, the guid is not eligible for deletion
	        	if(isLowerVersionPurge && !bVersionInvalid) {                    
	        		if(maxVersionQuick == dVersion && dVersion > mainCollnVersion) {
	        			bVersionInvalid = true;
                          
	        			Event e = new Event("UnexpectedGuidVersionForPurge");
	                    e.put(Event.EVENTLEVEL, Event.WARNING);
	                    e.put("method", "SaxTranslator.processStartDocument");
	                    e.put("guid", guid);
	                    e.put("PurgeVersion", dVersion);
	                    e.put("MainVersion", mainCollnVersion);
	                    e.put("MaxQuickVersion", maxVersionQuick);
	                    e.put(Event.TEXT, "Guid " +guid +" with version "+dVersion+ " is been unexpectedly attempted for purge");
	                    Log.print(e);
	                    e = null;
                    }
	        	}

	        }		  
        }
      
        if (alert == null || alert.length() == 0)
        {
        	alert = DOCUMENT_ALERT_INHERIT;
        }
        alert = alert.toUpperCase();
        if (!(DOCUMENT_ALERT_INHERIT.equals(alert) || DOCUMENT_ALERT_NEW.equals(alert) ||
        		DOCUMENT_ALERT_HIDE.equals(alert)))
        {
        	docGuid = guid;
        	String errorText = null;
        	if (mLoadFileName != null && !mLoadFileName.equals("")) {
        		errorText = "Invalid alert attribute value in load file " + mLoadFileName + ", guid = " + docGuid + ", alert attribute value = " + alert;
        	}
        	else {
        		errorText = "Invalid alert attribute value, guid = " + docGuid + ", alert attribute value = " + alert;
        	}
        	Event event = new Event("data");
            event.put(Event.EVENTLEVEL, Event.DEBUG);
            event.put("level", com.westgroup.novus.load.mq.LoadServImpl.ENV);
            event.put("errortype", "data");
            event.put(Event.TEXT, errorText);
            Log.print(event);
            event = null;
        	throw new LoadErrorException("LOAD", "LOAD0000", errorText);
        }
        
        if (!ParmReader.getParm("ALLOW_ALL_ALERT_TYPES", true)) {
        	String initialLoadContentType = loadcontenttype;
        	if (loaderParms.isAdrForEhaEnabled() && loaderParms.isCollectionAdrEnabled() ) {
        		initialLoadContentType = loaderParms.getInitialLoadContentType();
    		}
    			    	
        	if (  ( LOAD_CONTENT_TYPE_SECTIONAL.equalsIgnoreCase(loadcontenttype) || LOAD_CONTENT_TYPE_SECTIONAL.equalsIgnoreCase(initialLoadContentType) )
        			&& !(DOCUMENT_ALERT_INHERIT.equalsIgnoreCase(alert))  )
        	{
        		docGuid = guid;
            	String errorText = null;
            	if (mLoadFileName != null && !mLoadFileName.equals("")) {
            		errorText = "Invalid alert attribute value in load file " + mLoadFileName + " for SECTIONAL LOAD, guid = " + docGuid + ", alert attribute value = " + alert;
            	}
            	else {
            		errorText = "Invalid alert attribute value for SECTIONAL LOAD, guid = " + docGuid + ", alert attribute value = " + alert;
            	}
            	Event event = new Event("data");
                event.put(Event.EVENTLEVEL, Event.DEBUG);
                event.put("level", com.westgroup.novus.load.mq.LoadServImpl.ENV);
                event.put("errortype", "data");
                event.put(Event.TEXT, errorText);
                Log.print(event);
                event = null;
        		throw new LoadErrorException("LOAD", "LOAD0000", errorText);
        	}
        } 
        
        if((Collection.CollectionTypeEnum.QUICK.toString().equalsIgnoreCase(rel2Prtnr) || Collection.CollectionTypeEnum.MAIN.toString().equalsIgnoreCase(rel2Prtnr)) 
        		&& control != null && control.equalsIgnoreCase(PARTIAL_UPD)){
        	String errorText = "Partial Updates not allowed for QUICK or MAIN collections.";
            Event event = new Event("data");
            event.put(Event.EVENTLEVEL, Event.DEBUG);
            event.put("level", com.westgroup.novus.load.mq.LoadServImpl.ENV);
            event.put("errortype", "data");
            event.put(Event.TEXT, errorText);
            Log.print(event);
            event = null;
        	throw new LoadErrorException("SaxTranslator.processStartDocument","LOAD0000",errorText);            
	    }
        
        if (mLoadFileName != null) {
	        if (control != null && control.equalsIgnoreCase(PARTIAL_UPD) && !mLoadFileName.endsWith(PartialUpdateDefs.PARTIAL_UPDATE_EXTENSION)) {
	        	docGuid = guid;
	        	String errorText = "An n-document control attribute of " + PARTIAL_UPD + " is not" +
	        			" valid with any load file extensions other than ." + PartialUpdateDefs.PARTIAL_UPDATE_EXTENSION +
						".  The file being loaded was: " + mLoadFileName;
	            Event event = new Event("data");
	            event.put(Event.EVENTLEVEL, Event.DEBUG);
	            event.put("level", com.westgroup.novus.load.mq.LoadServImpl.ENV);
	            event.put("errortype", "data");
	            event.put(Event.TEXT, errorText);
	            Log.print(event);
	            event = null;
	        	throw new LoadErrorException("LOAD", "LOAD0000", errorText);
	        }
        }    

        // Check to make sure the document has a guid
        if(bVersionInvalid)
        {
        	documentStarted = false;
        	String strVersionSkippedMsg = "";
        	if(!isLowerVersionPurge){
        		documentsSkipped++;    
            	strVersionSkippedMsg = "GUID "+ guid +" with version "+ strVersion +" is lower than the version in family and cannot be loaded";
    			eventDocQueue.addException(new SLIMDataWarningException("SaxTranslator.processStartDocument","LOAD5001", strVersionSkippedMsg, new String[]{guid, strVersion}));
        	}
        	
			Event e = new Event("InvalidDocumentVersion");
            e.put(Event.EVENTLEVEL, Event.INFORMATIONAL);
            e.put("method", "SaxTranslator.processStartDocument");
            e.put("lowerversionpurge", ""+isLowerVersionPurge);
            e.put("guid", guid);
            e.put("version", strVersion);
            e.put(Event.TEXT, strVersionSkippedMsg);
            Log.print(e);
            e = null;
        }
        else if (guid == null || guid.length() == 0) 
        {
            documentStarted = false;
            isDeleteDoc = false;
            String errorText = "Guid not valued for document type " + getLoadType(loadActionType) + ", guid is" + getGuidErrorString(guid) + 
            		           ", number of docs processed =  " + documentsProcessed;
            Event event = new Event("data");
            event.put(Event.EVENTLEVEL, Event.DEBUG);
            event.put("level", com.westgroup.novus.load.mq.LoadServImpl.ENV);
            event.put("errortype", "data");
            event.put("errorcode", "N3001");
            event.put(Event.TEXT, errorText);
            Log.print(event);
            event = null;

            throw new LoadErrorException("LOAD", "LOAD0000", errorText);
        }
        else if (guid.length() > 33)
        {
            String dnStr = getLoadType(loadActionType);

            String errorText = dnStr + " with guid " + guid + " (guid length=" + guid.length() +
                    ") has a length that is greater than the max length of 33";
            Event event = new Event("data");
            event.put(Event.EVENTLEVEL, Event.DEBUG);
            event.put("level", com.westgroup.novus.load.mq.LoadServImpl.ENV);
            event.put("errortype", "data");
            event.put(Event.TEXT, errorText);
            Log.print(event);
            event = null;
            throw new LoadErrorException("LOAD ", "LOAD0000", errorText);
        }
        else if(isOutsideFamily)
        {
        	documentStarted = false;
        	documentsSkipped++;    
        	String strVersionSkippedMsg = "GUID "+ guid +" already exists in Collection " + existingColName + " and cannot be loaded to this family";
			eventDocQueue.addException(new SLIMDataWarningException("SaxTranslator.processStartDocument","LOAD5002", strVersionSkippedMsg, new String[]{guid, strVersion}));

			Event e = new Event("GuidExistsOutsideFamily");
            e.put(Event.EVENTLEVEL, Event.INFORMATIONAL);
            e.put("method", "SaxTranslator.processStartDocument");
            e.put(Event.TEXT, strVersionSkippedMsg);
            Log.print(e);
            e = null;
        }
        else
        {
            // Get an EventDoc from the EventDocQueue
            eventDoc = eventDocQueue.getNextFreeDoc();

            // Record when the parser found the Start Document element.
            if(eventDoc != null) {
                eventDoc.setParserStartTime(System.currentTimeMillis());
                eventDoc.setLanguage(language);
                eventDoc.setCountry(country);
                eventDoc.setStartDocumentElement(START_DOCUMENT_ELEMENT);
                eventDoc.setReservedTerms(reservedTerms);
                //Set the load source, for the document coming from PURGEABLE_VERSIONS table. Load source would be 'PURGE'
                eventDoc.setLoadSource(loadSource);
                if (loadGuid != null) {
                	eventDoc.setLoadGuid(loadGuid);
                }
                eventDoc.setLoadContentType(loadcontenttype);  
                if((Collection.CollectionTypeEnum.QUICK.toString().equalsIgnoreCase(rel2Prtnr) || Collection.CollectionTypeEnum.MAIN.toString().equalsIgnoreCase(rel2Prtnr)) && !bVersionInvalid){
                	eventDoc.setVersion(strVersion);
                	eventDoc.setGenerateQD(bGenerateQD);
                }
            }
                
            docGuid = guid.trim(); // set guid attribute.  Used in exception reporting
    
            if(control != null && control.equalsIgnoreCase(DOCUMENT_DELETE))
            {
                synchronized(docsDeletedSynchObject) {
                	
                	//Increment the documentsDeleted only if
                	//it is not a piggy back load.                	
                	if(isLowerVersionPurge) {
                		documentsInternallyDeleted ++;                		
                	} else {
                		if (!micLoad) {
                			documentsDeleted++;
                		} else {
                			(micDocCounter.get(micTargetCollection)).incrementDocumentsDeleted();
	                		}
    	        		}
                	}
                
                documentStarted = false; // A delete is treated atomically, not a document
                isDeleteDoc = true;
                if(eventDoc != null) {
                	// deleteDocument sets bit mask indicating doc is ready to process.  
                	// Need to set the section rules on the eventDoc before called deleteDocument. 
                    checkSectionRules();
                    eventDoc.deleteDocument(guid);
                }
            }
            
            else if (control != null && control.equalsIgnoreCase(DOCUMENT_DELETEBRANCH))
            {
                synchronized(docsDeletedSynchObject) {
       	    		if (!micLoad) {
    	    			documentsDeleted++;
    	        	} else {
    	        		(micDocCounter.get(micTargetCollection)).incrementDocumentsDeleted();
    	        	}
                }
                documentStarted = false; // A delete is treated atomically, not a document
                isDeleteDoc = true;
                if(eventDoc != null) {
                    eventDoc.deleteBranch(guid);
                }            
            }
            else  // The control attribute defaults to ADD
            {
            	control = DOCUMENT_ADD;
 //           	if(!bVersionInvalid){
	            	synchronized(docsAddedSynchObject) {
	            		if (!micLoad) {
	                    documentsAdded++;
	            		} else {
	            			(micDocCounter.get(micTargetCollection)).incrementDocumentsAdded();
	            		}
//	            	}
                }
                documentStarted = true;
                if(eventDoc != null) {
                	Map<String,String> controlAttributes = new HashMap<String, String>();
                	controlAttributes.put(DOCUMENT_IDENTIFIER, guid);
                	controlAttributes.put(DOCUMENT_ALERT,alert);
                    if((Collection.CollectionTypeEnum.QUICK.toString().equalsIgnoreCase(rel2Prtnr) || Collection.CollectionTypeEnum.MAIN.toString().equalsIgnoreCase(rel2Prtnr)) && !bVersionInvalid){
                		controlAttributes.put(DOCUMENT_VERSION, strVersion);
                    }
                    eventDoc.startDocument(controlAttributes);
                }
            }
        }
    }

    private long versionValue(String strVersion, String guid) throws LoadErrorException {
    	long dVersion = 0;
    	if(strVersion == null || "".equalsIgnoreCase(strVersion)){
        	String errorText = "Version attribute is required for QUICK and MAIN collection loads, guid = " + guid;
            Event event = new Event("data");
            event.put(Event.EVENTLEVEL, Event.DEBUG);
            event.put("level", com.westgroup.novus.load.mq.LoadServImpl.ENV);
            event.put("errortype", "data");
            event.put(Event.TEXT, errorText);
            Log.print(event);
            event = null;
			throw new LoadErrorException("SaxTranslator.versionValue","LOAD0000",errorText);      
	    }
        if(strVersion.trim().length() > 14){
        	String errorText = "Version attribute cannot be more than 14 characters for QUICK and MAIN collections, guid = " + guid + 
            		 ", version = " + strVersion + ", version length = " + strVersion.trim().length();
            Event event = new Event("data");
            event.put(Event.EVENTLEVEL, Event.DEBUG);
            event.put("level", com.westgroup.novus.load.mq.LoadServImpl.ENV);
            event.put("errortype", "data");
            event.put(Event.TEXT, errorText);
            Log.print(event);
            event = null;
			throw new LoadErrorException("SaxTranslator.versionValue","LOAD0000",errorText);      
        }
        try{
        	dVersion = Long.parseLong(strVersion);
        }catch(NumberFormatException nfex){
        	String errorText = "Version attribute for QUICK and MAIN collections must be numeric, guid = " + guid + ", version = " + strVersion;
            Event event = new Event("data");
            event.put(Event.EVENTLEVEL, Event.DEBUG);
            event.put("level", com.westgroup.novus.load.mq.LoadServImpl.ENV);
            event.put("errortype", "data");
            event.put(Event.TEXT, errorText);
            Log.print(event);
            event = null;
        	throw new LoadErrorException("SaxTranslator.versionValue","LOAD0000",errorText);
        }
        
        if(dVersion < 1){
        	String errorText = "Version attribute for QUICK and MAIN collections must be >= 1 for QUICK and MAIN collection loads, guid = " + guid + ", version = " + strVersion;
            Event event = new Event("data");
            event.put(Event.EVENTLEVEL, Event.DEBUG);
            event.put("level", com.westgroup.novus.load.mq.LoadServImpl.ENV);
            event.put("errortype", "data");
            event.put(Event.TEXT, errorText);
            Log.print(event);
            event = null;
			throw new LoadErrorException("SaxTranslator.versionValue","LOAD0000", errorText);      
        }
        
        return dVersion;
    }
    
	*//**
	 * Creates the conditions to start the loading of subjective views.  The method is called when
	 * the START_VIEW tag is encountered in the load data.  The START_VIEW tag is set in the 
	 * Load_Update_Process within the CCI.
	 * <p>
	 * Gets the next available EventDoc, increments the view loaded counts (both deletes and adds), and
	 * calls the startDocument method within the EventDoc.  A null String is passed into the EventDoc.startDocument()
	 * method as the guid, since there are no guids associated with views.  Sets the flag within the
	 * EventDoc that subjective views are being processed.
	 * </p>
	 * 
	 * @param attr object containing the attributes associated with the current element tag.
	 * @throws Exception
	 *//*
	protected void processStartSubjectiveView(Attributes attr) throws Exception {
    	
		if(Collection.CollectionTypeEnum.QUICK.toString().equalsIgnoreCase(rel2Prtnr) || Collection.CollectionTypeEnum.MAIN.toString().equalsIgnoreCase(rel2Prtnr)){
			throw new LoadErrorException("SaxTranslator.processStartSubjectiveView","LOAD0000","Subjective View Loads not allowed for QUICK or MAIN collections.");            
		}
		loadActionType = VIEWACTION;
		isSubjectiveView = true;
    	
		// Keep count of the documents for the load
		synchronized(docsProcessedSynchObject) {
    		if (!micLoad) {
    			documentsProcessed++;
        	} else {
        		(micDocCounter.get(micTargetCollection)).incrementDocumentsProcessed();
        	}
		}

		// Get an EventDoc from the EventDocQueue
		eventDoc = eventDocQueue.getNextFreeDoc();

		// Record when the parser found the Start Document element.
		if(eventDoc != null) {
			eventDoc.setParserStartTime(System.currentTimeMillis());
			eventDoc.setLanguage(language);
			eventDoc.setCountry(country);
		}
		
		String view_control = attr.getValue(VIEW_CONTROL);
		if (view_control != null && view_control.equals(DOCUMENT_DELETE)) {
			synchronized(docsDeletedSynchObject) {
	    		if (!micLoad) {
	    			documentsDeleted++;
	        	} else {
	        		(micDocCounter.get(micTargetCollection)).incrementDocumentsDeleted();
	        	}
			}
		} else {		                
			synchronized(docsAddedSynchObject) {
	    		if (!micLoad) {
	    			documentsAdded++;
	        	} else {
	        		(micDocCounter.get(micTargetCollection)).incrementDocumentsAdded();
	        	}
			}
		}
		
		String view_name = attr.getValue(VIEW_NAME);
		String view_target_collection = attr.getValue(VIEW_TARGET_COLLECTION);
		if(micLoad){
			MICGroup micGroup = loaderParms.getMicGroup();
			boolean isContentCollection = micGroup.getContentCollectionName().equals(loaderParms.getCollectionName());
			if(view_target_collection == null || view_target_collection.trim().length()==0 ){
				throw new LoadErrorException("SaxTranslator.validateAndSetSubjectiveViewForMICGroup","LOAD0000","MultiIndex SubjectiveView attempted when \'target-collection\' missing; (view: "+ view_name + ")");
			}
			view_target_collection = view_target_collection.trim();
			if(firstSubjectiveViewTargetCollection != null ){
				if(!firstSubjectiveViewTargetCollection.equals(view_target_collection)){
					throw new LoadErrorException("SaxTranslator.validateAndSetSubjectiveViewForMICGroup","LOAD0000", "MultiIndex SubjectiveView attempted when multiple target collections defined within the same load. Previous target collection: " + firstSubjectiveViewTargetCollection + " , current target collection: "+  view_target_collection);
				}
			}
			if(micGroup.getSubjectiveViewTargetCollection() == null){
				validateAndSetSubjectiveViewForMICGroup(view_target_collection,view_name,micGroup,isContentCollection);
			}
		}
		documentStarted = true;
		if(eventDoc != null) {
			Map <String,String> controlAttributes = new HashMap<String, String>();
			controlAttributes.put(DOCUMENT_IDENTIFIER, "");
			controlAttributes.put(DOCUMENT_ALERT, "");
			eventDoc.startDocument(controlAttributes);
			eventDoc.setProcessingSubjectiveViews();
		}
	}    
    
    *//**
     * Receives elements as the parser comes across start elements.
     *
     * Start Document is handled within StartElement becuase well known elements are used
     * for the start of documents.
     *
     * @param namespaceURI The Namespace URI.
     * @param localname The local name.
     * @param qname The qualified name.
     * @param attr The attributes attached to the element.
     *
     * @exception SAXException thrown if there is a problem with the startElement() function of the text builder client
     *//*
    public void startElement (String namespaceURI, String localname, String qname, Attributes attr)
    throws SAXException
    {
		errorOnMixedCollection(qname);
		   
		if((bVersionInvalid || isOutsideFamily) && !qname.equals(START_DOCUMENT_ELEMENT)) return;
		
        // If the load has been canceled don't bother going any further.
        if (cancelFlag) {
            throw new SAXException("Load Canceled");
        }
        else if (loadStarted && !documentStarted && qname.equals(START_DOCUMENT_ELEMENT)) 
        {
            try
            {
				nDocumentEncountered = true;
				
                // The start document element defined in the Load_Update_Process in the CCI was
                // encountered.  Call the processing to initialize the document. (i.e., n-document)
                this.processStartDocument(attr); 
                
                //get a new actual Elements map for the new document.
                actualElements = new HashMap();
                startTagElementCounter = 0;
                metaTagElementCounter = 0;
                metaDocTagElementCounter = 0;
                docTagElementCounter = 0;
                mmTagElementCounter = 0;
            }
            catch (LoadErrorException e) 
            {
                 Skip this document, go to the next document in the stream 
                LoadDMError le = new LoadDMError("Exception", e);
                le.logExtraInfo("extra", "Thrower " + e.getThrower() + ", Reason " + e.getReason());
                le.log("LOADER", "SaxTranslator.startElement", docGuid, e.getMessage() + " guid=" + docGuid);            
                
                throw new SAXException(e.getThrower() + ", " +e.getReason());                                        
            }
            catch (LoadSAXException se){
            	
            	LoadDMError le = new LoadDMError("Exception", se);               
                le.log("LOADER", "SaxTranslator.startElement", docGuid, se.getMessage() + " guid=" + docGuid);    
                throw se;    
            }
            catch (Exception e) 
            {
            	   Skip this document, go to the next document in the stream 
                LoadDMError le = new LoadDMError("Exception", e);
                le.log("LOADER", "SaxTranslator.startElement", docGuid, e.getMessage() + " guid=" + docGuid); 
                
                eventDocQueue.cancel(0);
                
                throw new SAXException(e);                                        
            }
        }
		// Process the start of a subjective view.
		else if (loadStarted && !documentStarted && qname.equals(START_SUBJECTIVE_VIEW)) {
			try {
				nViewSetEncountered = true;
				
				processStartSubjectiveView(attr);
				
				//get a new actual Elements map for the new document.
				actualElements = new HashMap();
				startTagElementCounter = 0;
				metaTagElementCounter = 0;
                metaDocTagElementCounter = 0;
                docTagElementCounter = 0;
                mmTagElementCounter = 0;
                
			}
			catch (Exception e) 
			{
				if(!this.isSubjectiveViewLoad() || !micLoad){
					 Skip this document, go to the next document in the stream 
					LoadDMError le = new LoadDMError("Exception", e);
					le.log("LOADER", "SaxTranslator.startElement", docGuid, e.getMessage() + " view=" + attr.getValue(VIEW_NAME));            
				}      
                
				eventDocQueue.cancel(0);
                
				throw new SAXException(e);                                        
			}        
		}
        else if (loadStarted && documentStarted && qname.equals(START_DOCUMENT_ELEMENT)) 
            // Embedded document - abort.
            // i.e., <n-document>...<n-document>...</n-document>
            // Must be of the form <n-document>...</n-document><n-document>...</n-document>
            if (loadActionType.equals(TOCACTION)) {
                throw new SAXException("New node started before end of previous node, guid=" +
                                        attr.getValue(DOCUMENT_IDENTIFIER));           
            } else if (loadActionType.equals(RELACTION)) {
                throw new SAXException("New relationship started before end of previous relationship, guid=" +
                                       attr.getValue(DOCUMENT_IDENTIFIER));
            } else if (loadActionType.equals(NIMSACTION)) {
                throw new SAXException("New NIMS document started before end of previous NIMS document, guid=" +
                                       attr.getValue(DOCUMENT_IDENTIFIER));
            } else {
				if (qname.equals(START_SUBJECTIVE_VIEW)) {
					throw new SAXException("New view started before end of previous view, view_name=" +
											attr.getValue(VIEW_NAME));
				}
                throw new SAXException("New document started before end of previous document, guid=" +
                                        attr.getValue(DOCUMENT_IDENTIFIER));                
            }
                
 
        else if (!loadStarted && qname.equals(LOAD_WRAPPER)) 
        {
            // The element name equals the load_wrapper tag defined in the
            // Load_Update_Process of the CCI.  (i.e., <n-load>)
            this.processStartLoad(attr);
        } else if (loadStarted && !documentStarted && 
        		qname.equals(SUBJECTIVE_WRAPPER)) {
            // Do nothing
        }
        //put another check here...if !docStarted and name = doc,mm or mmfile start...FAIL
        else if (!documentStarted && !isDeleteDoc && (qname.equals(LoaderStartElements.getDocTagName()) ||
                qname.equals(LoaderStartElements.getBlobTagName()) || qname.equals(LoaderStartElements.getMMFileTagName()) ||
                qname.equals(LoaderStartElements.getMetadocTagName()) || qname.equals(LoaderStartElements.getIncludeTagName())))
        {
            if (loadActionType.equals(TOCACTION)) {
                throw new SAXException("A start doc tag (n-docbody, n-metadoc, n-include) or start mm tag (n-blobref,n-mmfile) is not allowed in a TOC only collection.");            
            } else if (loadActionType.equals(RELACTION)) {
                throw new SAXException("A start doc tag (n-docbody, n-metadoc, n-include) or start mm tag (n-blobref,n-mmfile) is not allowed in a RELATIONSHIP only collection.");
            } else if (loadActionType.equals(NIMSACTION)) {
                throw new SAXException("A start doc tag (n-docbody, n-metadoc, n-include) or start mm tag (n-blobref,n-mmfile) is not allowed in a NIMS only collection.");
            } else {
                throw new SAXException("A start doc tag (n-docbody, n-metadoc, n-include) or start mm tag (n-blobref,n-mmfile) was encountered before the start document tag."); 
            }
        } else if (loadStarted && !documentStarted && 
				!qname.equals(START_DOCUMENT_ELEMENT) && !isDeleteDoc) {
			LoadDMError le = new LoadDMError("Exception", 
					new LoadErrorException("SaxTranslator.startElement", 
					"LOAD0000", "Load failed as the user tried to load a " + 
					qname + " element into a " + loadActionType + 
					" Collection, " + collectionName));
            le.log("LOADER", "SaxTranslator.startElement", docGuid, "Invalid Load file ");            
            
            eventDocQueue.cancel(0);
        }
        
        // Check DOC content rules  
        if (loadActionType.equals(DOCACTION)) {

        	// Check for start doc tags
        	if (qname.equals(LoaderStartElements.getDocTagName()) || qname.equals(LoaderStartElements.getBlobTagName()) 
        			|| qname.equals(LoaderStartElements.getMMFileTagName()) || qname.equals(LoaderStartElements.getIncludeTagName())) {
        		startTagElementCounter++;
        		inContentFlag = true;
        		contentInDocFlag = true;

        		if (qname.equals(LoaderStartElements.getDocTagName())) {
        			docTagElementCounter++;
        			inDocBodyFlag = true;
        		}
        		
        		if (qname.equals(LoaderStartElements.getBlobTagName()) || qname.equals(LoaderStartElements.getMMFileTagName())) {
        			mmTagElementCounter++;
        		}
        	}

        	// Check for start meta tag
        	if (qname.equals(LoaderStartElements.getMetadataTagName())) {
        		metaTagElementCounter++;
        		inMetadataFlag = true;
        	}

        	if (inContentFlag && inMetadataFlag)
        		throw new SAXException("Metadata start inside content or doc content start inside metadata, guid="+docGuid);

        	// Check for start metadoc tag
        	if (qname.equals(LoaderStartElements.getMetadocTagName())) {
        		metaDocTagElementCounter++;
        		inMetaDocFlag = true;
        	}

        	if (inContentFlag && inMetaDocFlag)
        		throw new SAXException("MetaDoc start inside content or doc content start inside metadata, guid = " + docGuid);

        } //if docFlag 


        // Process an element if a document has been started.
        // This tag would be embedded within the <n-document> tag, for example <n-docbody>.
        if (documentStarted) {
            int startElementInterrestedLoaders = 0;     // Contains bit flags indicating which builders are interested in this event.
                                                        // Example: 0010 1011
                                                        // 7th bit = metadata interested
                                                        // 6th bit = index wheel interested
                                                        // 5th bit = doc locator NOT interested
                                                        // 4th bit = mm interested
                                                        // 3rd bit = toc NOT interested
                                                        // 2nd bit = index interested
                                                        // 1st bit = doc interested
            int elementRule = 0;    // Uses 4 bit flags per builder indicating the element rules. 0101 = ProcessElement (3rd & 4th) Yes, Process Char (1st & 2nd) Yes
                                    // The bits are shifted to the left for each builder.  (i.e., 1010 0101 1010 0110) Example follows:
                                    // 1010 - MM PE = No, PC = No
                                    //      0101 - TOC PE = Yes, PC = Yes
                                    //          1010 - NDX PE = No, PC = NO
                                    //              0110 - DOC PE = Yes, PC = NO
            boolean ndxPC = true;   // boolean flag used to indicate if the Process Character bits for the Indexer are set to Yes or No.
            
            // Get the previous rule off of the stack.  This rule is used for inheritence processing
            // and indicates if the previous element had its PE and PC bit flags set to Yes or No for
            // each builder.
            if(!elementRulesStack.isEmpty())
                elementRule = elementRulesStack.get(elementRulesStack.size() - 1);
                
            // Loop through all the defined builders checking their rules and set 
            // the bit in startElementInterrestedLoaders if the builder has a PE of Yes.
            for(int i = 0; i < builderCount; i++)
            { 
                //Check if the element name is in the required elements map
                //If it is in the map, get the builders array for that element
                //Check if the current builder is in the builders array
                //If it is, add the element name to an array list
                //Add the arrayList to a map with the builderType as the key.
                ArrayList tmpBuilderArray = (ArrayList)requiredElements.get(qname);
                 
                if (tmpBuilderArray != null)
                {
                   String currentBuilder = Integer.toString(interestedBuilders.get(i));
                   ArrayList tmpActualElementArray = new ArrayList();
                   for (int j=0; j < tmpBuilderArray.size(); j++)
                   {
                      String tmpBuilder = (String)tmpBuilderArray.get(j);
                      if (tmpBuilder.equals(currentBuilder))
                      {
                         if (actualElements.size() == 0)
                         {
                            tmpActualElementArray.add(qname);
                         } //if actualElements == 0
                         else
                         {
                            ArrayList tmp = (ArrayList)actualElements.get(String.valueOf(currentBuilder));
                            if (tmp != null)
                            {
                               tmpActualElementArray = tmp;
                            }  
                            tmpActualElementArray.add(qname);
                         } //else if actualElement != 0
                      } //if tmpBuilder = currentBuilder
                   } //for 
                   
                   if (tmpActualElementArray != null)
                   {
                      actualElements.put(String.valueOf(currentBuilder), tmpActualElementArray);
                   }  
                } //if

                builderType = interestedBuilders.get(i);
                int rule = loadProcessingRules.getRule(qname, builderType); // Check hashmap of processing rules to see if this builder has rules for the current element.
                                                                           // The rule is specific to the builder and does not include other builders rules.
                                                                           // Example: 1010 PE=No and PC=No, 0101 PE=Yes and PC=Yes, 0110 PE=Yes and PC=No.
                
                // If this is the INDEX builder, check to see if the process characters flag is set to NO, if
                // it is NO then set the ndxPC flag to false.  This flag is used primarily to process elements
                // That have PE=No & PC= NO.
                if (builderType == EventDocDefs.BUILDER_TYPE_IDX) {
                    if ((rule & LoadProcessingDefs.PROCESS_CHARACTERS_NO) > 0) {
                        ndxPC = false;
                    }
                }

                // Determine if this builder is interested in this element (PE=Yes).  If a rule is not set for
                // this builder and element, check its parents attributes to determine if PE=Yes.
                if((rule & LoadProcessingDefs.PROCESS_ELEMENT_YES) > 0 ||
                    ((rule & LoadProcessingDefs.PROCESS_ELEMENT_MASK) ==
                    LoadProcessingDefs.PROCESS_ELEMENT_INHERIT &&
                    (LoadProcessingDefs.PROCESS_ELEMENT_YES & 
                    loadProcessingRules.getRule(builderType, elementRule)) > 0))
                {
                    // Determine if the load is reindexing.  If this is a reindex load and the builder type equals TOC or MM then
                    // DO NOT indicate that this builder is interested.  Only NDX, NDXWHL, DOC and DLC are interested in the reindex
                    // processing.
                    if (!(reindexFlag && (builderType == EventDocDefs.BUILDER_TYPE_TOC || builderType == EventDocDefs.BUILDER_TYPE_MM))) {
                        startElementInterrestedLoaders |= builderType;
                    }
                }
                elementRule = loadProcessingRules.generateRule(builderType, rule, elementRule);
                
            }

            // Make sure that the top level element has PE & PC rules defined.  The top level element must have
            // rules defined to be either Y or N, P (parent/inheritence) is not a valid option since there is 
            // nothing for this element to inherit being it is the first element in the stack.
            if (elementRulesStack.isEmpty() &&
                interestedBuilders.contains(EventDocDefs.BUILDER_TYPE_IDX) && 
                (loadProcessingRules.getRule(EventDocDefs.BUILDER_TYPE_IDX, elementRule) < 4 ||
                loadProcessingRules.getRule(EventDocDefs.BUILDER_TYPE_IDX, elementRule) == 4 ||
                loadProcessingRules.getRule(EventDocDefs.BUILDER_TYPE_IDX, elementRule) == 8))
                throw new SAXException("Top level element " + qname + " has inheritence properties in the INDEX ELEMENT SET.");


            // Add the rule for the current element to stack of rules.  This rule will 
            // get popped from the stack in the endElement() processing.
            elementRulesStack.add(elementRule);
                        
            try
            {
                // Call the start element method in the EventDocQueue
                // pass startElement() to EventQueue, stuffed into 
                // awaiting EventDoc object
                if(eventDoc != null) {
                    eventDoc.startElement(startElementInterrestedLoaders,
                    						namespaceURI, 
				                            localname,
				                            qname,
				                            attr,
				                            language,
				                            country,
				                            ndxPC);
                }                            
            }
            catch (LoadWarningException e) 
            {
                 Skip this document, go to the next document in the stream 
                dataErrorCount++;
                LoadDMError le = new LoadDMError("data", e);
                le.logExtraInfo("extra", "Thrower " + e.getThrower() + ", Reason " + e.getReason());
                le.logExtraInfo("errortype", "data");
                le.log("LOADER", "SaxTranslator.startElement", docGuid, e.getMessage() + " guid=" + docGuid);            
            }
            catch (LoadErrorException e) 
            {
                 Skip this document, go to the next document in the stream 
                LoadDMError le = new LoadDMError("Exception", e);
                le.logExtraInfo("extra", "Thrower " + e.getThrower() + ", Reason " + e.getReason());
                le.log("LOADER", "SaxTranslator.startElement", docGuid, e.getMessage() + " guid=" + docGuid);            
                
                throw new SAXException(e.getThrower() + ", " +e.getReason());                                        
            }
        }
    }
    
    *//**
     * Receive notification of character data inside an element.
     * 
     * <p>Need to buffer up characters because parser may make multiple calls to this function before it is done parsing.
     * 
     * <p>This function adds the relevant ch[] data from the parser onto the end of the elementText[] attribute.
     * 
     * @param ch An array of chars from the parser.
     * @param start The starting location of character data.
     * @param length The number of characters to use from the array.
     *
     * @exception SAXException thrown if there is an error with the characters() method of the text builder client.
     *//*
    public void characters(char[] ch, int start, int length)
    throws SAXException
    {
        // If the load has been canceled don't bother going any further.
		if(bVersionInvalid || isOutsideFamily) return;

		if (cancelFlag)
            throw new SAXException("Load Canceled");
        else if (documentStarted) 
        {
            int chInterrestedLoaders = 0;   // Contains bit flags indicating which builders are interested in this event.
                                                        // Example: 0010 1011
                                                        // 7th bit = metadata interested
                                                        // 6th bit = index wheel interested
                                                        // 5th bit = doc locator NOT interested
                                                        // 4th bit = mm interested
                                                        // 3rd bit = toc NOT interested
                                                        // 2nd bit = index interested
                                                        // 1st bit = doc interested
            int elementRule = 0;    // Uses 4 bit flags per builder indicating the element rules. 0101 = ProcessElement (3rd & 4th) Yes, Process Char (1st & 2nd) Yes
                                    // The bits are shifted to the left for each builder.  (i.e., 1010 0101 1010 0110) Example follows:
                                    // 1010 - MM PE = No, PC = No
                                    //      0101 - TOC PE = Yes, PC = Yes
                                    //          1010 - NDX PE = No, PC = NO
                                    //              0110 - DOC PE = Yes, PC = NO
            // Get the previous rule off of the stack.  This rule is used for inheritence processing
            // and indicates if the previous element had its PE and PC bit flags set to Yes or No for
            // each builder.
            if(!elementRulesStack.isEmpty())
                elementRule = elementRulesStack.get(elementRulesStack.size() - 1);
                
            // Pass characters to DOC if DOC has registered interest.
            // Pass characters to TOC if TOC has registered interest.
            // Iterate through the loaders to process the characters event one at a time.
            for(int i = 0; i < builderCount; i++)
            {
                builderType = interestedBuilders.get(i);
                int rule = loadProcessingRules.getRule(builderType,elementRule);    // Check hashmap of processing rules to see if this builder has rules for the current element.
                                                                                    // The rule is specific to the builder and does not include other builders rules.
                                                                                    // Example: 1010 PE=No and PC=No, 0101 PE=Yes and PC=Yes, 0110 PE=Yes and PC=No.
                if((rule & LoadProcessingDefs.PROCESS_CHARACTERS_YES) > 0)
                {
                    // Determine if the load is reindexing.  If this is a reindex load and the builder type equals TOC or MM then
                    // DO NOT indicate that this builder is interested.  Only NDX, NDXWHL, DOC and DLC are interested in the reindex
                    // processing.
                    if (!(reindexFlag && (builderType == EventDocDefs.BUILDER_TYPE_TOC || builderType == EventDocDefs.BUILDER_TYPE_MM))) {
                        chInterrestedLoaders |= builderType;
                    }
                }
            }

             Propagate charaters method to EventDocQueue to be stuffed into EventDoc object 
            try
            {
                if(eventDoc != null) {
                    int rule = loadProcessingRules.getRule(EventDocDefs.BUILDER_TYPE_IDX,elementRule);  // Need to pass along the indexers rule.
                    eventDoc.characters(chInterrestedLoaders, ch, start, length, rule);
                }
            }
            catch (LoadWarningException e) 
            {
                 Skip this document, go to the next document in the stream 
                dataErrorCount++;
                LoadDMError le = new LoadDMError("data", e);
                le.logExtraInfo("extra", "Thrower " + e.getThrower() + ", Reason " + e.getReason());
                le.logExtraInfo("errortype", "data");
                le.log("LOADER", "SaxTranslator.characters", docGuid, e.getMessage() + " guid=" + docGuid);            
            }
            catch (LoadErrorException e) 
            {
                 Skip this document, go to the next document in the stream 
                LoadDMError le = new LoadDMError("Exception", e);
                le.logExtraInfo("extra", "Thrower " + e.getThrower() + ", Reason " + e.getReason());
                le.log("LOADER", "SaxTranslator.characters", docGuid, e.getMessage() + " guid=" + docGuid);            
                
                throw new SAXException(e.getThrower() + ", " +e.getReason());                                        
            }
        }
    }


    *//**
     * Receive notification of the end of an element.
     * 
     * @param namespaceURI The Namespace URI
     * @param localname The local name.
     * @param qname The qualified name.
     * @exception SAXException thrown if there is an error adding an sndx field
     * 			  or adding end element info to the text builder client
     *//*
    public void endElement(String namespaceURI, String localname, String qname)
    throws SAXException
    {

		// If the load has been canceled don't bother going any further.
        if (cancelFlag) {
            throw new SAXException("Load Canceled");
        }
		 
		if (loadActionType.equals(DOCACTION)) {
			if (qname.equals(LoaderStartElements.getDocTagName()) || qname.equals(LoaderStartElements.getBlobTagName()) 
				|| qname.equals(LoaderStartElements.getMMFileTagName()) || qname.equals(LoaderStartElements.getIncludeTagName())) {
				inContentFlag = false;
				
				if (qname.equals(LoaderStartElements.getDocTagName())) {	
					inDocBodyFlag = false;
				}
			}
			
			if (qname.equals(LoaderStartElements.getMetadataTagName())) {
				inMetadataFlag = false;
			}
			
            if (qname.equals(LoaderStartElements.getMetadocTagName())) {
                inMetaDocFlag = false;
            }			
		}

        // Record parser end time for both adds and deletes when the end element for the start
        // doc is found. (i.e., </n-document>))
		if ((qname.equals(START_DOCUMENT_ELEMENT) || qname.equals(START_SUBJECTIVE_VIEW)) && (eventDoc != null)) {
            eventDoc.setParserEndTime(System.currentTimeMillis());   
        }
        
		if (qname.equals(START_DOCUMENT_ELEMENT)) {
			contentInDocFlag = false;	
		}
		
        if (documentStarted) 
        {
            int eeInterrestedLoaders = 0;   // Contains bit flags indicating which builders are interested in this event.
                                            // Example: 0010 1011
                                            // 6th bit = index wheel interested
                                            // 5th bit = doc locator NOT interested
                                            // 4th bit = mm interested
                                            // 3rd bit = toc NOT interested
                                            // 2nd bit = index interested
                                            // 1st bit = doc interested
            int elementRule = 0;    // Uses 4 bit flags per builder indicating the element rules. 0101 = ProcessElement (3rd & 4th) Yes, Process Char (1st & 2nd) Yes
                                    // The bits are shifted to the left for each builder.  (i.e., 1010 0101 1010 0110) Example follows:
                                    // 1010 - MM PE = No, PC = No
                                    //      0101 - TOC PE = Yes, PC = Yes
                                    //          1010 - NDX PE = No, PC = NO
                                    //              0110 - DOC PE = Yes, PC = NO
            // Get the previous rule off of the stack.  This rule is used for inheritence processing
            // and indicates if the previous element had its PE and PC bit flags set to Yes or No for
            // each builder.
            if(!elementRulesStack.isEmpty())
                elementRule = elementRulesStack.remove(elementRulesStack.size() - 1);

            // Loop through all the defined builders checking their rules and set 
            // the bit in startElementInterrestedLoaders if the builder has a PE of Yes.
            for(int i = 0; i < builderCount; i++)
            {
                builderType = interestedBuilders.get(i);
                int rule = loadProcessingRules.getRule(builderType,elementRule);    // Check hashmap of processing rules to see if this builder has rules for the current element.
                                                                                    // The rule is specific to the builder and does not include other builders rules.
                                                                                    // Example: 1010 PE=No and PC=No, 0101 PE=Yes and PC=Yes, 0110 PE=Yes and PC=No.
                if((rule & LoadProcessingDefs.PROCESS_ELEMENT_YES) > 0)
                {
                    // Determine if the load is reindexing.  If this is a reindex load and the builder type equals TOC or MM then
                    // DO NOT indicate that this builder is interested.  Only NDX, NDXWHL, DOC and DLC are interested in the reindex
                    // processing.
                    if (!(reindexFlag && (builderType == EventDocDefs.BUILDER_TYPE_TOC || builderType == EventDocDefs.BUILDER_TYPE_MM))) {
                        eeInterrestedLoaders |= builderType;
                    }
                }
            }
            try
            {
                // Pass endElement event to the EventDocQueue to be stuffed into the EventDoc object                
                if(eventDoc != null)
                    eventDoc.endElement(eeInterrestedLoaders,
                    					namespaceURI,
                    					localname,
                    					qname);
                    
                *//**
                * Note: The START DOCUMENT element will change in the future and there will be attributes
                * associated with it as well.  The attributes will be passed to the loaders.
                *//*
				if (qname.equals(START_DOCUMENT_ELEMENT) || qname.equals(START_SUBJECTIVE_VIEW))  
                {
					// Check sections in load file against rules
					checkSectionRules();
					
                    documentStarted = false;    
                    isDeleteDoc = false;
                    //set the actualElementMap in the EventDoc
                    if (eventDoc != null) {
                        eventDoc.setActualRequiredElementMap(actualElements);
                    }                    
                    actualElements = null;

                    //reset the startTagElementCounter
                    startTagElementCounter = 0;
                    metaTagElementCounter = 0;
                    metaDocTagElementCounter = 0;
                    docTagElementCounter = 0;
                    mmTagElementCounter = 0;                    

                    if(eventDoc != null) {
                        eventDoc.endDocument(docGuid);
                    }

                    // Document is done set internal refernce to null
                    eventDoc = null;  
                }
            }
            catch (LoadWarningException e) 
            {
                 Skip this document, go to the next document in the stream 
                dataErrorCount++;
                LoadDMError le = new LoadDMError("data", e);
                le.logExtraInfo("extra", "Thrower " + e.getThrower() + ", Reason " + e.getReason());
                le.logExtraInfo("errortype", "data");
                le.log("LOADER", "SaxTranslator.endElement", docGuid, e.getMessage() + " guid=" + docGuid);                            
            }
            catch (LoadErrorException e) {
                LoadDMError le = new LoadDMError("Exception", e);
                le.logExtraInfo("extra", "Thrower " + e.getThrower() + ", Reason " + e.getReason());
                le.log("LOADER", "SaxTranslator.characters", docGuid, e.getMessage() + " guid=" + docGuid);            
                
                throw new SAXException(e.getThrower() + ", " +e.getReason());
            }
            catch (Exception e) {
                LoadDMError le = new LoadDMError("Exception", e);
                le.log("LOADER", "SaxTranslator.characters", docGuid, e.getMessage() + " guid=" + docGuid);            
                
                throw new SAXException(e.getMessage());
            }                    
        } else {
            // documentStarted is false for start element attribute control = DEL or DELBRANCH.
            if (qname.equals(START_DOCUMENT_ELEMENT)) {
                // Notify the queue that a doc is ready
                eventDocQueue.put();

                // Document is done set internal refernce to null
                eventDoc = null;
                
//                isDeleteDoc = false;
            } 
        }
    }
    
    *//**
     * An XML file may constitute mutliple logical documents.
     * We can not key off the XML file for start and end
     * of documents unless each XML file corresponds to a 
     * single logical document.
     *//*
    public void endDocument() throws SAXException
    {
    }
    
    *//**
     * Receive notification of ignorableWhitespace in an element.
     *
     * @param ch the whitespace characters
     * @param start start position of whitespace characters in array
     * @param length number of characters to use from array
     *
     * @exception SAXException thrown if there is an error calling the ignorableWhitespace function of the text builder client
     *//*
    public void ignorableWhitespace(char[] ch, int start, int length)
    throws SAXException
    {
    	if(bVersionInvalid || isOutsideFamily) return;

    	if (documentStarted && eventDoc != null) 
        {
            eventDoc.ignorableWhitespace(ch, start, length);
        }
    }
    
    *//**
     * Receive notification of a processing instruction.
     * 
     * @param target The processing instruction target.
     * @param data The processing instruction data, or null if none was supplied.
     *//*
    public void processingInstruction(String target, String data)
    throws SAXException
    {
    	if(bVersionInvalid || isOutsideFamily) return;

    	if (documentStarted && eventDoc != null) {
            eventDoc.processingInstruction(target, data, getBuilders());
        }
    }

    *//**
     * Receive an object for locating the origin of SAX document events.
     * 
     * @param locator An object that can return the location of any SAX document event.
     *//*  
    public void setDocumentLocator(Locator locator) 
    {
        if (documentStarted && eventDoc != null) 
        {
            eventDoc.setDocumentLocator(locator);
        }
    }

	*//**
	 * Begin the scope of a prefix-URI Namespace mapping. 
	 *
	 * @param prefix The Namespace prefix being declared.
	 * @param uri The Namespace URI the prefix is mapped to.
	 *//*
    public void startPrefixMapping(String prefix, String uri)
    throws SAXException
    {
        if (documentStarted && eventDoc != null) 
        {
            eventDoc.startPrefixMapping(prefix, uri);
        }
    }

	*//**
	 * End the scope of a prefix-URI mapping. 
	 *
	 * @param prefix The prefix that was being mapping.
	 *//*
    public void endPrefixMapping(String prefix)
    throws SAXException
    {
        if (documentStarted && eventDoc != null) 
        {
            eventDoc.endPrefixMapping(prefix);
        }

    }
    
	*//**
	 * Receive notification of a skipped entity.
	 *
	 * @param name The name of the skipped entity. If it is a parameter entity,
	 * 			   the name will begin with '%', and if it is the external DTD
	 * 			   subset, it will be the string "[dtd]". 
	 *//*
    public void skippedEntity(String name)
    throws SAXException
    {
        if (documentStarted && eventDoc != null) 
        {
            eventDoc.skippedEntity(name);
        }
    }
    
    *//********************* Lexical Handler Interfaces *****************************************************//*

	*//**
	 * Report an XML comment anywhere in the document.
	 * <p>
	 * This callback will be used for comments inside or
	 * outside the document element, including comments in
	 * the external DTD subset (if read). Comments in the DTD
	 * must be properly nested inside start/endDTD and start/endEntity
	 * events (if used).
	 * </p>
	 * @param buf An array holding the characters in the comment.
	 * @param start The starting position in the array.
	 * @param length The number of characters to use from the array.
	 * @throws SAXException
	 *//*    
    public void comment(char[] buf, int start, int length) throws SAXException {

		if (documentStarted && eventDoc != null) {
			eventDoc.comment(buf, start, length, getBuilders()); 
		}
    }
    
	*//**
	 * Report the start of a CDATA section.
	 * <p>
	 * The contents of the CDATA section will be reported through the regular
	 * characters event; this event is intended only to report the boundary.
	 * </p>
	 * @throws SAXException
	 *//*
	public void startCDATA() throws SAXException {
		if (documentStarted && eventDoc != null) {
			eventDoc.startCDATA(); 
		} 
	}
	
	*//**
	 * Report the end of a CDATA section.
	 * 
	 * @throws SAXException
	 *//*
	public void endCDATA() throws SAXException {
		if (documentStarted && eventDoc != null) {
			eventDoc.endCDATA(); 
		} 
	}

	*//**
	 * Report the start of DTD declarations, if any.
	 * <p>
	 * This method is intended to report the beginning of the DOCTYPE declaration; if the document
	 * has no DOCTYPE declaration, this method will not be invoked.
	 * </p>
	 * <p>
	 * All declarations reported through DTDHandler or DeclHandler events must appear between
	 * the startDTD and endDTD events. Declarations are assumed to belong to the internal DTD
	 * subset unless they appear between startEntity and endEntity events. Comments and processing
	 * instructions from the DTD should also be reported between the startDTD and endDTD events, in
	 * their original order of (logical) occurrence; they are not required to appear in their correct
	 * locations relative to DTDHandler or DeclHandler events, however.Note that the start/endDTD events
	 * will appear within the start/endDocument events from ContentHandler and before the first startElement event.
	 * </p>
	 * @param name The document type name.
	 * @param publicId The declared public identifier for the external DTD subset, or null if none was declared.
	 * @param systemId The declared system identifier for the external DTD subset, or null if none was declared.
	 * @throws SAXException
	 *//*
	public void startDTD(String name, String publicId, String systemId) throws SAXException {
		if (documentStarted && eventDoc != null) {
			eventDoc.startDTD(name, publicId, systemId); 
		} 
	}

	*//**
	 * Report the end of DTD declarations.
	 * <p>
	 * This method is intended to report the end of the DOCTYPE declaration; if the document
	 * has no DOCTYPE declaration, this method will not be invoked.
	 * </p>
	 * @throws SAXException
	 *//*
	public void endDTD() throws SAXException {
		if (documentStarted && eventDoc != null) {
			eventDoc.endDTD(); 
		} 
	}
	
	*//**
	 * Report the beginning of some internal and external XML entities.
	 * <p>
	 * The reporting of parameter entities (including the external DTD subset) is optional,
	 * and SAX2 drivers that support LexicalHandler may not support it; you can use the
	 * http://xml.org/sax/features/lexical-handler/parameter-entities feature to query or
	 * control the reporting of parameter entities.
	 * </p>
	 * <p>
	 * General entities are reported with their regular names, parameter entities have '%'
	 * prepended to their names, and the external DTD subset has the pseudo-entity name "[dtd]".
	 * </p>
	 * <p>
	 * When a SAX2 driver is providing these events, all other events must be properly nested within
	 * start/end entity events. There is no additional requirement that events from DeclHandler or
	 * DTDHandler be properly ordered.
	 * </p>
	 * <p>
	 * Note that skipped entities will be reported through the skippedEntity event, which is part of
	 * the ContentHandler interface.
	 * </p>
	 * <p>
	 * Because of the streaming event model that SAX uses, some entity boundaries cannot be reported under any circumstances:
	 * - general entities within attribute values
	 * - parameter entities within declarations
	 * </p>
	 * <p>
	 * These will be silently expanded, with no indication of where the original entity boundaries were.
	 * </p>
	 * <p>
	 * Note also that the boundaries of character references (which are not really entities anyway) are not reported.
	 * </p>
	 * <p>
	 * All start/endEntity events must be properly nested.
	 * </p>
	 * @param name The name of the entity. If it is a parameter entity, the name will begin with '%', and if it is the external DTD subset, it will be "[dtd]".
	 * @throws SAXException
	 *//*
	public void startEntity(String name) throws SAXException {
		if (documentStarted && eventDoc != null) {
			eventDoc.startEntity(name); 
		} 
	}

	*//**
	 * Report the end of an entity.
	 * 
	 * @param name The name of the entity that is ending.
	 * @throws SAXException
	 *//*
	public void endEntity(String name) throws SAXException {
		if (documentStarted && eventDoc != null) {
			eventDoc.endEntity(name); 
		} 
	}
				
	*//********************* Lexical Handler Interfaces *****************************************************//*
        
    *//**----------- Error Handler Interface Methods ---------------**//*
    
    *//**
    * Receive notification of a warning. 
    * <p>SAX parsers will use this method to report conditions that are not errors or fatal errors as defined by the XML 1.0 recommendation. The default behaviour is to take no action.
    *
    * <p>The SAX parser must continue to provide normal parsing events after invoking this method: it should still be possible for the application to process the document through to the end.
    * <br>An appropriate message as been added to the log should this be called.
    *
    * @param exception The warning information encapsulated in a SAX parse exception.
    * @exception SAXException Any SAX exception, possibly wrapping another exception.
    **//*
    public void warning(SAXParseException exception)
    throws SAXException
    {
        // Log the warning
        dataErrorCount++;
        LoadDMError le = new LoadDMError("Exception", exception);
        le.logExtraInfo("SAXException1", "Line number = " + exception.getLineNumber() + ", Column Number = " + exception.getColumnNumber());
        le.logExtraInfo("SAXException2", "Public ID = " + exception.getPublicId() + ", System ID = " + exception.getSystemId());
        le.logExtraInfo("errortype", "data");
        le.log("LOADER", "SaxTranslator.warning", docGuid, exception.getMessage() + " guid=" + docGuid);            
    }
    
    *//**
    * Receive notification of a recoverable error. 
    * <p>This corresponds to the definition of "error" in section 1.2 of the W3C XML 1.0 Recommendation. For example, a validating parser would use this callback to report the violation of a validity constraint. The default behaviour is to take no action.
    * 
    * <p>The SAX parser must continue to provide normal parsing events after invoking this method: it should still be possible for the application to process the document through to the end. If the application cannot do so, then the parser should report a fatal error even if the XML 1.0 recommendation does not require it to do so.
    * <br>An appropriate message is recorded in the log file 
    *
    * @param exception The error information encapsulated in a SAX parse exception.
    * @exception SAXException Any SAX exception, possibly wrapping another exception.
    **//*
    public void error(SAXParseException exception)
    throws SAXException
    {
        // Log the error
        dataErrorCount++;
        LoadDMError le = new LoadDMError("Exception", exception);
        le.logExtraInfo("SAXException1", "Line number = " + exception.getLineNumber() + ", Column Number = " + exception.getColumnNumber());
        le.logExtraInfo("SAXException2", "Public ID = " + exception.getPublicId() + ", System ID = " + exception.getSystemId());
        le.logExtraInfo("errortype", "data");
        le.log("LOADER", "SaxTranslator.error", docGuid, exception.getMessage() + " guid=" + docGuid);            
    }
    
    *//**
    * Receive notification of a <b>non-recoverable</b> error. 
    * <p>This corresponds to the definition of "fatal error" in section 1.2 of the W3C XML 1.0 Recommendation. For example, a parser would use this callback to report the violation of a well-formedness constraint.
    *
    * <br>The application must assume that the document is unusable after the parser has invoked this method, and should continue (if at all) only for the sake of collecting addition error messages: in fact, SAX parsers are free to stop reporting any other events once this method has been invoked.
    *
    * @param exception The error information encapsulated in a SAX parse exception.
    * @exception SAXException Any SAX exception, possibly wrapping another exception.
    *//*
    public void fatalError(SAXParseException exception)
    throws SAXException
    {
        // Log the fatal error  
        dataErrorCount++;
        LoadDMError le = new LoadDMError("Exception", exception);
        le.logExtraInfo("SAXException1", "Line number = " + exception.getLineNumber() + ", Column Number = " + exception.getColumnNumber());
        le.logExtraInfo("SAXException2", "Public ID = " + exception.getPublicId() + ", System ID = " + exception.getSystemId());
        le.logExtraInfo("errortype", "data");
        le.log("LOADER", "SaxTranslator.fatalError", docGuid, exception.getMessage() + " guid=" + docGuid);            
        
        throw exception;
    }

    
    *//**----------- General Purpose Methods ---------------**//*

    *//**
     * The load process is now complete, notify all loaders.
     * 
     * @return String	formatted string that indicates the number of data errors found.  Null
     * 					if no data errors were found.
     * @throws LoadErrorException
     * @throws LoadWarningException
     **//*
    public String getStatus() throws LoadErrorException, LoadWarningException
    {
        if(dataErrorCount > 0)
        {
        	if (!micLoad) {
            return "Found " + dataErrorCount + " data errors in load";
        	} else {
        		return "Found " + dataErrorCount + " data errors in load" + "For mic target collection: " + micTargetCollection;
        	}
        }
        else
            return null;
    }

    *//**
     * The user or load process has canceled the load.
     * Gracefully shut down all load related elements.
     * 
     * @throws LoadException
     **//*
    public static void cancelLoad() throws NovusException
    {
        Thread.dumpStack();
        cancelFlag = true;
    }

    *//** 
     * Return the number of documents within the XML file parsed.
     * Also, return how many were add or delete requests.
     *
     * @return String	formatted string that indicates the total 
     * 					number of documents loaded.  Also, how many
     * 					were added and deleted.
     *//*
    public String getInformation()
    {
        return "Number: Add=" + documentsAdded + " Skipped =" + documentsSkipped + " Delete=" +
            documentsDeleted + " Total=" + documentsProcessed;
    }

    *//**
     * Return the number of documents processed so far.
     *
     * @return int	total number of documents processed (both
     * 				adds and deletes).
     *//*
    public int getDocsProcessed()
    {
        return documentsProcessed;
    }
    
    *//**
     * Return the number of documents added during the load.
     * 
     * @return int	number of documents add to collection.
     *//*
    public int getDocsAdded()
    {
        return documentsAdded;
    }
    
    *//**
     * Return the number of documents skipped during the load.
     * 
     * @return int	number of documents skip to collection.
     *//*
    public static int getDocsSkipped()
    {
        return documentsSkipped;
    }
    
    *//**
     * Return the number of documents deleted during the load.
     * 
     * @return int	number of documents deleted from the collection.
     *//*
    public int getDocsDeleted()
    {
        return documentsDeleted;
    }

    *//**
     * Returns number of documents internally deleted during the load.
     * 
     * @return int number of documents internally deleted from the collection.
     *//*
    public int getDocsInternallyDeleted()
    {    	
    	return documentsInternallyDeleted;
    }
    
    *//**
     * Return the data identifier associated with the xml to be loaded.
     * 
     * @return String	data identifier found within the xml load file.
     *//*
    public String getDataIdentifier()
    {
        return dataIdentifier;
    }

    *//**
     * Return the number of data errors in load.
     * 
     * @return int	number of data errors found in the load.
     *//*
    public int getDataErrorCount()
    {
        return dataErrorCount;
    }

    *//**
     * Return the notification option specified in the data if given by client.
     * 
     * @return String	string containing email addresses to get notified at the
     * 					end of the load.
     *//*
    public String getNotification()
    {
        return notification;
    }
    
    *//**
     * Return the notification option specified in the data if given by client.
     *//*
    public static void resetDocCount()
    {
        documentsProcessed = 0;
        documentsAdded = 0;
        documentsSkipped = 0;
        documentsDeleted = 0;
        documentsInternallyDeleted = 0;
    }
    
    *//**
     * Returns the current doc guid.
     * 
     * @return String	guid of the current doc being parsed.
     *//*
    public String getDocGuid() {
    	return this.docGuid;
    }
    
    *//**
     * Returns a formatted string depending on the guid.
     * 
     * @param gid the guid to check.
     * @return String	formatted string of the specific error.
     *//*
    private String getGuidErrorString(String gid) {        
        if (gid == null)
        	return " null.";
        else if (gid == "")
        	return " a null string.";
       	else
       		return " all spaces.";
    }
    
    *//**
     * Returns the document type (i.e., Document, Node, or Relationship).
     * 
     * @param type indicating the doc type.
     * @return String	string representation of the doc type.
     *//*
    private String getLoadType(String type) {
        if (type.equals(TOCACTION)) {
            return "Node";           
        } else if (type.equals(RELACTION)) {
            return "Relationship";
        } else if (type.equals(NIMSACTION)) {
            return "NIMS Document";   //This string is used only for logging.
		} else if (type.equals(VIEWACTION)) {
			return "View";
        } else {
            return "Document";
        }
    }
    
	*//**
	 * Check if the load is processing subject views.
	 * 
	 * @return boolean	true if subjective views were loaded; otherwise false.
	 *//*
	public static boolean isSubjectiveViewLoad() {
		return SaxTranslator.isSubjectiveView;
	}
    
	*//**
	 * Ensures that both a n-document and n-view-set elements do not occur
	 * within the same load file.
	 * <p>
	 * If an n-document element is present then there cannot be any n-view-set
	 * elements and vice versa.
	 * </p>
	 * 
	 * @param element element name.
	 * @throws SAXException
	 *//*
	private void errorOnMixedCollection(String element) throws SAXException {
		if (element.equals(START_DOCUMENT_ELEMENT)) {
			if (nViewSetEncountered) {
				throw new SAXException("An individual load file cannot contain both a " +
										START_DOCUMENT_ELEMENT + " tag and a " +
										START_SUBJECTIVE_VIEW + " tag");
			}
		}
		if (element.equals(START_SUBJECTIVE_VIEW)) {
			if (nDocumentEncountered) {
				throw new SAXException("An individual load file cannot contain both a " +
										START_DOCUMENT_ELEMENT + " tag and a " +
										START_SUBJECTIVE_VIEW + " tag");
			}
		}
	}

    *//**
     * Call back method to assign the load guid for error reporting.
     * 
     * @param gd	the load guid.
     *//*
    public void setLoadGuid(String gd) {
    	loadGuid = gd;
    }
    
    *//**
     * Depending on where a comment is found, set the appropriate
     * load builders.
     * <p>
     * Comments found in the n-docbody section of the xml document need only
     * to get reported to the doc builder.  Likewise for the meta builder.
     * This method sets the appropriate builder types when a comment is encountered.
     * By doing this we ensure that the builders where the comment is located
     * get called.
     * </p>
     * @return	an integer that represents all of the load builders interested in the event.
     *//*
    private int getBuilders() {
		int iBuilders = 0;
		int bSize = interestedBuilders.size();
		for (int i=0;i<bSize;i++) {
			int builder = interestedBuilders.get(i);
			if (builder == EventDocDefs.BUILDER_TYPE_DOC) {
				if (inDocBodyFlag) {
					iBuilders |= builder;
				}
			} else if (builder == EventDocDefs.BUILDER_TYPE_META) {
				if (inMetadataFlag) {
					iBuilders |= builder;
				}
			} else if (builder == EventDocDefs.BUILDER_TYPE_WPOS){
				if (inDocBodyFlag) {
					iBuilders |= builder;
				}
			} else {
				iBuilders |= builder;
			}
		}
		return iBuilders;
    }
    
    *//**
     * Set the load file name.  If the n-document control attribute is
     * set to partial the file name must have an .upd.xml extension.
     * 
     * @param name load file name.
     *//*
    public void setLoadFileName(String name) {
    	mLoadFileName = name;
    }
    
    *//**
     * Encapsulate section rules for valid load files.  DOC content only.
     * loadcontenttype defines one of three behaviors.
     * loadActionType contains the content type, doc, toc, nims, etc.
     * control contains value of control attribute.
     * startTagElementCounter, docTagElementCounter, mmTagElementCounter,
     *     metaTagElementCounter, metaDocTagElementCounter indicate what sections were in the load file.
     *     
     * From the load matrix, the 4 builders MetaDoc, Doc, Metadata, MM are represented in order by Y or N.
     * For example, NYYN indicates that the document in the load file does not contain MetaDoc, does contain Doc and Metadata, and no MM.
     *//*
    private void checkSectionRules() throws SAXException, LoadErrorException, LoadWarningException {
    	int builderEmitEventMask = EventDocDefs.BUILDER_TYPE_ALL;   	

    	if (!loadActionType.equals(DOCACTION))
    		return;

    	// Check for more than one start doc, start meta, or start metadoc tags.
    	if (startTagElementCounter > 1) {
    		throw new SAXException("There is more than one start doc tag (n-docbody, n-include)" +
    				" or start mm tag (n-blobref,n-mmfile) in the document, guid = " + docGuid);
    	}

    	if (metaTagElementCounter > 1) {
    		throw new SAXException("There is more than one start metadata tag (n-metadata) in the document, guid = " + docGuid);
    	}

    	if (metaDocTagElementCounter > 1) {
    		throw new SAXException("There is more than one start metadoc tag (n-metadoc) in the document, guid = " + docGuid);
    	}

    	if (loadcontenttype.equals(LOAD_CONTENT_TYPE_ALL)) {
    		if (control.equals(DOCUMENT_ADD)) {
    			if ((metaDocTagElementCounter == 1) && (docTagElementCounter == 0)) {
    				throw new SAXException("n-metadoc not allowed without a n-docbody, guid = " + docGuid);
    			}
    			if ((metaTagElementCounter == 1) && (startTagElementCounter == 0)) {
    				throw new SAXException("n-metadata not allowed without a n-docbody, guid = " + docGuid);    				
    			}
    			if (startTagElementCounter + metaTagElementCounter + metaDocTagElementCounter == 0) {
    				throw new SAXException("Nothing to load, guid = " + docGuid);    				    				
    			}
    		}
    		
    	} else if (loadcontenttype.equals(LOAD_CONTENT_TYPE_METADOC)) {
    		if ((startTagElementCounter == 1) || (metaTagElementCounter == 1)) {
    			throw new SAXException("n-docbody, n-blobref, n-mmfile, n-metadata" +
    					" not allowed in a MetaDoc only load file, guid = " + docGuid);
    		}

    		if (control.equals(DOCUMENT_ADD)) {
        		if (metaDocTagElementCounter == 1) {
        			// YNNN
        			// DocLoc is needed for the Doc guid check.
        			builderEmitEventMask = EventDocDefs.BUILDER_TYPE_METADOC |
        								   EventDocDefs.BUILDER_TYPE_DOCLOC;
        		} else {
        			// NNNN
        			builderEmitEventMask &= 0;
        		}    			
    		} else if (control.equals(DOCUMENT_DELETE)) {
    			builderEmitEventMask = EventDocDefs.BUILDER_TYPE_METADOC;
    		}

    	} else if (loadcontenttype.equals(LOAD_CONTENT_TYPE_SECTIONAL)) {
    		if (control.equals(DOCUMENT_ADD)) {
		        
        		if (startTagElementCounter + metaTagElementCounter + metaDocTagElementCounter == 0) {
        			// NNNN
        			throw new SAXException("Nothing to load, guid = " + docGuid);
        		}

    			if (metaDocTagElementCounter == 0) {
    				// NXXX - leave MetaDoc unchanged if not in load file
    				builderEmitEventMask &= ~EventDocDefs.BUILDER_TYPE_METADOC;
    			}
    			if (docTagElementCounter == 0) {
    				// XNXX - leave Doc unchanged if not in load file
    				builderEmitEventMask &= ~EventDocDefs.BUILDER_TYPE_DOC;    				
    			}
    			if (metaTagElementCounter == 0) {
    				// XXNX - leave Meta unchanged if not in load file
    				builderEmitEventMask &= ~EventDocDefs.BUILDER_TYPE_META;    				
    			}
    			if (mmTagElementCounter == 0) {
    				// XXXN - leave MM unchanged if not in load file
    				builderEmitEventMask &= ~EventDocDefs.BUILDER_TYPE_MM;
    			}
    			
    			if (mmTagElementCounter == 1) {
    				if ((docTagElementCounter == 1) || (metaDocTagElementCounter == 1)) {
    					throw new SAXException(("There is more than one start doc tag (n-docbody, n-include)" +
    		    				" or start mm tag (n-blobref,n-mmfile) in the document, guid = " + docGuid));
    				}
    			}

    			if ((metaDocTagElementCounter == 1) && (startTagElementCounter == 0) && (metaTagElementCounter == 0)) {
    				// YNNN
        			// DocLoc is needed for the Doc guid check.
        			builderEmitEventMask = EventDocDefs.BUILDER_TYPE_METADOC |
        								   EventDocDefs.BUILDER_TYPE_DOCLOC;
    				
    				// Tell DocLoc this is a MetaDoc only load for Doc guid check.
    		        Map builderMap = eventDocQueue.getBuildersRef();
    		        for (Iterator builderIt = builderMap.values().iterator(); builderIt.hasNext(); ) {
    		            LoadBuilder builder = (LoadBuilder)builderIt.next();
    		            int builderType = builder.getType();
    		            if (builderType == EventDocDefs.BUILDER_TYPE_DOCLOC) {
        		            builder.setLoadContentType(LOAD_CONTENT_TYPE_METADOC);    		            	
    		            }
    		        }
    		        if (!ParmReader.getParm("AIBE_FIX_INCLUDED", true)) {
    		        	eventDocQueue.setLoadContentType(LOAD_CONTENT_TYPE_METADOC);
    		        }
    		        
    		        eventDoc.setLoadContentType(LOAD_CONTENT_TYPE_METADOC);

    			}
    		}
    	}
    	eventDoc.setBuilderEmitEventMask(builderEmitEventMask);
    }

    *//**
     * Initializes the sax elements to be identified in a load file
     * for this collection.
     * 
     * @param collectionName
     * @throws LoadErrorException
     *//*
    public static void initSaxElements(String collectionName) throws LoadErrorException {
    	initSaxElements(collectionName, null);
    }
    
    *//**
     * Initializes the sax elements to be identified in a load file
     * for this collection.
     * 
     * @param collectionName
     * @param lp
     * @throws LoadErrorException
     *//*
    public static void initSaxElements(String collectionName, LoaderParms lp) throws LoadErrorException {
        elementsIdentified = false;
        try {        	
            //LoadUpdateProcess lup =LoadUpdateProcess.retrieve(collectionName);
            //LoadElementSet xmlElementSet = LoadElementSet.retrieve(lup.getSetName());          
            LoadUpdateProcess lup = CciInfo.getLoadUpdateProcessInfo(collectionName).getLoadUpdateProcessObject();            
            LoadElementSet xmlElementSet = CciInfo.getLoadElementSetInfo(lup.getSetName()).getLoadElementSetObject();
            LoadElement xmlElements[] = xmlElementSet.getLoadElements();
            for(int i = 0; i < xmlElements.length; i++)
            {
                // START_DOC and START_NODE are valid load actions in CCI. 
                if(xmlElements[i].getLoadAction().equalsIgnoreCase("START_LOAD"))
                {
                    LOAD_WRAPPER = xmlElements[i].getElementName();
                    LoadAttribute attributes[] = xmlElements[i].getLoadAttributes();
                    for(int j=0 ; j < attributes.length; j++)
                    {
                        if(attributes[j].getContentType().equalsIgnoreCase("LOAD_ID"))
                        {
                        	LOAD_IDENTIFIER = attributes[j].getAttributeName();
                        }
                        else if(attributes[j].getContentType().equalsIgnoreCase("LOAD_VERIFY_COLLECTION"))
                        {
                        	LOAD_CLIENT_DEFINED_COLLECTION = attributes[j].getAttributeName();
                        }
                        else if(attributes[j].getContentType().equalsIgnoreCase("LOAD_NOTIFY"))
                        {
                        	LOAD_NOTIFY = attributes[j].getAttributeName();
                        }
                    }
                }

                // Loads will have either documents, nodes, NIMS or relationships, not any combination.
                // So the same element name and attribute name variables can be used.
                else if(xmlElements[i].getLoadAction().equalsIgnoreCase("START_DOC"))
                {
                	START_DOCUMENT_ELEMENT = xmlElements[i].getElementName();
                    LoadAttribute attributes[] = xmlElements[i].getLoadAttributes();
                    for(int j=0 ; j < attributes.length; j++)
                    {
                        if(attributes[j].getContentType().equalsIgnoreCase("DOC_ID"))
                        {
                        	DOCUMENT_IDENTIFIER = attributes[j].getAttributeName();
                        }
                        else if(attributes[j].getContentType().equalsIgnoreCase("DOC_CNTRL"))
                        {
                        	DOCUMENT_CONTROL = attributes[j].getAttributeName();
                        }
                        else if(attributes[j].getContentType().equalsIgnoreCase("DOC_ALERT"))
                        {
                        	DOCUMENT_ALERT = attributes[j].getAttributeName();
                        }
                    }
                    loadActionType = DOCACTION;
                }
                else if(xmlElements[i].getLoadAction().equalsIgnoreCase("START_NODE"))
                {
                	START_DOCUMENT_ELEMENT = xmlElements[i].getElementName();
                    LoadAttribute attributes[] = xmlElements[i].getLoadAttributes();
                    for(int j=0 ; j < attributes.length; j++)
                    {
                        if(attributes[j].getContentType().equalsIgnoreCase("NODE_ID"))
                        {
                        	DOCUMENT_IDENTIFIER = attributes[j].getAttributeName();
                        }
                        else if(attributes[j].getContentType().equalsIgnoreCase("NODE_CNTRL"))
                        {
                        	DOCUMENT_CONTROL = attributes[j].getAttributeName();
                        }
                    }
                    loadActionType = TOCACTION;
                }
                else if(xmlElements[i].getLoadAction().equalsIgnoreCase("START_REL"))
                {
                	START_DOCUMENT_ELEMENT = xmlElements[i].getElementName();
                    LoadAttribute attributes[] = xmlElements[i].getLoadAttributes();
                    for(int j=0 ; j < attributes.length; j++)
                    {
                        if(attributes[j].getContentType().equalsIgnoreCase("REL_ID"))
                        {
                        	DOCUMENT_IDENTIFIER = attributes[j].getAttributeName();
                        }
                        else if(attributes[j].getContentType().equalsIgnoreCase("REL_CNTRL"))
                        {
                        	DOCUMENT_CONTROL = attributes[j].getAttributeName();
                        }
                    }
                    loadActionType = RELACTION;
                }
                else if(xmlElements[i].getLoadAction().equalsIgnoreCase("START_NIMS"))
                {
                	// CCI Load_update_proc table contains a row for each collection. This row contain a field called
                	//LOAD_ELEMSET_NAME. For NIMS this field is set to be "nims".
                	
                	//CCI load_element table contains names of load_elements for each load_elemset. For NIMS, this table 
                	//contains a single load_element called "n-nims". This table also contains a load_action for each
                	//load_element. For "n-nims", the load_action is defined to be "START_NIMS".
                	
                	//String constants used in this code segment are defined (or hard coded) in CCI tables.
                	//"NIMS_ID" and "NIMS_CNTRL" are values of the CONTENT_TYPE field in rows for which value of the ATTRIBUTE_NAME
                	//column is equal to "control" and "guid" respectively. For these rows, ELEMENT_NAME column has the value "n-nims"
                	
                	START_DOCUMENT_ELEMENT = xmlElements[i].getElementName();
                    LoadAttribute attributes[] = xmlElements[i].getLoadAttributes();
                    for(int j=0 ; j < attributes.length; j++)
                    {
                        if(attributes[j].getContentType().equalsIgnoreCase("NIMS_ID"))
                        {
                        	DOCUMENT_IDENTIFIER = attributes[j].getAttributeName();
                        }
                        else if(attributes[j].getContentType().equalsIgnoreCase("NIMS_CNTRL"))
                        {
                        	DOCUMENT_CONTROL = attributes[j].getAttributeName();
                        }
                    }
                    loadActionType = NIMSACTION;       
				} else if (xmlElements[i].getLoadAction().equalsIgnoreCase("START_VIEW")) {
					START_SUBJECTIVE_VIEW = xmlElements[i].getElementName();
                }
            }
        }
        catch(CciException ex)
        {
            LoadDMError le = new LoadDMError("CCIException", ex);
            le.log("LOADER", "SaxTranslator.initSaxElements", "");
            
			throw new LoadErrorException("SaxTranslator.initSaxElements","LOAD0000",ex.getMessage());            
        }
        
        try{
        	//Collection col = Collection.retrieve(collectionName);
        	Collection col = CciInfo.getCollectionInfo(collectionName).getCollectionObject();
        	collID = col.getId();
        	rel2Prtnr = col.getRelation2Partner();
        	familyName = col.getCollectionFamilyName().trim();
    	}catch(CciRecordNotFoundException crnfe){
            throw new LoadErrorException("SaxTranslator.initSaxElements","LOAD0000","Collection "+ collectionName +" does not exist");                                        
        }catch(CciException ce){
    		Event e = new Event("RetrieveCollectionFamilyCollections");
            e.put(Event.EVENTLEVEL, Event.CRITICAL);
            e.put("method", "SaxTranslator.initSaxElements");
            e.put(Event.TEXT, ce.getMessage());
            e.putException(ce);
            Log.print(e);
            e = null;
            
            throw new LoadErrorException("SaxTranslator.initSaxElements","LOAD0000",ce.getMessage());                                        
        }

        if(!"".equalsIgnoreCase(familyName)){
        	try{
	        	Collection[] familyCollections = Collection.retrieveAllCollectionsForMainCollection(familyName);
	        	mCFCollections = new HashMap<Integer, String>(familyCollections.length);
	        	for(int cp = 0; cp < familyCollections.length; cp++){
	        		mCFCollections.put(familyCollections[cp].getId(), familyCollections[cp].getRelation2Partner().trim());
	        	}
	        	if(lp != null){
	        		lp.setCollectionFamilyMap(mCFCollections);
	        	}

        	}catch(CciRecordNotFoundException crnfe){
                throw new LoadErrorException("SaxTranslator.initSaxElements","LOAD0000","Collection family "+ familyName +" does not have collections");                                        
        	}catch(CciException ce){

        		Event e = new Event("RetrieveCollectionFamilyCollections");
                e.put(Event.EVENTLEVEL, Event.CRITICAL);
                e.put("method", "SaxTranslator.initSaxElements");
                e.put(Event.TEXT, ce.getMessage());
                e.putException(ce);
                Log.print(e);
                e = null;
                
                throw new LoadErrorException("SaxTranslator.initSaxElements","LOAD0000",ce.getMessage());                                        
        	}
        }
        elementsIdentified = true;
    }
    
    *//**
     * Method validateGuidUniquenessForCollectionFamilyLoad - This method verifies the DOCLOC rules between the
     * current family and the current document GUID which exists in outside of the family
     * and the DOCLOC rules are not matched throws the exception to fail the load
     * 
     * @param currentFamilyDLC		DocLocUpdateProcess object of current family
     * @param currentFamilyOwner	Owner name of the current family 	
     * @param guid					GUID    
     * @parm  DocumentVersionInfo	DocumentVerisonInfo object  
     *//*
    private void validateGuidUniquenessForCollectionFamilyLoad(DocLocUpdateProcess currentFamilyDLC, String currentFamilyOwner, 
    		String guid, DocumentVersionInfo docVersionInfo, String existingColName) throws LoadErrorException, LoadSAXException{
    			
		DocLocUpdateProcess existingColDocloc = null;
		String existingColOwner = null;
    			
		try {
			//Collection existingCollection = Collection.retrieve(docVersionInfo.getCollectionId());
			Collection existingCollection = CciInfo.getCollectionInfo(docVersionInfo.getCollectionId()).getCollectionObject();
			existingColName = existingCollection.getCollectionName();
			existingColOwner = existingCollection.getOwnerName();	
			//existingColDocloc = DocLocUpdateProcess.retrieve(existingColName); 
			existingColDocloc = CciInfo.getDocLocUpdateProcessInfo(existingColName).getDocLocUpdateProcessObject();					
			char currentDoclocType = currentFamilyDLC.getDocLocCheckType();
			int currentDoclocId = currentFamilyDLC.getDocLocId();	
			int exisitngDoclocId = existingColDocloc.getDocLocId();
					
			if(currentDoclocId == exisitngDoclocId){
			
				if(currentDoclocType == GUIDUniqueness.UNIQUENESS_CHECK_TYPE_CHAR_STRONG){					
					if(control != null && control.equalsIgnoreCase(DOCUMENT_DELETE) && currentFamilyOwner.equals(existingColOwner)){						
						return;
					}
					if(docVersionInfo.getDeleteFlag().equalsIgnoreCase("Y") && currentFamilyOwner.equals(existingColOwner)){        					
    					return;
    				}								
					if (loaderParms.isMigrateLoad() && loaderParms.getInitialLoadContentType().equalsIgnoreCase("ExtractLoad")) {
						isOutsideFamily = true;
					} else {
						throw new LoadSAXException("SaxTranslator.validateGuidUniquenessForCollectionFamilyLoad","LOAD0000","GUID "+ guid +" already exists in Collection " + existingColName + " and cannot be loaded to this family");
					}
				}else if(!currentFamilyOwner.equals(existingColOwner)){
					if (loaderParms.isMigrateLoad() && loaderParms.getInitialLoadContentType().equalsIgnoreCase("ExtractLoad")) {
						isOutsideFamily = true;
					} else {
						throw new LoadSAXException("SaxTranslator.validateGuidUniquenessForCollectionFamilyLoad","LOAD0000","GUID "+ guid +" already exists in Collection " + existingColName + " having a different owner and cannot be loaded to this family");						
					}
				}
			}			
					
		} catch (CciException e) {	
			Event event = new Event("validateGuidUniquenessForCollectionFamilyLoad");
			event.put(Event.EVENTLEVEL, Event.DEBUG);
			event.put(Event.TEXT, e.getMessage());
			event.putException(e);
			Log.print(event);
			throw new LoadErrorException("SaxTranslator.validateGuidUniquenessForCollectionFamilyLoad","LOAD0000", "Error while retrieving the DLC for collection "+existingColName);
		}
    }
    
	
	*//**
	 * @param loadProcessingRules 
	 *//*
	public void setLoadProcessingRules(LoadProcessingRulesThreadSafe loadProcessingRules) {
		this.loadProcessingRules = loadProcessingRules;
	}
	
	*//**
	 * @return the loadProcessingRules
	 *//*
	public LoadProcessingRulesThreadSafe getLoadProcessingRules() {
		return loadProcessingRules;
	}
    
    public static void initMicDocCounters(String collectionName, ArrayList<String> micIndexCollections) {
    	micDocCounter.put(collectionName, new MICDocumentCounter());
    	MICDocumentCounter cntr1 = micDocCounter.get(collectionName);
    	int x = cntr1.getDocumentsAdded();
    	
 		for (String indexCollectionName : micIndexCollections ) {
 			micDocCounter.put(indexCollectionName, new MICDocumentCounter());
 	    	MICDocumentCounter cntr2 = micDocCounter.get(collectionName);
 	    	int xx = cntr2.getDocumentsAdded();
		}
    }

	*//**
	 * @param micTargetCollection the micTargetCollection to set
	 *//*
	public void setMicTargetCollection(String micTargetCollection) {
		this.micTargetCollection = micTargetCollection;
	}

	*//**
	 * @return the micTargetCollection
	 *//*
	public String getMicTargetCollection() {
		return micTargetCollection;
	}

	*//**
	 * @param micLoad the micLoad to set
	 *//*
	public void setMicLoad(boolean micLoad) {
		this.micLoad = micLoad;
	}

	*//**
	 * @return the micLoad
	 *//*
	public boolean isMicLoad() {
		return micLoad;
	}

	*//**
	 * @return the micDocCounter
	 *//*
	public static HashMap <String, MICDocumentCounter> getMicDocCounter() {
		return micDocCounter;
	}
	
	*//**
	 * Validates and sets (paired) target collection to MICGroup 
	 * @param view_target_collection
	 * @param view_name
	 * @throws Exception
	 *//*
	private void validateAndSetSubjectiveViewForMICGroup(
			String view_target_collection, String view_name, MICGroup micGroup, boolean isContenCollection ) throws Exception {
		if (micLoad) {
			firstSubjectiveViewTargetCollection = view_target_collection;
			Set<String> configuredCollectionSet = new HashSet<String>(micGroup.getConfiguredCollections());
			String CollectionName = loaderParms.getCollectionName();
			Collection reloadCollection = CciInfo.getCollectionInfo(collectionName).getCollectionObject();
			if (configuredCollectionSet.contains(view_target_collection)) {
				micGroup.setSubjectiveViewTargetCollection(view_target_collection);
				if(isContenCollection){
					Event event1 = new Event("debug-info");
					event1.put(Event.EVENTLEVEL, Event.DEBUG);
					event1.put("method","SAXTranslator.validateAndSetSubjectiveViewForMICGroup");
					event1.put(Event.COLLECTION,collectionName);
					event1.put(Event.TEXT,view_target_collection + ": is set as Subjective View Target Collection in MICGroup "	+ collectionName);
					LoadLog.logEvent(event1);
					event1 = null;
				}
				return;
			} else {
				// If target collection is  paired 						
				if ( DataManagement.isCollectionInReloadState(CollectionName)) {
					Collection viewTargetCollection = null;
					Collection pairedTargetCollection = null;
					try{
						viewTargetCollection = CciInfo.getCollectionInfo(view_target_collection).getCollectionObject();
						pairedTargetCollection = CciInfo.getCollectionInfo(-viewTargetCollection.getId()).getCollectionObject();
					}catch(CciRecordNotFoundException e){
						throw new LoadErrorException("SaxTranslator.validateAndSetSubjectiveViewForMICGroup","LOAD0000"," Either target collection: " +  view_target_collection + " or its paired collection does not exist in CCI. "	+ collectionName);
					}
					if (configuredCollectionSet.contains(pairedTargetCollection.getCollectionName())) {
						micGroup.setSubjectiveViewTargetCollection(pairedTargetCollection.getCollectionName());
						if(isContenCollection){
							Event event1 = new Event("debug-info");
							event1.put(Event.EVENTLEVEL, Event.DEBUG);
							event1.put("method","SAXTranslator.validateAndSetSubjectiveViewForMICGroup");
							event1.put(Event.COLLECTION,collectionName);
							event1.put(Event.TEXT," Paired Collection:" + pairedTargetCollection.getCollectionName() + " is set as Subjective View Target Collection for target collection: " +  view_target_collection + " in MICGroup "	+ collectionName);
							LoadLog.logEvent(event1);
							event1 = null;
						}
						return;
					}
					else{
						if(reloadCollection.isReloadCollection()){
							throw new LoadErrorException("SaxTranslator.validateAndSetSubjectiveViewForMICGroup","LOAD0000"," MultiIndex SubjectiveView attempted - Paired Target collection " + pairedTargetCollection.getCollectionName() + " is not part of Mass reload MICG collection: "+ collectionName);
						}else{
							throw new LoadErrorException("SaxTranslator.validateAndSetSubjectiveViewForMICGroup","LOAD0000"," MultiIndex SubjectiveView attempted - Paired Target collection " + pairedTargetCollection.getCollectionName() + " is not part of  MICG collection: "+ collectionName);
						}
					}
				}
				else {
					throw new LoadErrorException("SaxTranslator.validateAndSetSubjectiveViewForMICGroup","LOAD0000"," MultiIndex SubjectiveView attempted -  Target collection " + view_target_collection + " is not part of  MICG collection: "+ collectionName);
				}
			}
		}
	}
}
*/