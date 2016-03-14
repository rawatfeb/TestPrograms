package calculator;

public class CalculatePremiumCompoundValue {

	public static void main(String[] args) {
		double interestRate = 8;
		int premiumValue = 21259;
		double time = (15);
		if (time == 0) {
			System.out.println("Time can not be zero value");
			return;
		}
		double cummutativeSum = calculateForAllPremiumCummutatively(premiumValue, interestRate, time,
				Frequency.HALF_YEARLY);
		System.out.println("cummutativeSum=" + cummutativeSum);
		// test();
	}

	public static void test() {

		double futureValue = 100 * Math.pow((1 + ((8.0 / 100) / 1)), (11.7 * 1));
		System.out.println(futureValue);

		double x = 11.7;
		System.out.println(Math.floor(x));
		System.out.println(x - Math.floor(x));

		double futureValue2 = 100 * (Math.pow((1 + ((8.0 / 100) / 1)), (11.0 * 1))) * ((1 + (((8.0 * 0.7) / 100) / 1)));
		System.out.println(futureValue2);

		double interestRate = 8;
		int premiumValue = 31250;
		double time = (11.0 / 4.0);
		double cummutativeSum = calculateForAllPremiumCummutatively(premiumValue, interestRate, time, Frequency.YEARLY);

		// actual answer= 36450 expected answer=38637

	}

	public static double calculateForAllPremiumCummutatively(int premiumValue, double interestRate, double time,
			Frequency fr) {
		double res = 0;
		double minusFactor = 1.0;
		double frequency = 1;
		switch (fr) {
		case YEARLY:
			frequency = 1;
			break;
		case HALF_YEARLY:
			frequency = 2;
			break;
		case QUARTERLY:
			frequency = 4;
			break;
		}
		minusFactor = 1 / frequency;
		double specialFractionalTime = time % minusFactor;
		if (specialFractionalTime != 0.0) {
			System.out.println(
					"Please enter the proper time period shold be representable in number of half year if premium is half yearly");
			throw new RuntimeException(
					"Please enter the proper time period shold be representable in number of half year if premium is half yearly likewiwe");
		}
		time = time - specialFractionalTime;
		while (time > 0.0) {
			res += calculate(premiumValue, interestRate, time, frequency);
			time = time - minusFactor;
		}
		if (specialFractionalTime != 0.0) {
			res += calculate(premiumValue, interestRate, specialFractionalTime, frequency);
		}
		return res;
	}

	public static double calculate(int premiumValue, double interestRate, double time, double frequency) {
		double futureValue = 0;
		try {
			double interestFactorWhole = 0;
			double totalNumberOfPremium = time * frequency;
			double interestFactorFraction = 1;
			if (totalNumberOfPremium % 1 == 0) {
				interestFactorWhole = Math.pow((1 + ((interestRate / 100) / frequency)), totalNumberOfPremium);
			} else {
				double timeWhole = Math.floor(time);
				interestFactorWhole = Math.pow((1 + ((interestRate / 100) / frequency)), (timeWhole * frequency));
				interestFactorFraction = Math.pow((1 + ((((time - timeWhole) * interestRate) / 100) / frequency)),
						frequency);
			}
			futureValue = premiumValue * interestFactorWhole * interestFactorFraction;
			System.out.println("futureValue=" + futureValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return futureValue;
	}

	public static enum Frequency {
		YEARLY {
			@Override
			public String toString() {
				return "1";
			}
		},
		HALF_YEARLY {
			@Override
			public String toString() {
				return "2";
			}
		},
		QUARTERLY {
			@Override
			public String toString() {
				return "4";
			}
		}
	}

}
