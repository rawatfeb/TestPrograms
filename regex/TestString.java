package regex;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class TestString {

	public static void main(String... args) throws IOException {
		String value = "metadoc_domain:null,metadoc_resource:null,metadoc_schema:null,rel_domain:null,rel_resource:null,rel_schema:null,toc_resource:null,toc_schema:null,nims_domain:pc_w_wl_df_cms,nims_resource:NIMSPREMISE,nims_schema:pcwwldfcms";
		CollcetionDomainInfoBean bean1 = new CollcetionDomainInfoBean(value);
		bean1.setCollcetion_name("test");
		CollcetionDomainInfoBean bean2 = new CollcetionDomainInfoBean(value);

		System.out.println(bean1);

		System.out.println(bean1.equals(bean2));
		
		
		HashMap<String,String> testmap=new HashMap<String,String>();
		
		String t="does not exist";
		String s=testmap.get(t);
		if(s==null){System.out.println("s is null");}
		//System.out.println(" s value:   "+s.isEmpty());
		
		
		String key="  ";
		System.out.println("empty:="+key.isEmpty());
		

		
		FileOutputStream fos = new FileOutputStream("witePrpoerty.properties");
		Properties prop = new Properties();
		prop.setProperty(bean1.collcetion_name, bean1.toString());
		prop.store(fos, "test");
		fos.close();
		
		
		FileInputStream fis = new FileInputStream("witePrpoerty.properties");
		Properties propRead = new Properties();
		propRead.load(fis);
		
		
		
		System.out.println("Reading the property from file(testing is / slash will be removed): "+propRead.getProperty("test"));
		
		
		
		
		
		
	}

	private static class CollcetionDomainInfoBean

	{

		private String collcetion_name;
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

			if (value != null && !value.isEmpty()) {
				String[] values = value.split(",");

				for (String eachValue : values) {
					String key=eachValue.split(":")[0];
				switch (key) {
				case "doc_resource_name":
					this.doc_resource_name = eachValue.split(":")[1];
						break ;
				case "doc_schema_name":
					this.doc_schema_name = eachValue.split(":")[1];
						break ;
				case "meta_resource_name":
					this.meta_resource_name = eachValue.split(":")[1];
						break ;
				case "meta_schema_name":
					this.meta_schema_name = eachValue.split(":")[1];
						break ;
				case "metadoc_domain":
					this.metadoc_domain = eachValue.split(":")[1];
						break ;
				case "metadoc_resource":
					this.metadoc_resource = eachValue.split(":")[1];
						break ;
				case "metadoc_schema":
					this.metadoc_schema = eachValue.split(":")[1];
						break ;
				case "rel_domain":
					this.rel_domain = eachValue.split(":")[1];
						break ;
				case "rel_resource":
					this.rel_resource = eachValue.split(":")[1];
						break ;
				case "rel_schema":
					this.rel_schema = eachValue.split(":")[1];
						break ;
				case "toc_resource":
					this.toc_resource = eachValue.split(":")[1];
						break ;
				case "toc_schema":
					this.toc_schema = eachValue.split(":")[1];
						break ;
				case "nims_domain":
					this.nims_domain = eachValue.split(":")[1];
						break ;
				case "nims_resource":
					this.nims_resource = eachValue.split(":")[1];
						break ;
				case "nims_schema":
					this.nims_schema = eachValue.split(":")[1];
						break ;
				default:
					System.out.println("detected unrecognized key: "+key);
						break ;
					}
				}
			}
		}

		@Override
		public String toString() {
			StringBuilder valueBuilder = new StringBuilder();
			if (metadoc_domain != null && !metadoc_domain.isEmpty()) {
				valueBuilder.append(",metadoc_domain:" + metadoc_domain);
			}
			if (metadoc_resource != null && !metadoc_resource.isEmpty()) {
				valueBuilder.append(",metadoc_resource:" + metadoc_domain);
			}
			if (metadoc_schema != null && !metadoc_schema.isEmpty()) {
				valueBuilder.append(",metadoc_schema:" + metadoc_schema);
			}
			if (rel_domain != null && !rel_domain.isEmpty()) {
				valueBuilder.append(",rel_domain:" + rel_domain);
			}
			if (rel_resource != null && !rel_resource.isEmpty()) {
				valueBuilder.append(",rel_resource:" + rel_resource);
			}
			if (rel_schema != null && !rel_schema.isEmpty()) {
				valueBuilder.append(",rel_schema:" + rel_schema);
			}
			if (toc_resource != null && !toc_resource.isEmpty()) {
				valueBuilder.append(",toc_resource:" + toc_resource);
			}
			if (toc_schema != null && !toc_schema.isEmpty()) {
				valueBuilder.append(",toc_schema:" + toc_schema);
			}
			if (nims_domain != null && !nims_domain.isEmpty()) {
				valueBuilder.append(",nims_domain:" + nims_domain);
			}
			if (nims_resource != null && !nims_resource.isEmpty()) {
				valueBuilder.append(",nims_resource:" + nims_resource);
			}
			if (nims_schema != null && !nims_schema.isEmpty()) {
				valueBuilder.append(",nims_schema:" + nims_schema);
			}
			if (toc_schema != null && !toc_schema.isEmpty()) {
				valueBuilder.append(",toc_schema:" + toc_schema);
			}
			if (nims_domain != null && !nims_domain.isEmpty()) {
				valueBuilder.append(",nims_domain:" + nims_domain);
			}
			if (nims_resource != null && !nims_resource.isEmpty()) {
				valueBuilder.append(",nims_resource:" + nims_resource);
			}
			if (nims_schema != null && !nims_schema.isEmpty()) {
				valueBuilder.append(",nims_schema:" + nims_schema);
			}
			if (doc_resource_name != null && !doc_resource_name.isEmpty()) {
				valueBuilder.append(",doc_resource_name:" + doc_resource_name);
			}
			if (doc_schema_name != null && !doc_schema_name.isEmpty()) {
				valueBuilder.append(",doc_schema_name:" + doc_schema_name);
			}
			if (meta_resource_name != null && !meta_resource_name.isEmpty()) {
				valueBuilder.append(",meta_resource_name:" + meta_resource_name);
			}
			if (meta_schema_name != null && !meta_schema_name.isEmpty()) {
				valueBuilder.append(",meta_schema_name:" + meta_schema_name);
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
				System.out.println("obj was not instanceof CollcetionDomainInfoBean");
				return false;
			} else {
				if (obj.toString().trim().equals(this.toString())) {
					return true;
				} else {
					System.out.println(obj.toString() + "  and " + this.toString() + "   are not equal");
					return false;
				}
			}
		}

		public String getCollcetion_name() {
			return collcetion_name;
		}

		public void setCollcetion_name(String collcetion_name) {
			this.collcetion_name = collcetion_name;
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

	}

	// =======================================================================================

	private static class CollcetionDomainInfoBean2 {
		private String collcetion_name;
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

		public String getCollcetion_name() {
			return collcetion_name;
		}

		public void setCollcetion_name(String collcetion_name) {
			this.collcetion_name = collcetion_name;
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

		CollcetionDomainInfoBean2() {
		}

		CollcetionDomainInfoBean2(String value) {

			String[] values = value.split(",");
			System.out.println(values.length);
			if (values.length == 11) {
				this.metadoc_domain = values[0].split("=")[1];
				this.metadoc_resource = values[1].split("=")[1];
				this.metadoc_schema = values[2].split("=")[1];
				this.rel_domain = values[3].split("=")[1];
				this.rel_resource = values[4].split("=")[1];
				this.rel_schema = values[5].split("=")[1];
				this.toc_resource = values[6].split("=")[1];
				this.toc_schema = values[7].split("=")[1];
				this.nims_domain = values[8].split("=")[1];
				this.nims_resource = values[9].split("=")[1];
				this.nims_schema = values[10].split("=")[1];
			}
		}

		/*
		 * "metadoc_domain=" + metadoc_domain + ",metadoc_resource=" +
		 * metadoc_resource + ",metadoc_schema=" + metadoc_schema +
		 * ",rel_domain=" + rel_domain + ",rel_resource=" + rel_resource +
		 * ",rel_schema=" + rel_schema + ",toc_resource=" + toc_resource +
		 * ",toc_schema=" + toc_schema + ",nims_domain=" + nims_domain +
		 * ",nims_resource=" + nims_resource + ",nims_schema=" + nims_schema +
		 * "\r\n";
		 */

		@Override
		public String toString() {

			return "metadoc_domain=" + metadoc_domain + ",metadoc_resource=" + metadoc_resource + ",metadoc_schema="
					+ metadoc_schema + ",rel_domain=" + rel_domain + ",rel_resource=" + rel_resource + ",rel_schema="
					+ rel_schema + ",toc_resource=" + toc_resource + ",toc_schema=" + toc_schema + ",nims_domain="
					+ nims_domain + ",nims_resource=" + nims_resource + ",nims_schema=" + nims_schema;
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
				System.out.println("obj was not instanceof CollcetionDomainInfoBean");
				return false;
			} else {
				if (obj.toString().trim().equals(this.toString())) {
					return true;
				} else {
					System.out.println(obj.toString() + "  and " + this.toString() + "   are not equal");
					return false;
				}
			}
		}

		// collection=metadoc_domain=ABC,metadoc_resource=ABC,metadoc_schema=ABC,rel_domain=ABC,
		// rel_schema=ABC,rel_resource=ABC,toc_resource=ABC,toc_schema=AMC,nims_domain=ABC,nims_schema=ABC,nims_resource=ABC

	}

}
