package simple;

public class BinaryToDecimal {
	public static void main(String[] args) {
		int binary = 1010;
		BinaryToDecimalTest(binary);
	}

	private static void BinaryToDecimalTest(int binary) {
		double decimal = 0;
		int position = 0;
		while (binary != 0) {
			int lastdigit = binary % 2;
			System.out.println(lastdigit);
			decimal += lastdigit * Math.pow(2, position);
			System.out.println(decimal);
			position++;
			binary = binary / 10;
			System.out.println(binary);
		}
		System.out.println(decimal);
	}
}
