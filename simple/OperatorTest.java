package simple;

public class OperatorTest {
	public static void main(String[] args) {

		unaryOperatorTest();

	}

	private static void unaryOperatorTest() {
		int i = 1, j = 10;
		do {
			if (i++ > --j) /* Line 4 */
			{
				continue;
			}
		} while (i < 5);
		System.out.println("i = " + i + "and j = " + j); /* Line 9 */
		
		//Basically the prefix and postfix unary operators have a higher order of evaluation than the relational operators.

	}
}
