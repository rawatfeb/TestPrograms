package simple;

public class MyNumberSumRec {
	static int sum = 0;

	public static void main(String[] args) {
		int number = 235;
		numberSum(number);
		System.out.println(getNumberSum(number));
	}

	private static void numberSum(int number) {
		int sum = 0;
		while (number != 0) {
			sum = sum + number % 10;
			number = number / 10;
		}
		System.out.println("Sum=" + sum);
	}

	private static int getNumberSum(int number) {

		if (number == 0) {
			return sum;
		} else {
			sum += (number % 10);
			getNumberSum(number / 10);
		}
		return sum;
	}
}
