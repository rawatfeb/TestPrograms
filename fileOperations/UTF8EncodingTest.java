package fileOperations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;

public class UTF8EncodingTest {

	private static String fileName = "626855830.text.D-NEWSTEX-T001.1.rpx.xml";

//	private static String fileName ="NPP_UTF8Test.txt";
	
	public static void main(String... args) throws Exception {

//		readUTF8File();
		
//		readFISUTF8();
		
		//readFileInByteArray();
		
		
//		byteFileCompare();
		
		
		readByteByByte();

	}

	private static void readByteByByte() throws Exception {

		System.out.println("....s t a r t....");
		File UTF8File = new File(fileName); 
		//File UTF8File = new File("626855830.text.D-NEWSTEX-T001.1.rpx.xml"); // 3c
																				// 6e
		InputStream is = new FileInputStream(UTF8File);
		int i = 0;
		int c = 0;
		byte[] buffer=new byte[(int)UTF8File.length()];
		System.out.println(buffer.length);
		 int totalBytesRead = 0;
		 while(totalBytesRead < buffer.length){
	          int bytesRemaining = buffer.length - totalBytesRead;
	          //input.read() returns -1, 0, or more :
	       //   int bytesRead = is.read(buffer, totalBytesRead, bytesRemaining); //preferable
	          int bytesRead = is.read(buffer, totalBytesRead, 8); //will skip last few bits if less than 8 bits
	        
	          /*byte[] localBuffer=new byte[8];
	          int bytesRead = is.read(localBuffer, 0, 8);*/
	          
	          if (bytesRead > 0){
	            totalBytesRead = totalBytesRead + bytesRead;
		//	System.out.println(Integer.toHexString(buffer)); // ef bb bf UTF-8 //fe
		//	System.out.println(Integer.toBinaryString(buffer));											// ff unicode big endian
	System.out.println(bytesRead);
	System.out.println(new String(buffer));
			//	System.out.println(Integer.toString(bytesRead,2));
			
			// //ff fe unicode
			c++;
			if (c == 500)
				break;

		}
		 }
		is.close();
		
		
	}

	
	
	
	private static boolean isValidUTF8(final byte[] bytes) {
	     
	    try {
	        Charset.availableCharsets().get("UTF-8").newDecoder().decode(ByteBuffer.wrap(bytes));
	 
	    } catch (CharacterCodingException e) {
	 
	        return false;
	    }
	 
	    return true;
	}
	
	
	private static void readFISUTF8() {

		try {
			File fileDir = new File(fileName);

			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileDir), "UTF8"));

			String str;

			while ((str = in.readLine()) != null) {
				System.out.println(str);
			}

			in.close();
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	
	
	private static void byteFileCompare() throws Exception{
		System.out.println("starting...");
		String fileName="Converted_To_UTF8.rpx.xml";
		File file1 = new File("626855830.text.D-NEWSTEX-T001.1.rpx.xml"); 
		File file2 = new File(fileName);
		InputStream is1 = new FileInputStream(file1);
		System.out.println("loaded file1...");
		InputStream is2 = new FileInputStream(file2);
		System.out.println("loaded file2...");
		int i=0;
		while ((i = is1.read()) != -1) {
			if(i!=is2.read()){
			System.out.println(Integer.toHexString(i)); // ef bb bf UTF-8 //fe
														// ff unicode big endian
														// //ff fe unicode
			}
		}
		is1.close();
		is2.close();
	}
	
	
	private static void readUTF8File() throws IOException {
		// File UTF8File = new File("UTF8Test.txt"); //
		File UTF8File = new File(fileName); 
		//File UTF8File = new File("626855830.text.D-NEWSTEX-T001.1.rpx.xml"); // 3c
																				// 6e
		InputStream is = new FileInputStream(UTF8File);
		int i = 0;
		int c = 0;
		while ((i = is.read()) != -1) {
			System.out.println(Integer.toHexString(i)); // ef bb bf UTF-8 //fe
														// ff unicode big endian
														// //ff fe unicode
			c++;
			if (c == 50)
				break;

		}
		is.close();
	}
	
	
	
	private static void readFileInByteArray() throws Exception{
		File file = new File(fileName);
		InputStream is = new FileInputStream(file);
		 long length = file.length();
		 System.out.println(length);
		 if (length > Integer.MAX_VALUE) {
		      System.out.println("File is too large");
		    }
		 byte[] bytes = new byte[(int) length];

		 
		 System.out.println((int) length);
		 
		    int offset = 0;
		    int numRead = 0;
		    while (offset <= length) {
		      numRead = is.read(bytes, offset, bytes.length - offset);
		      System.out.println(offset);
		      System.out.println(numRead);
		      offset += numRead;
		    }
		    if (offset < bytes.length) {
		        throw new IOException("Could not completely read file " + file.getName());
		      }
		      is.close();
		      System.out.println(new String(bytes));
	}

}
