package fileOperations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RemoveCommentTest {

	public static void main(String[] args) throws Exception {
		Path path = Paths.get("C:\\Rawat\\Luna_Workspace\\TestPrograms\\BasicTest.java");
		Path path2 = Paths.get("C:\\Rawat\\Luna_Workspace\\TestPrograms\\BasicTest2.java");
		
		FileReader fr = new FileReader(path.toFile());
		FileWriter fw = new FileWriter(path2.toFile());

		BufferedReader br = new BufferedReader(fr);
		PrintWriter pw = new PrintWriter(new BufferedWriter(fw)); //new BufferedWriter(fw);

		String line = null;
		while ((line = br.readLine()) != null) {
			line=line.trim();
			if (line.startsWith("/")) {
				continue;
			} else if (line.trim().contains(";/")) {
				pw.println(line.substring(0, line.indexOf(";")));
			}
			if (line.endsWith("/")) {
				continue;
			} else {
				pw.println(line);
			}

		}

		br.close();
		pw.close();
		System.out.println("Done");

	}
}
