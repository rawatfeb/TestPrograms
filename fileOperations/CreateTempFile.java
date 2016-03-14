package fileOperations;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class CreateTempFile {
public static void main(String...args) throws Throwable{
	
	
	//FOSTest();
	//createTempFile();
	FOSPathTest();
	
}

private static void createTempFile() throws IOException {
	File file = new File("temp.temp");
	File tempFile = file.createTempFile("temp", "temp");  //C:\Users\U6025719\AppData\Local\Temp\temp2469838727101561514temp
	
	System.out.println(tempFile.getAbsolutePath());
	tempFile.deleteOnExit();  //should use 
	System.out.println("end.");
	
}

private static void FOSTest() throws IOException {
	//FileOutputStream fos = new FileOutputStream("will it got created"); //yes
	
	FileOutputStream fos = new FileOutputStream("open in append mode",true); //will open a file in append mode and will create new if not present
	
	FileDescriptor  fd = new FileDescriptor();//use  FileDescriptor fd = fileInputStream.getFD();
	System.out.println(fd.valid());
	//FileOutputStream fos = new FileOutputStream(fd); 
	fos.close();
	System.out.println("end...");
	
	
}


private static void FOSPathTest() throws IOException{
	//String path = "c:\\zip\\myfigs.zip";  ////The system cannot find the path specified
	//String path ="C:\\Rawat\\MyWorkspace\\TestPrograms\\testDirectory";  // will create file testDirectory
	String path ="C:\\Rawat\\MyWorkspace\\TestPrograms\\testDirectory\\testFile.txt";  //The system cannot find the path specified
	//String path = "d:\\myfigs.zip";  //aceess is denied for some reason
	
	FileOutputStream fos = new FileOutputStream(path); 
	
	fos.close();
	System.out.println("END...");
	
}


}
