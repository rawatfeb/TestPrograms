package com.test;


public class TestProtected {

	public int data=9;
	
	protected void protectedMethod(){
		data=10;
		System.out.println("I am protected and from com.test package");
		
	}
	
	public static void parentStaticMethod(){
		System.out.println("parentStaticMethod");
	}
	
}
