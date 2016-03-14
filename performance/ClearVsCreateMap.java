package performance;

import java.util.HashMap;

public class ClearVsCreateMap {

	//comment: time complexity looks same if we create once and clear it n times vs create new  n times
	public static void main(String[] args) {

		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			HashMap<String, String> mp = new HashMap<String, String>();
		}
		System.out.println("create new Map time: " + (System.currentTimeMillis() - startTime));

		HashMap<String, String> mp = new HashMap<String, String>();
		startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			mp.clear();
		}
		System.out.println("clear new Map time: " + (System.currentTimeMillis() - startTime));

	}

	
}
