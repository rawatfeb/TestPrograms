package io;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;


public class TestSysOut {

	public static void main(String[] args) throws Exception{
		fasterSysOut();
	}
	
	public static void normalSysOut() throws Exception {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			System.out.print("abcdefghijk ");
			System.out.print(String.valueOf(i));
			System.out.print('\n');
		}
		System.err.println("Loop time: " + (System.currentTimeMillis() - start));
	}
	
	
	public static void fasterSysOut() throws Exception{

	     BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new
	         FileOutputStream(java.io.FileDescriptor.out), "ASCII"), 1024);
	     long start = System.currentTimeMillis();
	     for (int i = 0; i < 1000000; i++) {
	       out.write("abcdefghijk ");
	       out.write(String.valueOf(i));
	       out.write('\n');
	     }
	     out.flush();
	     System.err.println("Loop time: " +
	       (System.currentTimeMillis() - start));
	  
	}
	
	
}