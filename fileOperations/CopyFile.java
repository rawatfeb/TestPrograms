package fileOperations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class CopyFile {
	public static void main(String... args) throws Exception {

		// copyByLine();
		copyByStream();

	}

	private static void copyByStream() throws IOException {
		String fileName = "EAadhaar_764290062106_31032015153608_587043 - Copy.pdf (SECURED) - Adobe Reader, Version_ Signature1,Signed by Sandeep Bhardwaj _sandeep.bhardwaj@uidai.net.in_,2015.03.31 15_36_11 +05'30'.pdf";
		File file = new File(fileName);
		File copyFile = new File(fileName.trim() + "copy.pdf");
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(copyFile);
		int i = 0;
		while ((i = fis.read()) != -1) {

			fos.write(i);
		}
		fos.close();
		fis.close();
	}

	private static void copyByLine() throws IOException {
		String fileName = "EAadhaar_764290062106_31032015153608_587043 - Copy.pdf (SECURED) - Adobe Reader, Version_ Signature1,Signed by Sandeep Bhardwaj _sandeep.bhardwaj@uidai.net.in_,2015.03.31 15_36_11 +05'30'.pdf";
		File file = new File(fileName);
		File copyFile = new File(fileName.trim() + "copy.pdf");

		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));
		FileWriter fw = new FileWriter(copyFile);

		String line = null;
		while ((line = br.readLine()) != null) {

			fw.write(line);
		}
		fw.close();
		br.close();

	}
}
