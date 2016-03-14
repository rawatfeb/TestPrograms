package fileOperations;


import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathTest {

	
	public static void main(String...args){
		pathTest();
		
	}

	private static void pathTest() {
Path path =Paths.get("PathTest.txt");
System.out.println(path.isAbsolute());
System.out.println(path.getParent());
System.out.println(path.toAbsolutePath());


File f = path.toFile();

System.out.println(f.exists());

/*false
null
C:\Rawat\Luna_Workspace\TestPrograms\PathTest.txt
false*/


	}
	
	
	
}
