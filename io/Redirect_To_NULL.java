package io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Redirect_To_NULL {

	//This can be useful if you want to suppress all output.
	public static void main(String[] args) {
		redirect();
		System.out.println("This will go to NULL device ");
	}
	
	
	public static void redirect(){
	/*// Unix style when it is running on unix machine this makes this code platform dependent be sure.
	PrintStream nps;
	try {
		nps = new PrintStream(new FileOutputStream("/dev/null"));
		System.setErr(nps);
		System.setOut(nps);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}*/
	

	//Windows style
	PrintStream nps;
	try {
		nps = new PrintStream(new FileOutputStream("NUL:"));
		System.setErr(nps);
		System.setOut(nps);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	

//platform independent
	//One-liner style : subclass OutputStream to override the write method ...
	System.setOut(new java.io.PrintStream(
	    new java.io.OutputStream() {
	       public void write(int b){}
	    }
	 ));
	}
}
