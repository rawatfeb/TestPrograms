package regex;


public class SplitVsSubstringPerformanceTest {
	public static void main(String[] args) {
		
		// testString.substring(0, testString.indexOf(".")); wins over the  testString.split("\\.")[0];
		
		int itr = 100000;
		String testString = "csclone.prod.poolk";

		long start = System.currentTimeMillis();
		for (int i = 0; i < itr; i++) {
			String val = testString.substring(0, testString.indexOf("."));
//			System.out.print(val);
		}
		System.out.println(System.currentTimeMillis() - start);
		start = System.currentTimeMillis();

		for (int i = 0; i < itr; i++) {
			String val = testString.split("\\.")[0];
//			System.out.print(val);
		}
		System.out.println(System.currentTimeMillis() - start);
	}
}
