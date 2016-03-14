package simple;

public class IsPerfectNumberTest {
	public static void main(String[] args) {
		
		//A perfect number is a positive integer that is equal to the sum of its proper positive divisors, that is, the sum of its positive divisors excluding the number itself.
		
		int number = 28;
		isPerfectNumber(number);
	}

	private static void isPerfectNumber(int number) {

		int divsiorsSum = 0;
		for (int i = 1; i <= number / 2; i++) {
			if (number % i == 0) {
				divsiorsSum = divsiorsSum + i;
			}
		}
		System.out.println(divsiorsSum);

		if (divsiorsSum == number) {
			System.out.println("NUmber is perfect number");
		} else {
			System.out.println("Number is not perfect");
		}
	}
}
