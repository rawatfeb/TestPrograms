package performance;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;


public class MapSortingTest {

	public static void main(String... args) {

		simpleTest();

	}

	private static void simpleTest() {
		Map<String, String> hashMap = new HashMap<String, String>();
		Map<String, String> treeMap = new TreeMap<String, String>();

		long start = System.currentTimeMillis();
		for (int i = 0; i < 500000; i++) {
			hashMap.put(UUID.randomUUID() + "Key", "any value" + i);
		}
		hashMap=new TreeMap<>(hashMap);
		System.out.println(" HashMap Performace "+(System.currentTimeMillis() - start));
		
		
		start = System.currentTimeMillis();
		for (int i = 0; i < 500000; i++) {
			treeMap.put(UUID.randomUUID() + "Key", "any value" + i);
		}
		System.out.println(" TreeMap Performace "+(System.currentTimeMillis() - start));
		
	}

}
