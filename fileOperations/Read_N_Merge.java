package fileOperations;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class Read_N_Merge {

	public static String filePath="D:\\print";
	public static String fileName="Test.txt";
	public static String fileName2="Test3.txt";
	
	
	public static void main(String...args) throws Exception{
		
		System.out.println("Start...");
		File file=new File(filePath+"\\"+fileName);
		File file2=new File(filePath+"\\"+fileName2);
		
		
		

		 FileOutputStream fos = new FileOutputStream(file2,true);
		 
		
		
		FileInputStream fis = new FileInputStream(file);
		byte[] b=new byte[10];
		int off=3;
		int len=5;
		System.out.println(fis.available()+" "+fis.skip(5)+" "+fis.getFD()+" "+fis.read(b, off, len)+" "+fis.read(b));
		InputStreamReader is = new InputStreamReader(fis);
		System.out.println(is.getEncoding()+" system encoding:="+System.getProperty("file.encoding")+" "+is.read());
		BufferedReader buffer=new BufferedReader(is);
		System.out.println(buffer.readLine());
		//PrintStream 
		String line=null;
		int count=1;
		int maxAllowed=20;
		String welcomeMessageString="welcome to mars "+System.getProperty("line.separator");
		byte[] welcomeMessage=welcomeMessageString.getBytes();

while((line = buffer.readLine()) != null){
	count++;
	fos.write(welcomeMessage);
//	writer.write(line+System.getProperty("line.separator"));
	System.out.println(line);
	if(count>maxAllowed)break;
}
fos.write(("file.exists ").getBytes());
fos.write((Boolean.valueOf(file.exists())+System.getProperty("line.separator")).toString().getBytes());
fos.write("file.canExecute".getBytes());
fos.write((Boolean.valueOf(file.canExecute())+System.getProperty("line.separator")).toString().getBytes());
fos.write(("file.canRead ").getBytes());
fos.write((Boolean.valueOf(file.canRead())+System.getProperty("line.separator")).toString().getBytes());
fos.write(("file.canWrite ").getBytes());
fos.write((Boolean.valueOf(file.canWrite())+System.getProperty("line.separator")).toString().getBytes());
fos.write(("file.length ").getBytes());
fos.write((Long.valueOf(file.length())+System.getProperty("line.separator")).toString().getBytes());
fos.write(("file.lastModified ").getBytes());
fos.write((Long.valueOf(file.lastModified())+System.getProperty("line.separator")).toString().getBytes());
fos.write(("file.isHidden ").getBytes());
fos.write((Boolean.valueOf(file.isHidden())+System.getProperty("line.separator")).toString().getBytes());
fos.write(("file.listRoots ").getBytes());
fos.write(file.listRoots().toString().getBytes());


buffer.close();
fos.close();
//writer.close();
		
System.out.println("...End");
	}
	
	
	
	
	
}
