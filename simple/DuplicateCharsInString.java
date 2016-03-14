package simple;

import java.util.HashMap;

public class DuplicateCharsInString {
	public static void main(String[] args) {
		DuplicateCharsInStringTest();
	}

	private static void DuplicateCharsInStringTest() {
		String testString = "Hello how are you";
		char[] testStringArray = testString.toCharArray();

		HashMap<Character, Integer> dupCarMap = new HashMap<Character, Integer>();

		for (char c : testStringArray) {
			if (dupCarMap.containsKey(c)) {
				dupCarMap.put(c, dupCarMap.get(c) + 1);
			} else {
				dupCarMap.put(c, 1);
			}
		}
		System.out.println(dupCarMap);
	}
}
