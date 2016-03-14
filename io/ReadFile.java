package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ReadFile {

	public static void main(String[] args) throws Exception {
		String fileURI = "C:\\tmp\\bag.ser";
		
		System.out.println(new File(fileURI).exists());
		System.out.println("startting now...");
		
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			//QuickFileRead(fileURI);
		}
		System.out.println("QuickFileRead=" + (System.currentTimeMillis() - start) + " ms");
		
		long readFileBufferedReaderstart = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			readFileBufferedReader(fileURI);
		}
		long readFileBufferedReaderend = System.currentTimeMillis();
		System.out.println("readFileBufferedReader=" + (System.currentTimeMillis() - readFileBufferedReaderstart) + " ms");
	
		long readFilestart = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			readFile(fileURI);
		}
		System.out.println("readFile=" + (System.currentTimeMillis() - readFilestart) + " ms");
		System.out.println("readFileBufferedReader=" + (readFileBufferedReaderend - readFileBufferedReaderstart) + " ms");
	}

	public static void QuickFileRead(String filename) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(filename)).useDelimiter("\\Z");
		String contents = scanner.next();
		System.out.println(contents);
		scanner.close();
	}

	private static String readFileBufferedReader(String filename) throws IOException {
		String lineSep = System.getProperty("line.separator");
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String nextLine = "";
		StringBuffer sb = new StringBuffer();
		while ((nextLine = br.readLine()) != null) {
			sb.append(nextLine);
			//
			// note:
			//   BufferedReader strips the EOL character
			//   so we add a new one!
			//
			sb.append(lineSep);
		}
		System.out.println(sb.toString());
		br.close();
		return sb.toString();
	}

	public static void readFile(String filename) {
		String thisLine;
		try {
			FileInputStream fin = new FileInputStream(filename);
			// JDK1.1+
			BufferedReader myInput = new BufferedReader(new InputStreamReader(fin));
			while ((thisLine = myInput.readLine()) != null) {
				System.out.println(thisLine);
			}
			fin.close();
			myInput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
