package regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecimalExtract {
public static void main(String...args){
	extractDecimal();
//	spiltByBlank();
//	splitBySpace();
//	splitByNewLine();
	
	
}






private static void splitByNewLine() {
String s="hello 123 welcome 45ms  \r\n   helo this new line";
	
	String[] tokens = s.split(System.getProperty("line.separator"));
	for (String token : tokens) {
		System.out.println(token);
	}	
}






private static void extractDecimal() {
	String s="hello 123 welcome 45ms";
	s="Processed total Meta guids in cleanup: 87 guids in 10021ms";
	
	
	 Pattern p = Pattern.compile("\\d+"+" guids");
     Matcher m = p.matcher(s);		//  hello1234goodboy789very2345
     while(m.find()) {
         System.out.println(m.group());
     }
	
    /* String[] tokens = s.split("(\\D+)(\\d+)(.*)");  //"\\d+"+"ms"
	for (String token : tokens) {
		System.out.println(token);
	}*/
	
}





private static void splitBySpace() {
String s="hello 123 welcome 45ms";
	
	String[] tokens = s.split(" ");
	for (String token : tokens) {
		System.out.println(token);
	}
	/*
	hello
	123
	welcome
	45ms*/
}

private static void spiltByBlank() {
String s="hello 123 welcome 45ms";
	
	String[] tokens = s.split("");
	for (String token : tokens) {
		System.out.println(token);
	}

/*h
e
l
l
o
 
1
2
3
 
w
e
l
c
o
m
e
 
4
5
m
s
*/
}









}
