package simple;

import java.util.HashSet;

public class BreakLoopTest {

	public static void main(String... args) {

		forBraekTest();
	}

	private static void forBraekTest() {

		for (int i = 0; i < 5; i++) {
			String t = "hello";
		
			boolean isBreak=false;
			if (t.equalsIgnoreCase("hello")) {

				
				if (isBreak)
					break;

				System.out.println("inside the nested if condition");
			}

			isBreak=true;
			System.out.println("after the outer if condition");
			
			HashSet<String> openedConBuckets = new HashSet<String>();
			if ("ResourceNames".equalsIgnoreCase("ResourceNames")) {
				System.out.println("ResourceNames");
			}
			
			
			System.out.println("Last line of the for loop");
			
		}
	}
}
