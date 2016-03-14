package simple;

public class ReverseString {
	public static void main(String[] args) {
		String inString = "Hello";
		reverse(inString);
		

	}

	private static String reverse(String inString) {
		String reverse = "";
		for (int i = 0; i < inString.length(); i++) {
			reverse+=inString.charAt(inString.length()-i-1);
		}
		System.out.println(reverse);
		return reverse;
	}
	
	
	
}
