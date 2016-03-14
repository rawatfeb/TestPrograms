package simple;

import java.util.Arrays;

public class ArraysSortTest {
	public static void main(String[] args) {
		String[] stArray = new String[] { "hello", "hi", "venkat","akhil" };
		Arrays.sort(stArray);
		for (String string : stArray) {
			System.out.println(string);
		}

	}
}
