package fileOperations;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ReadFileTest {

	public static void main(String... args) throws Exception {
		read_N_Line_in_File(10);
		countLine_in_File();

	}

	private static void countLine_in_File() throws Exception {
		String filePtah = "C:\\Users\\U6025719\\Documents\\LoadFiles\\031315083310_InfoCan_Consumer0115.txt.gz_InfoCan_Consumer0115.txt.xml.gz__305_1665_PEOPLE-CANADA_1426251790338_1.gz__1169_LoadFileGenerator.out.8\\hhg_0.5.0.2\\hhg_0.5.0.2";
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePtah)));) {
			String line = null;
			int counter = 0;
			while (null != (line = br.readLine())) {
				counter++;
			}
			System.out.println(counter);
		}
	}

	private static void read_N_Line_in_File(int limit) throws Exception {
		String filePtah = "C:\\Users\\U6025719\\Documents\\LoadFiles\\031315083310_InfoCan_Consumer0115.txt.gz_InfoCan_Consumer0115.txt.xml.gz__305_1665_PEOPLE-CANADA_1426251790338_1.gz__1169_LoadFileGenerator.out.8\\hhg_0.5.0.2\\hhg_0.5.0.2";
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(filePtah)));) {
			String line = null;
			int counter = 0;
			while (null != (line = br.readLine())) {
				System.out.println(line);
				counter++;
				if (counter == limit) {
					break;
				}

			}
		}
	}
}
