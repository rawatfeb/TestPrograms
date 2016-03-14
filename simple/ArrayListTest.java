package simple;

import java.util.ArrayList;

public class ArrayListTest {
public static void main(String...args){
	
	addAtSpecificIndex();
	
	
}

private static void addAtSpecificIndex() {
ArrayList<String> al = new ArrayList<String>(10);	
al.add(0, "at zero index");
System.out.println(al);
al.add("just add");
System.out.println(al);

al.add(4, "at fourth");  //we can not add at specified index until all before this is filled up 
System.out.println(al);
}
}
