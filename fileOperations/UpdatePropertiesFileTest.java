package fileOperations;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Properties;

public class UpdatePropertiesFileTest {
	private static final String FILE_PATH = "C:\\home\\swat\\services\\DBConnectionUtilty\\DBConnectionUtiltyCache.properties";

	enum availableConfigurationProperties {
		domainCache, TWO, THREE
	}

	public static void main(String[] args) throws Exception {
		Path path = Paths.get(FILE_PATH);
		FileInputStream fis = new FileInputStream(path.toFile());
		Properties properties = new Properties();
		properties.load(fis);
		fis.close();
		System.out.println("properties=" + properties);

		FileInputStream fis2 = new FileInputStream(path.toFile());
		Properties propertiesModified = new Properties();
		propertiesModified.load(fis2);
		fis2.close();
		System.out.println("propertiesModified=" + propertiesModified);

		propertiesModified.setProperty("domainCache", "finaces.");
		propertiesModified.setProperty("dhdhdhhe", "hashfdj.............");

		Enumeration<Object> keysEnumeration = propertiesModified.keys();
		while (keysEnumeration.hasMoreElements()) {
			Object key = keysEnumeration.nextElement();
			try {
				availableConfigurationProperties.valueOf((String) key);
			} catch (IllegalArgumentException ex) {
				propertiesModified.remove(key);
				// do not update properties which are not allowed				 
			}
		}

		properties.putAll(propertiesModified);

		FileOutputStream fos = new FileOutputStream(path.toFile());
		properties.store(fos, "flushed");
		fos.close();

		System.out.println("propertiesModified=" + propertiesModified);
		properties.clear();
		System.exit(0);
	}
}
