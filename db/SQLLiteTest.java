package db;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Set;

public class SQLLiteTest {
	private static final String PROPERTY_SEPARATOR = ":";
	private static final String COLLECTION_NAME="collection_name";
	private static final String DOC_RESOURCE_NAME="doc_resource_name";
	private static final String NIMS_SCHEMA="nims_schema";
	private static final String NIMS_RESOURCE="nims_resource";
	private static final String NIMS_DOMAIN="nims_domain";
	private static final String TOC_SCHEMA="toc_schema";
	private static final String TOC_RESOURCE="toc_resource";
	private static final String REL_SCHEMA="rel_schema";
	private static final String REL_RESOURCE="rel_resource";
	private static final String REL_DOMAIN="rel_domain";
	private static final String METADOC_SCHEMA="metadoc_schema";
	private static final String METADOC_RESOURCE="metadoc_resource";
	private static final String METADOC_DOMAIN="metadoc_domain";
	private static final String META_SCHEMA_NAME="meta_schema_name";
	private static final String META_RESOURCE_NAME="meta_resource_name";
	private static final String DOC_SCHEMA_NAME="doc_schema_name";
	
	public static void main(String args[]) throws SQLException, IOException {
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:CollectionDomainInfo.db");// test.db
																		// file
																		// will
																		// got
																		// created
																		// at
																		// the
																		// workspace
																		// level
			stmt = con.createStatement();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
		test();
		createTable(stmt);
	}

	private static void createTable(Statement stmt) throws SQLException, IOException {
		String QUERY = "create table if not exists CollectionDomainInfo( collection_name   varchar2 (35) primary key, doc_resource_name   varchar2 (35), doc_schema_name   varchar2 (35), meta_resource_name   varchar2 (35), meta_schema_name   varchar2 (35), metadoc_domain   varchar2 (35), metadoc_resource   varchar2 (35), metadoc_schema   varchar2 (35), rel_domain   varchar2 (35), rel_resource   varchar2 (35), rel_schema   varchar2 (35), toc_resource   varchar2 (35), toc_schema   varchar2 (35), nims_domain   varchar2 (35), nims_resource   varchar2 (35), nims_schema varchar2 (35))";
		boolean status = stmt.execute(QUERY);
		//System.out.println(status);

		// test it out as well
		// this.getClass().getResourceAsStream("/sample.properties");#sthash.whbUgGvJ.dpuf
		FileInputStream fis = new FileInputStream("CollectionDomainUtility_client.properties");
		Properties prop = new Properties();
		prop.load(fis);
		Set<Object> keyset = prop.keySet();
int counter=0;
		for (Object key : keyset) {
			CollcetionDomainInfoBean bean = new CollcetionDomainInfoBean(prop.getProperty((String)key));
			bean.setCollcetion_name((String)key);
			counter++;
			String query=bean.insertQueryBuilder();
			stmt.addBatch(query);
		}
		stmt.executeBatch();
System.out.println(counter);
	}

	private static class CollcetionDomainInfoBean {
		private String collection_name;
		private String doc_resource_name;
		private String doc_schema_name;
		private String meta_resource_name;
		private String meta_schema_name;
		private String metadoc_domain;
		private String metadoc_resource;
		private String metadoc_schema;
		private String rel_domain;
		private String rel_resource;
		private String rel_schema;
		private String toc_resource;
		private String toc_schema;
		private String nims_domain;
		private String nims_resource;
		private String nims_schema;

		CollcetionDomainInfoBean() {
		}

		CollcetionDomainInfoBean(String value) {

			// building bean object by the value string
			if (value != null && !value.isEmpty()) {
				String[] values = value.split(",");

				for (String eachValue : values) {
					String key = eachValue.split(PROPERTY_SEPARATOR)[0].trim();
					if (!key.isEmpty()) {
						switch (key) {
						case "doc_resource_name":
							this.doc_resource_name = eachValue.split(PROPERTY_SEPARATOR)[1];
							break;
						case "doc_schema_name":
							this.doc_schema_name = eachValue.split(PROPERTY_SEPARATOR)[1];
							break;
						case "meta_resource_name":
							this.meta_resource_name = eachValue.split(PROPERTY_SEPARATOR)[1];
							break;
						case "meta_schema_name":
							this.meta_schema_name = eachValue.split(PROPERTY_SEPARATOR)[1];
							break;
						case "metadoc_domain":
							this.metadoc_domain = eachValue.split(PROPERTY_SEPARATOR)[1];
							break;
						case "metadoc_resource":
							this.metadoc_resource = eachValue.split(PROPERTY_SEPARATOR)[1];
							break;
						case "metadoc_schema":
							this.metadoc_schema = eachValue.split(PROPERTY_SEPARATOR)[1];
							break;
						case "rel_domain":
							this.rel_domain = eachValue.split(PROPERTY_SEPARATOR)[1];
							break;
						case "rel_resource":
							this.rel_resource = eachValue.split(PROPERTY_SEPARATOR)[1];
							break;
						case "rel_schema":
							this.rel_schema = eachValue.split(PROPERTY_SEPARATOR)[1];
							break;
						case "toc_resource":
							this.toc_resource = eachValue.split(PROPERTY_SEPARATOR)[1];
							break;
						case "toc_schema":
							this.toc_schema = eachValue.split(PROPERTY_SEPARATOR)[1];
							break;
						case "nims_domain":
							this.nims_domain = eachValue.split(PROPERTY_SEPARATOR)[1];
							break;
						case "nims_resource":
							this.nims_resource = eachValue.split(PROPERTY_SEPARATOR)[1];
							break;
						case "nims_schema":
							this.nims_schema = eachValue.split(PROPERTY_SEPARATOR)[1];
							break;
						default:
							System.out.println("detected unrecognized key while building bean from string: " + key);
							break;
						}
					}
				}
			}
		}

		
		public String insertQueryBuilder(){
			String table="CollectionDomainInfo";
	        StringBuilder QUERY=new StringBuilder();
	        QUERY.append("INSERT INTO "+table+" (");
	        StringBuilder columnBuilder=new StringBuilder();
	        StringBuilder valueBuilder=new StringBuilder(" values(");
	        
	        if (collection_name != null && !collection_name.isEmpty()) {
	        	columnBuilder.append(COLLECTION_NAME);
	        	columnBuilder.append(",");
	        	valueBuilder.append("'"+collection_name+"'");
	        	valueBuilder.append(",");
			}
	        if (doc_resource_name != null && !doc_resource_name.isEmpty()) {
	        	columnBuilder.append(DOC_RESOURCE_NAME);
	        	columnBuilder.append(",");
	        	valueBuilder.append("'"+doc_resource_name+"'");
	        	valueBuilder.append(",");
			}
			if (doc_schema_name != null && !doc_schema_name.isEmpty()) {
				columnBuilder.append(DOC_SCHEMA_NAME);
	        	columnBuilder.append(",");
	        	valueBuilder.append("'"+doc_schema_name+"'");
	        	valueBuilder.append(",");
			}
			if (meta_resource_name != null && !meta_resource_name.isEmpty()) {
				columnBuilder.append(META_RESOURCE_NAME);
	        	columnBuilder.append(",");
	        	valueBuilder.append("'"+meta_resource_name+"'");
	        	valueBuilder.append(",");
			}
			if (meta_schema_name != null && !meta_schema_name.isEmpty()) {
				columnBuilder.append(META_SCHEMA_NAME);
	        	columnBuilder.append(",");
	        	valueBuilder.append("'"+meta_schema_name+"'");
	        	valueBuilder.append(",");
			}
			if (metadoc_domain != null && !metadoc_domain.isEmpty()) {
				columnBuilder.append(METADOC_DOMAIN);
	        	columnBuilder.append(",");
	        	valueBuilder.append("'"+metadoc_domain+"'");
	        	valueBuilder.append(",");
			}
			if (metadoc_resource != null && !metadoc_resource.isEmpty()) {
				columnBuilder.append(METADOC_RESOURCE);
	        	columnBuilder.append(",");
	        	valueBuilder.append("'"+metadoc_resource+"'");
	        	valueBuilder.append(",");
			}
			if (metadoc_schema != null && !metadoc_schema.isEmpty()) {
				columnBuilder.append(METADOC_SCHEMA);
	        	columnBuilder.append(",");
	        	valueBuilder.append("'"+metadoc_schema+"'");
	        	valueBuilder.append(",");
			}
			if (rel_domain != null && !rel_domain.isEmpty()) {
				columnBuilder.append(REL_DOMAIN);
	        	columnBuilder.append(",");
	        	valueBuilder.append("'"+rel_domain+"'");
	        	valueBuilder.append(",");
			}
			if (rel_resource != null && !rel_resource.isEmpty()) {
				columnBuilder.append(REL_RESOURCE);
	        	columnBuilder.append(",");
	        	valueBuilder.append("'"+rel_resource+"'");
	        	valueBuilder.append(",");
			}
			if (rel_schema != null && !rel_schema.isEmpty()) {
				columnBuilder.append(REL_SCHEMA);
	        	columnBuilder.append(",");
	        	valueBuilder.append("'"+rel_schema+"'");
	        	valueBuilder.append(",");
			}
			if (toc_resource != null && !toc_resource.isEmpty()) {
				columnBuilder.append(TOC_RESOURCE);
	        	columnBuilder.append(",");
	        	valueBuilder.append("'"+toc_resource+"'");
	        	valueBuilder.append(",");
			}
			if (toc_schema != null && !toc_schema.isEmpty()) {
				columnBuilder.append(TOC_SCHEMA);
	        	columnBuilder.append(",");
	        	valueBuilder.append("'"+toc_schema+"'");
	        	valueBuilder.append(",");
			}
			if (nims_domain != null && !nims_domain.isEmpty()) {
				columnBuilder.append(NIMS_DOMAIN);
	        	columnBuilder.append(",");
	        	valueBuilder.append("'"+nims_domain+"'");
	        	valueBuilder.append(",");
			}
			if (nims_resource != null && !nims_resource.isEmpty()) {
				columnBuilder.append(NIMS_RESOURCE);
	        	columnBuilder.append(",");
	        	valueBuilder.append("'"+nims_resource+"'");
	        	valueBuilder.append(",");
			}
			if (nims_schema != null && !nims_schema.isEmpty()) {
				columnBuilder.append(NIMS_SCHEMA);
	        	columnBuilder.append(",");
	        	valueBuilder.append("'"+nims_schema+"'");
	        	valueBuilder.append(",");
			}
			
			
			QUERY.append(columnBuilder.substring(0,columnBuilder.lastIndexOf(",")));
			QUERY.append(")");
			QUERY.append(valueBuilder.substring(0,valueBuilder.lastIndexOf(",")));
			QUERY.append(")");
			
			
			return QUERY.toString();
		}
		
		
		@Override
		public String toString() {
			StringBuilder valueBuilder = new StringBuilder();

			if (doc_resource_name != null && !doc_resource_name.isEmpty()) {
				valueBuilder.append("doc_resource_name" + PROPERTY_SEPARATOR + doc_resource_name);
			}
			if (doc_schema_name != null && !doc_schema_name.isEmpty()) {
				valueBuilder.append(",doc_schema_name" + PROPERTY_SEPARATOR + doc_schema_name);
			}
			if (meta_resource_name != null && !meta_resource_name.isEmpty()) {
				valueBuilder.append(",meta_resource_name" + PROPERTY_SEPARATOR + meta_resource_name);
			}
			if (meta_schema_name != null && !meta_schema_name.isEmpty()) {
				valueBuilder.append(",meta_schema_name" + PROPERTY_SEPARATOR + meta_schema_name);
			}
			if (metadoc_domain != null && !metadoc_domain.isEmpty()) {
				valueBuilder.append(",metadoc_domain" + PROPERTY_SEPARATOR + metadoc_domain);
			}
			if (metadoc_resource != null && !metadoc_resource.isEmpty()) {
				valueBuilder.append(",metadoc_resource" + PROPERTY_SEPARATOR + metadoc_domain);
			}
			if (metadoc_schema != null && !metadoc_schema.isEmpty()) {
				valueBuilder.append(",metadoc_schema" + PROPERTY_SEPARATOR + metadoc_schema);
			}
			if (rel_domain != null && !rel_domain.isEmpty()) {
				valueBuilder.append(",rel_domain" + PROPERTY_SEPARATOR + rel_domain);
			}
			if (rel_resource != null && !rel_resource.isEmpty()) {
				valueBuilder.append(",rel_resource" + PROPERTY_SEPARATOR + rel_resource);
			}
			if (rel_schema != null && !rel_schema.isEmpty()) {
				valueBuilder.append(",rel_schema" + PROPERTY_SEPARATOR + rel_schema);
			}
			if (toc_resource != null && !toc_resource.isEmpty()) {
				valueBuilder.append(",toc_resource" + PROPERTY_SEPARATOR + toc_resource);
			}
			if (toc_schema != null && !toc_schema.isEmpty()) {
				valueBuilder.append(",toc_schema" + PROPERTY_SEPARATOR + toc_schema);
			}
			if (nims_domain != null && !nims_domain.isEmpty()) {
				valueBuilder.append(",nims_domain" + PROPERTY_SEPARATOR + nims_domain);
			}
			if (nims_resource != null && !nims_resource.isEmpty()) {
				valueBuilder.append(",nims_resource" + PROPERTY_SEPARATOR + nims_resource);
			}
			if (nims_schema != null && !nims_schema.isEmpty()) {
				valueBuilder.append(",nims_schema" + PROPERTY_SEPARATOR + nims_schema);
			}
			if (toc_schema != null && !toc_schema.isEmpty()) {
				valueBuilder.append(",toc_schema" + PROPERTY_SEPARATOR + toc_schema);
			}
			if (nims_domain != null && !nims_domain.isEmpty()) {
				valueBuilder.append(",nims_domain" + PROPERTY_SEPARATOR + nims_domain);
			}
			if (nims_resource != null && !nims_resource.isEmpty()) {
				valueBuilder.append(",nims_resource" + PROPERTY_SEPARATOR + nims_resource);
			}
			if (nims_schema != null && !nims_schema.isEmpty()) {
				valueBuilder.append(",nims_schema" + PROPERTY_SEPARATOR + nims_schema);
			}

			return valueBuilder.toString();

			/*
			 * return "metadoc_domain:" + metadoc_domain + ",metadoc_resource:"
			 * + metadoc_resource + ",metadoc_schema:" + metadoc_schema +
			 * ",rel_domain:" + rel_domain + ",rel_resource:" + rel_resource +
			 * ",rel_schema:" + rel_schema + ",toc_resource:" + toc_resource +
			 * ",toc_schema:" + toc_schema + ",nims_domain:" + nims_domain +
			 * ",nims_resource:" + nims_resource + ",nims_schema:" +
			 * nims_schema;
			 */
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}
			if (obj == this) {
				return true;
			}
			if (!(obj instanceof CollcetionDomainInfoBean)) {
				// System.out.println("obj was not instanceof CollcetionDomainInfoBean");
				return false;
			} else {
				if (obj.toString().trim().equals(this.toString())) {
					return true;
				} else {
					// System.out.println(obj.toString()
					// +"  and "+this.toString()+"   are not equal");
					return false;
				}
			}
		}

		public String getCollection_name() {
			return collection_name;
		}

		public void setCollcetion_name(String collection_name) {
			this.collection_name = collection_name;
		}

		public String getMetadoc_domain() {
			return metadoc_domain;
		}

		public void setMetadoc_domain(String metadoc_domain) {
			this.metadoc_domain = metadoc_domain;
		}

		public String getMetadoc_resource() {
			return metadoc_resource;
		}

		public void setMetadoc_resource(String metadoc_resource) {
			this.metadoc_resource = metadoc_resource;
		}

		public String getMetadoc_schema() {
			return metadoc_schema;
		}

		public void setMetadoc_schema(String metadoc_schema) {
			this.metadoc_schema = metadoc_schema;
		}

		public String getRel_domain() {
			return rel_domain;
		}

		public void setRel_domain(String rel_domain) {
			this.rel_domain = rel_domain;
		}

		public String getRel_resource() {
			return rel_resource;
		}

		public void setRel_resource(String rel_resource) {
			this.rel_resource = rel_resource;
		}

		public String getRel_schema() {
			return rel_schema;
		}

		public void setRel_schema(String rel_schema) {
			this.rel_schema = rel_schema;
		}

		public String getToc_resource() {
			return toc_resource;
		}

		public void setToc_resource(String toc_resource) {
			this.toc_resource = toc_resource;
		}

		public String getToc_schema() {
			return toc_schema;
		}

		public void setToc_schema(String toc_schema) {
			this.toc_schema = toc_schema;
		}

		public String getNims_domain() {
			return nims_domain;
		}

		public void setNims_domain(String nims_domain) {
			this.nims_domain = nims_domain;
		}

		public String getNims_resource() {
			return nims_resource;
		}

		public void setNims_resource(String nims_resource) {
			this.nims_resource = nims_resource;
		}

		public String getNims_schema() {
			return nims_schema;
		}

		public void setNims_schema(String nims_schema) {
			this.nims_schema = nims_schema;
		}

		public String getDoc_resource_name() {
			return doc_resource_name;
		}

		public void setDoc_resource_name(String doc_resource_name) {
			this.doc_resource_name = doc_resource_name;
		}

		public String getDoc_schema_name() {
			return doc_schema_name;
		}

		public void setDoc_schema_name(String doc_schema_name) {
			this.doc_schema_name = doc_schema_name;
		}

		public String getMeta_resource_name() {
			return meta_resource_name;
		}

		public void setMeta_resource_name(String meta_resource_name) {
			this.meta_resource_name = meta_resource_name;
		}

		public String getMeta_schema_name() {
			return meta_schema_name;
		}

		public void setMeta_schema_name(String meta_schema_name) {
			this.meta_schema_name = meta_schema_name;
		}

		// collection=metadoc_domain=ABC,metadoc_resource=ABC,metadoc_schema=ABC,rel_domain=ABC,
		// rel_schema=ABC,rel_resource=ABC,toc_resource=ABC,toc_schema=AMC,nims_domain=ABC,nims_schema=ABC,nims_resource=ABC
		// should not contain value which has null or no value
		// key that is collection should be in lower case

	}

	private static void test() {
		// stmt.executeQuery("create table table1 (name varchar(30),id varchar(10))");
		// stmt.executeQuery("create table table2 (domain_name varchar(30),domain_id varchar(10))");

		// stmt.execute("create table table2 (name varchar(30),id varchar(10))");
		// stmt.execute("insert into table2 values('sqllitetest',1)");
		/*
		 * stmt.execute("insert into table2 values('hello',2)");
		 * stmt.execute("insert into table2 values('thirdRow',3)"); //
		 * 
		 * 
		 * ResultSet rs = stmt.executeQuery("select * from table2");
		 * while(rs.next()){
		 * 
		 * System.out.println(rs.getString(1));
		 * System.out.println(rs.getString(2)); }
		 */

	}
}
