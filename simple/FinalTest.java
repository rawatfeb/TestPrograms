package simple;

import java.io.PrintStream;

public class FinalTest {
	public final static PrintStream out =null;
	
	public static void main(String[] args) {
		
	}



	public static PrintStream getOut() {
		return out;
	}
	
	public static void setOut(PrintStream o) {
		out=o;
	}
	
	
}
