package io;

public class SysConsoleTest {
	public static void main(String[] args) {

		String s = "caract�res fran�ais :  � � \u00e9"; // Unicode for "�"
		System.out.println(s);
		//System.console().writer().println(s);
		System.console().writer().write("hello");
		
	}
}
