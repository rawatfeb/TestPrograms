package simple;

public class ShiftOperatorTest {
	public static void main(String[] args) {

		doubleAngularBraketTest();

	}

	private static void doubleAngularBraketTest() {
		int normalMultiply = 3 * 4;
		System.out.println(normalMultiply);

		int shiftTwoBit = 3 << 2;
		System.out.println(shiftTwoBit);
	}
}
