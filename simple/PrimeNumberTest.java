package simple;

public class PrimeNumberTest {
	public static void main(String[] args) {
		int number = 31;
		System.out.println(isPrime(number));

		sumOfFirstThousandPrime();

	}

	private static void sumOfFirstThousandPrime() {

		long sum = 0;
		int count = 0;
		int gn = 2;
		while (count < 1000) {
			if (isPrime(gn)) {
				sum += gn;
				count++;
			}
			gn++;
		}

		System.out.println(sum);
	}

	private static boolean isPrime(int number) {

		for (int i = 2; i < number / 2; i++) {

			if (number % i == 0) {
				return false;
			}
		}
		return true;

	}
}
