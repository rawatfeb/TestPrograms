package simple;

public class ReverseNumber {
	public static void main(String[] args) {
		int number = 556768;
		reverseNumber(number);
	}

	private static void reverseNumber(int number) {
		int temp = 0;
		while (number != 0) {
			temp = temp * 10 + (number % 10);
			number = number / 10;
		}
		System.out.println(temp);
	}
}
