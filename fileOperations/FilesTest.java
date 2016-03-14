package fileOperations;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilesTest {

	public static void main(String[] args) throws Exception {
		getMimeContentType();
	}

	private static void getMimeContentType() throws Exception {
		String arg0 = "C:/Users/gaurr/Downloads/Rawat.jpg";
		Path path = Paths.get(arg0);
		System.out.println(Files.probeContentType(path));
	}

}
