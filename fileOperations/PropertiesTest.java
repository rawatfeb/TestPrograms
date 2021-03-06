package fileOperations;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesTest {
	
	public static void main(String...args){
		getPoolOrDomainHtml(); 
	}
	
	private static String getPoolOrDomainHtml() {
		String CACHE_PROPERTY_FILE = "/home/swat/services/DBConnectionUtiltyTest/DBConnectionUtiltyCache.properties";
		String DOMAIN_MODE = "domainMode";
		String MODE="poolMode";
		String POOL_OR_DOMAIN="AGENTMANAGER";
		InputStream inStream = null;
		Path path = Paths.get(CACHE_PROPERTY_FILE);
		String poolCache = "[AGENTMANAGER, ATLASJMS_TITANJMS, CASTORJMS_POLLUXJMS, CSLOC.PROD, CSLOC.PROD.POOLA, CSLOC.PROD.POOLB, CSLOC.PROD.POOLC, Cannondale/Cannonford, Cannonfalls/Cannonfield, DCS, DOC.CLIENT, DOC.COBALT, DOC.COBALT.STREAMING, DOC.PROD, DOC.PROD.POOLA, DOC.PROD.POOLB, DOC.PROD.POOLC, DOC.PROD.POOLD, DOC.PROD.POOLE, DOC.PROD.WLCLIP, DOC.QC.POOLA		, DOC.UK.PROD, DUDLEYJMS_DUNDEEJMS, DURBINJMS_DYSTARTJMS, DsDomainPair_DUNDEEJMS/DUDLEYJMS, DsDomainPair_DUNMOREJMS/DUNLOWJMS, DsDomainPair_DUQUESNEJMS/DUNREITHJMS, DsDomainPair_DURBINJMS/DYSARTJMS, DsDomainPair_GLENBROOKJMS/GLENVILLEJMS, DsDomainPair_HADOCKET1AJMS/HADOCKET1BJMS, DsDomainPair_HADOCKET2AJMS/HADOCKET2BJMS, DsDomainPair_HALPWLNFOLD1AJMS/HALPWLNFOLD1BJMS, DsDomainPair_HALPWLNFOLD2AJMS/HALPWLNFOLD2BJMS, DsDomainPair_LINDENWOLDJMSLINDENHURSTJMS, DsDomainPair_PAWTUCKETJMS/PALCOJMS, DsDomainPair_PECONICJMS/PARAMUSJMS, DsDomainPair_PELIONJMS/PEGRAMJMS, DsDomainPair_PIERSONJMS/PULLMANJMS, DsDomainPair_PIPESTONEJMS/PENNINGTONJMS, DsDomainPair_PITTSBAYJMS/PITTSRIVERJMS, DsDomainPair_PURLINGJMS/PURDYJMS, DsDomainPair_SUNRISEJMS/SUNSETJMS, DsDomainPair_SUTTERJMS/SUTTONJMS, DsDomainPair_TERRACEJMS/TEGNERJMS, DsDomainPair_WAVERLYJMS/WALKERJMS, DsDomainPair_WAYPORTJMS/WAYCREEKJMS, DsDomainPair_WAYSIDEJMS/WAYZATAJMS, DsDomainPair_WEIRGORJMS/WEINERTJMS, DsDomainPair_WEISERJMS/WYACONDAJMS, DsDomainPair_WELCOMEJMS/WEAVERJMS, DsDomainPair_WESTRIVERJMS/WESTTOWNJMS, DsDomainPair_WEWOKAJMS/WENONAJMS, DsDomainPair_WHEELWRIGHTJMS/WHITEWATERJMS, DsDomainPair_WILKERSONJMS/WELLERSBURGJMS, DsDomainPair_WILKESBOROJMSWILKESVILLEJMS, DsDomainPair_WINHALLJMS/WORLEYJMS, DsDomainPair_WRIGHTJMS/WINSTONJMS, DsDomainPr_WESTERNPORTJMS/WESTERVILLEJMS, Dunlow/Dunmore, Glenbrook/Glenville, HBMONITOR.PROD, INDEXWHEEL.CLIENT, INDEXWHEEL.CLIENT.DIALOG, INDEXWHEEL.PROD, INDEXWHEEL.PROD.POOLA, INDEXWHEEL.PROD.POOLB, INDEXWHEEL.PROD.POOLD, INDEXWHEEL.PROD.POOLE, INDEXWHEEL.PROD.POOLF, INDEXWHEEL.VERIFY.DIALOG, IRCJMS, LINDENWOLD/LINDENHURST, METADOC.PROD, METADOC.PROD.POOLA, METADOC.PROD.POOLB, MORELIKETHIS.CLIENT, MORELIKETHIS.PROD, Millseat/Millstone, NIMS.PROD, NIMS.PROD.POOLA, NIMS.PROD.POOLAPOOLB, NIMS.PROD.POOLAPOOLC, NIMS.PROD.POOLB, NORM.CLIENT, NORM.PROD, NORM.PROD.POOLA, NORM.PROD.POOLAPOOLC, NORM.PROD.POOLB, NORM.PROD.POOLBPOOLG, NORM.PROD.POOLC, NORM.PROD.POOLD, NORM.PROD.POOLE, NORM.PROD.POOLF, NORM.PROD.POOLG, NORM.PROD.POOLH, NORM.PROD.POOLJ, NORM.PROD.PREMISE, NOT A POOL, NOT NEEDED, trailservice.client, trailservice.prod, weaver - welcome DS, UTILITY.CLIENT, UTILITY.DEDUP.PROD, UTILITY.PROD, UTILITY.PROD.POOLA, UTILITY.PROD.POOLC, UTILITY.PROD.WLCLIP, UTILITY.QC.POOLA, VERIFY_AMARILLO, VERIFY_COOLEY, VERIFY_DALTON, VERIFY_DUDLEYJMS, VERIFY_DUNDEEJMS, VERIFY_DUNLOWJMS, VERIFY_DUNMOREJMS, VERIFY_DUNNELL, VERIFY_DURBINJMS, VERIFY_DYSARTJMS, VERIFY_GLENBROOK, VERIFY_GLENVILLE, VERIFY_LAREDO, VERIFY_LINDENHURSTJMS, VERIFY_LINDENWOLDJMS, VERIFY_MILLSEAT, VERIFY_MILLSTONE, VERIFY_MUNCH, VERIFY_PENNINGTONJMS, VERIFY_PIERSONJMS, VERIFY_PIPESTONEJMS, VERIFY_PITTSBAYJMS, VERIFY_PITTSRIVERJMS, VERIFY_PULLMANJMS, VERIFY_PURDYJMS, VERIFY_PURLINGJMS, VERIFY_SUNRISEJMS, VERIFY_SUNSETJMS, VERIFY_SUTTERJMS, VERIFY_SUTTONJMS, VERIFY_TEGNERJMS, VERIFY_TERRACEJMS, VERIFY_VESTAJMS, VERIFY_VILLARDJMS, VERIFY_WALKERJMS, VERIFY_WAVERLYJMS, VERIFY_WEAVERJMS, VERIFY_WELBYJMS, VERIFY_WELCOMEJMS, VERIFY_WENONAJMS, VERIFY_WESTERNPORTJMS, VERIFY_WESTERVILLEJMS, VERIFY_WEWOKAJMS, VERIFY_WILKESBOROJMS, VERIFY_WILKESVILLEJMS, VERIFY_WINSTONJMS, VERIFY_WRIGHTJMS, Vesta/Villard, WALKERJMS_WAVERLYJMS, Wayport/Waycreek, Wayside/Wayzata, Weirgor/Weinert, Weiser/Wyaconda, Wenona/Wewoka, Westernport/Westerville, Westriver/Westtown, Wilkerson/Wellersburg, Wilkesboro/Wilkesville, Winhall/Worley, Wright/Winston, duquesne/dunreith, lager.client, lager.prod, prefs.client, prefs.prod, pricing.client, pricing.prod, prismsec.client, prismsec.prod, router.client, router.prod, PALCOJMS_PAWTUCKETJMS, PARAMUSJMS_PECONICJMS, PEARLJMS_RUBYJMS, PEGRAMJMS_PELIONJMS, PENNINGTONJMS_PIPESTONEJMS, PERFQCSEARCH, PUBLISHING.PROD, PairedDSDomain_DURBINJMS/DYSARTJMS, Pittsbay/Pittsriver, Pullman/Pierson, Purdy/Purling, QUICKDELETE.PROD, REPLICATOR.PROD, ROUTER.CLIENT, ROUTER.PROD, SEARCH.CLIENT, SEARCH.CLIENT.DIALOG, SEARCH.CLIENT.IPS, SEARCH.CLIENT.JAPAN, SEARCH.CLIENT.JONESBORO, SEARCH.CLIENT.N, SEARCH.CLIENT.POOLG, SEARCH.CLIENT.SAEGIS, SEARCH.CLIENT.SAYBROOK, SEARCH.CLIENT.SHARED, SEARCH.CLIENT.TF, SEARCH.CLIENT.WACONIA, SEARCH.CLIENT.WELBYDS, SEARCH.CLIENT.WL, SEARCH.DEV.ADREHA , SEARCH.PROD.DGCLIP, SEARCH.PROD.POOLA, SEARCH.PROD.POOLAPOOLB, SEARCH.PROD.POOLB, SEARCH.PROD.POOLC, SEARCH.PROD.POOLD, SEARCH.PROD.POOLE, SEARCH.PROD.POOLF, SEARCH.PROD.POOLG, SEARCH.PROD.POOLH, SEARCH.PROD.POOLJ, SEARCH.PROD.POOLK, SEARCH.PROD.POOLL, SEARCH.PROD.POOLM, SEARCH.PROD.POOLN, SEARCH.PROD.POOLO, SEARCH.PROD.POOLP, SEARCH.PROD.POOLQ, SEARCH.PROD.POOLR, SEARCH.PROD.POOLRPOOLV, SEARCH.PROD.POOLS, SEARCH.PROD.POOLU, SEARCH.PROD.POOLV, SEARCH.PROD.POOLW, SEARCH.PROD.POOLX, SEARCH.PROD.POOLY, SEARCH.PROD.POOLZ, SEARCH.QC.DEBUG, SEARCH.QC.POOLA, SEARCHENGINE.POOL1A, SEARCHENGINE.POOLA, SUDS.CLIENT, SUDS.PROD, Sunset - Sunrise DS, Sutter/Sutton, TAFT/TANSEM, TEGNER/TERRACE, TFREALTIME, TOC.CLIENT, TOC.PROD, TOC.PROD.POOLA, TOC.PROD.POOLAPOOLBPOOLD, TOC.PROD.POOLB]";
		String domainCache = "[WLPHA1A,cooley,PRLOADA,dummy,tf_real,PRLOADB,WLLOADB,WLPHA2B,SHLOADB,WLPHA2A,WLLOADA,buffalo,GSILOAD,WLPHA1B,togo]";
		Properties properties = new Properties();
		String pool_or_domain_cache="[]";
		try {
			inStream = new FileInputStream(path.toFile());
				properties.load(inStream);
			inStream.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		if (DOMAIN_MODE.equalsIgnoreCase(MODE)) {
			pool_or_domain_cache = properties.getProperty("domainCache",domainCache);
		} else {
			pool_or_domain_cache = properties.getProperty("poolCache",poolCache);
		}
		
		String parseable = pool_or_domain_cache.substring(1, pool_or_domain_cache.length() - 1);
		String[] pools_or_domains = parseable.split(",");
		StringBuilder pool_or_domain_html = new StringBuilder(
				"<SELECT ID=\"pool_or_domain\" name=\"pool_or_domain\"><OPTION value=\"dummy\">----Select----</OPTION>");
		for (String pool_or_domain : pools_or_domains) {
			pool_or_domain_html.append("<OPTION value=\"" + pool_or_domain.trim() + "\">" + pool_or_domain + "</OPTION>");
		}
		pool_or_domain_html.append("</SELECT>");
		String previousSelectedPool = POOL_OR_DOMAIN;
		System.out.println("previousSelectedPool=" + previousSelectedPool);
		if (null == previousSelectedPool || previousSelectedPool.isEmpty()) {
			previousSelectedPool = "dummy";
		}
		System.out.println("previousSelectedPool=" + previousSelectedPool);
		int index = pool_or_domain_html.indexOf("<OPTION value=\"" + previousSelectedPool + "\">");
		if (index != -1) {
			pool_or_domain_html.replace(index, index + ("<OPTION value=\"" + previousSelectedPool + "\">").length(),
					"<OPTION selected value=\"" + previousSelectedPool + "\">");
		}
		System.out.println(pool_or_domain_html);
		return pool_or_domain_html.toString();
	}
}
