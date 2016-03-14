package simple;

public class FindOddEven {
	public static void main(String[] args) {

		Integer n = 456898;
		isOdd(n);

	}

	private static void isOdd(Integer number) {

		if ((number & 1) != 0) {
			System.out.println(Integer.toBinaryString(number));
			System.out.println(Integer.bitCount(number));
			System.out.println("Number id odd");
		} else {
			System.out.println("Number is even");
		}

	}

}
