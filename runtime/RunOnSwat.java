/*package runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class RunOnSwat {

	private static final int MAX_UPLOAD_FILE_SIZE = 1024 * 1024 * 7; // 5

	private String uitilityHomeDir =    "C:/tmp"; //"/home/c167160/share/RunOnSwat" ; //"C:/tmp"
	private String JAVA_HOME = "jdk1.8.0";
	private String NovusRelease = "Novus.15.7.1.4.jdk1.8";
	private String SystemJars = "System.20150422";
	private String db = "dbrel12";
	private String MQV = "System.20150422";
	private String program;
	private String customJar;
	private String OUTPUT_FILE = "stdout2.out";

	private final Object JAVA = uitilityHomeDir + File.separator + JAVA_HOME + File.separator + "bin" + File.separator
			+ "java";
	private final Object JAVAC = uitilityHomeDir + File.separator + JAVA_HOME + File.separator + "bin" + File.separator
			+ "javac";
	private final Object PROPS = "-Dcom.sun.management.jmxremote";
	private final String NOVUS = uitilityHomeDir + File.separator + NovusRelease;
	private final String SYSTEM = uitilityHomeDir + File.separator + SystemJars;
	private final String DB = uitilityHomeDir + File.separator + db;
	private final String MQ = uitilityHomeDir + File.separator + MQV;
	private final String CUSTOM_JARS = uitilityHomeDir + File.separator + "CustomJars";

	private final String JAVA_EXT = uitilityHomeDir + File.separator + JAVA_HOME + File.separator + "jre"
			+ File.separator + "lib" + File.separator + "ext";
	private final Object EXT = "-DNSA_Location=EaganBPool -Dsite=B -Dcir.site=B -Djava.ext.dirs=" + CUSTOM_JARS + ":"
			+ NOVUS + ":" + SYSTEM + ":" + DB + ":" + MQ + ":" + JAVA_EXT + ":" + JAVA_HOME + "/lib -cp .";
	private boolean DEBUG = true;

	public boolean checkAllRequiredDirectoriesExist() {
		boolean result = true;
		if (!checkHomeDirectryPathExist()) {
			throw new RuntimeException("Home Directory for Tool does not exist " + uitilityHomeDir);
		}
		if (!checkHomeBinPathExist()) {
			throw new RuntimeException("bin Directory for Tool does not exist inside Tool Home(" + uitilityHomeDir
					+ ")");
		}
		if (!checkCustomJarPathExist()) {
			throw new RuntimeException("CustomJars Directory for Tool does not exist inside Tool Home("
					+ uitilityHomeDir + ")");
		}
		if (!isNovusExist()) {
			throw new RuntimeException("Seems Like Novus does not exist inside Tool Home("
					+ uitilityHomeDir + ")");
		}
		if (!isSystemExist()) {
			throw new RuntimeException("Seems Like System does not exist inside Tool Home("
					+ uitilityHomeDir + ")");
		}
		if (!isDbrelExist()) {
			throw new RuntimeException("Seems Like DB Release does not exist inside Tool Home("
					+ uitilityHomeDir + ")");
		}
		if (!isJDKExist()) {
			throw new RuntimeException("Seems Like JDK does not exist inside Tool Home("
					+ uitilityHomeDir + ")");
		}
		return result;
	}

	private boolean run() throws Exception {
		if (DEBUG)
			System.out.println("Started Run");
		boolean result = false;
		Process process;
		try {
			StringBuilder runsh = new StringBuilder();
			if(program.endsWith(".class")){
				runsh.append(JAVA);
			}else{
				runsh.append(JAVAC);
			}
			runsh.append(" ");
			runsh.append(PROPS);
			runsh.append(" ");
			runsh.append(EXT);
			runsh.append(" ");
			if(program.endsWith(".class")){
				runsh.append(program.split(".")[0]);
			}else{
				runsh.append(program);
			}
			runsh.append(" ");
			runsh.append(" 1>" + OUTPUT_FILE + " 2>&1 &");

			if (DEBUG)
				System.out.println("runsh dump:==" + runsh);
			//process = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", runsh.toString() });
			process = Runtime.getRuntime().exec("cmd /c dir");
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

			if (DEBUG)
				System.out.println("Executed Runtime ");

			String line = null;
			while ((line = in.readLine()) != null) {
				result = true;
				System.out.println(line);
			}
			in.close();
			System.out.println(process.waitFor());
			process.destroy();
		} catch (Exception t) {
			throw t;
		}
		return result;
	}

	private boolean checkHomeDirectryPathExist() {
		return checkPathExist(uitilityHomeDir);
	}

	private boolean checkHomeBinPathExist() {
		String path = uitilityHomeDir + File.separator + "bin";
		return checkPathExist(path);
	}

	private boolean checkCustomJarPathExist() {
		String path = uitilityHomeDir + File.separator + "CustomJars";
		return checkPathExist(path);
	}

	private boolean checkPathExist(String path) {
		System.out.print("path=" + path + "\t");
		boolean result = false;
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				System.out.println("Directory path exist.");
				return true;
			}
		}
		return result;
	}

	private boolean isNovusExist() {
		return isDirExistApprox("NOVUS");
	}

	private boolean isSystemExist() {
		return isDirExistApprox("SYSTEM");
	}

	private boolean isDbrelExist() {
		return isDirExistApprox("DBREL");
	}

	private boolean isJDKExist() {
		return isDirExistApprox("JDK");
	}

	public List<String> getNovusDirs() {
		return getDirApprox("NOVUS");
	}

	public List<String> getSystemDirs() {
		return getDirApprox("SYSTEM");
	}

	public List<String> getDbrelExistDirs() {
		return getDirApprox("DBREL");
	}

	public List<String> getJDKDirs() {
		return getDirApprox("JDK");
	}

	private boolean isDirExistApprox(String Dir) {
		boolean result = false;
		File file = new File(uitilityHomeDir);
		if (file.exists()) {
			String[] ls = file.list();
			for (String content : ls) {
				if (content.toUpperCase().contains(Dir)) {
					result = true;
					System.out.println(Dir + "   ~    " + content);
				}
			}
		}
		return result;
	}

	private List<String> getDirApprox(String Dir) {
		List<String> result = new ArrayList<String>();
		File file = new File(uitilityHomeDir);
		if (file.exists()) {
			String[] ls = file.list();
			for (String content : ls) {
				if (content.toUpperCase().contains(Dir)) {
					result.add(content);
				}
			}
		}
		return result;
	}

	public void saveAndRun(HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean isSuccessful = false;
		try {
			save(request);
			isSuccessful = run();
		} catch (Exception e) {
			throw e;
		} finally {
			cleanUp();
		}
		if (isSuccessful) {
			tailOut(response.getWriter());
		}
	}

	private void tailOut(PrintWriter printWriter) {
		Process process;
		try {
			process = Runtime.getRuntime().exec(
					new String[] { "/bin/sh", "-c",
							" tail -f " + uitilityHomeDir + File.separator + "bin" + File.separator + OUTPUT_FILE });
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			while ((line = in.readLine()) != null) {
				printWriter.write(line);
				System.out.println(line);
			}
			in.close();
			process.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		}
		printWriter.println("Done");
		printWriter.flush();
	}

	private void save(HttpServletRequest request) throws Exception {
		List<FileItem> items = null;
		try {
			ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());
			upload.setSizeMax(MAX_UPLOAD_FILE_SIZE);
			items = upload.parseRequest(request);
		} catch (FileUploadException e) {
			throw e;
		}
		for (FileItem item : items) {
			String fieldName = item.getFieldName();
			if (!item.isFormField()) {
				String fileName = new File(item.getName()).getName();
				File storeFile = null;
				if (item.getSize() > 0) {
					if ("programFile".equalsIgnoreCase(fieldName)) {
						String filePath = uitilityHomeDir + File.separator + "bin" + File.separator + fileName;
						storeFile = new File(filePath);
						setProgram(fileName);
					}
					if ("CustomJar".equalsIgnoreCase(fieldName)) {
						String filePath = uitilityHomeDir + File.separator + "CustomJars" + File.separator + fileName;
						storeFile = new File(filePath);
						setCustomJar(fileName);
					}
					try {
						item.write(storeFile);
					} catch (Exception e) {
						throw e;
					}
				}
			} else {
				if ("NovusReleases".equalsIgnoreCase(fieldName)) {
					setNovusRelease(item.getString());
				}
			}
		}
		if (null == program || program.isEmpty()) {
			throw new RuntimeException("Did You selected Program file to run ?");
		}

	}

	private void cleanUp() {
		if (null != program && !program.isEmpty()) {
			new File(uitilityHomeDir + File.separator + "bin" + File.separator + program).delete();
			System.out.println(new File(uitilityHomeDir + File.separator + "bin" + File.separator + program).exists());
		}
		if (null != customJar && !customJar.isEmpty()) {
			new File(uitilityHomeDir + File.separator + "CustomJars" + File.separator + customJar).delete();
			System.out.println(new File(uitilityHomeDir + File.separator + "CustomJars" + File.separator + customJar)
					.exists());
		}
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getUitilityHomeDir() {
		return uitilityHomeDir;
	}

	public void setUitilityHomeDir(String uitilityHomeDir) {
		this.uitilityHomeDir = uitilityHomeDir;
	}

	public String getJAVA_HOME() {
		return JAVA_HOME;
	}

	public void setJAVA_HOME(String jAVA_HOME) {
		this.JAVA_HOME = jAVA_HOME;
	}

	public String getNovusRelease() {
		return NovusRelease;
	}

	public void setNovusRelease(String novusRelease) {
		this.NovusRelease = novusRelease;
	}

	public String getSystemJars() {
		return SystemJars;
	}

	public void setSystemJars(String systemJars) {
		this.SystemJars = systemJars;
	}

	public String getMQV() {
		return MQV;
	}

	public void setMQV(String mQV) {
		this.MQV = mQV;
	}

	public String getCustomJar() {
		return customJar;
	}

	public void setCustomJar(String customJar) {
		this.customJar = customJar;
	}

}
*/