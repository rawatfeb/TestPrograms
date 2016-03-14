package io;

public class SysConsoleTest {
	public static void main(String[] args) {

		String s = "caractères français :  à é \u00e9"; // Unicode for "é"
		System.out.println(s);
		//System.console().writer().println(s);
		System.console().writer().write("hello");
		
	}
}
