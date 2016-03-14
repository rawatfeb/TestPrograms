package calculator;

public class IncomeTaxCalculator {

	public static void main(String[] args) {

		double annualIncome = 31_771*7+54_264*5 ;  //(2_22_441+2_71_320=493761)
		double basicSalaryMonthly = 23_337;
		double HRAFromEmployerMonthly = 11_667;
		double actualHRPaidYearly = 11_500 * 12;
		double dearnessAllowancesYearly = 8_750 * 12;

		System.out.println("Tax = " + calculate(annualIncome, basicSalaryMonthly, HRAFromEmployerMonthly, actualHRPaidYearly,dearnessAllowancesYearly));

	}

	public enum SLAB {
		SLAB1, SLAB2, SLAB3, SLAB4
	}

	private static double calculate(double annualIncome, double basicSalaryMonthly, double HRAFromEmployerMonthly,
			double actualHRPaidYearly,double dearnessAllowancesYearly) {
		double tax = 0;
		int SLAB1_CAP = 2_50_000;
		double SLAB1_TAX_RATE = 0.0 / 100.0;
		int SLAB2_CAP = 5_00_000;
		double SLAB2_TAX_RATE = 10.0 / 100.0;
		int SLAB3_CAP = 10_00_000;
		double SLAB3_TAX_RATE = 20.0 / 100.0;
		double SLAB4_TAX_RATE = 30.0 / 100.0;
		SLAB slab;

		double SLAB1_TAX = SLAB1_CAP * SLAB1_TAX_RATE;
		double SLAB2_TAX = (SLAB2_CAP - SLAB1_CAP) * SLAB2_TAX_RATE;
		double SLAB3_TAX = (SLAB3_CAP - SLAB2_CAP) * SLAB3_TAX_RATE;
		double SLAB4_TAX = 0;

		double exemptionAbleHR = calculateExemptionAbleHR(basicSalaryMonthly, HRAFromEmployerMonthly,
				actualHRPaidYearly,dearnessAllowancesYearly);
		double exemptionAblePF = calculateExemptionAblePF(basicSalaryMonthly);
		double exemptionAbleInvestment = calculateExemptionAbleInvestment();

		double taxable = annualIncome - exemptionAbleHR - exemptionAblePF - exemptionAbleInvestment;

		if (taxable > SLAB3_CAP) {
			slab = SLAB.SLAB4;
		} else if (taxable > SLAB2_CAP) {
			slab = SLAB.SLAB3;
		} else if (taxable > SLAB1_CAP) {
			slab = SLAB.SLAB2;
		} else {
			slab = SLAB.SLAB1;
		}

		switch (slab) {
		case SLAB1:
			SLAB1_TAX = (taxable * SLAB1_TAX_RATE);
			tax += SLAB1_TAX;
			break;
		case SLAB2:
			SLAB2_TAX = ((taxable - SLAB1_CAP) * SLAB2_TAX_RATE);
			tax += SLAB1_TAX + SLAB2_TAX;
			break;
		case SLAB3:
			SLAB3_TAX = ((taxable - SLAB2_CAP) * SLAB3_TAX_RATE);
			tax += SLAB1_TAX + SLAB2_TAX + SLAB3_TAX;
			break;
		case SLAB4:
			SLAB4_TAX = ((taxable - SLAB3_CAP) * SLAB4_TAX_RATE);
			tax += SLAB1_TAX + SLAB2_TAX + SLAB3_TAX + SLAB4_TAX;
			break;
		}

		/*
		 * UPTO 2_50_000 NIL 2_50_001 to 5_00_000 10 % 5_00_000 to 10_00_000 20
		 * % 10_00_000 and above 30 %
		 * 
		 * 
		 * 
		 * 
		 */

		return tax;
	}

	private static double calculateExemptionAbleInvestment() {
		double exemptionAbleInvestment = 0;
		return exemptionAbleInvestment;
	}

	private static double calculateExemptionAblePF(double basicSalaryMonthly) {
		int PF_CEILING_SALARY = 15_000;
		int MAX_MONTHLY_PFCONTR = (12 * PF_CEILING_SALARY) / 100;

		double monthlyPFContr = ((basicSalaryMonthly * 12) / 100);
		if (monthlyPFContr > MAX_MONTHLY_PFCONTR) {
			monthlyPFContr = MAX_MONTHLY_PFCONTR;
		}
		double exemptionAblePF = (monthlyPFContr * 12) * 2;
		System.out.println("ExemptionAble PF = " + exemptionAblePF);
		return exemptionAblePF;
	}

	private static double calculateExemptionAbleHR(double basicSalaryMonthly, double HRAFromEmployerMonthly,
			double actualHRPaidYearly,double dearnessAllowancesYearly) {
		double exemptionAbleHR = 0;
		//		double fiftyPerOfBasicSal = ((basicSalaryMonthly * 12) * 50) / 100;
		double fortyPerOfBasicSal=((basicSalaryMonthly*12)*40)/100;  // Bengaluru is not metro city
		double actualRentPaidMinTenPer = actualHRPaidYearly - (((basicSalaryMonthly * 12)+dearnessAllowancesYearly) * 10) / 100;
		exemptionAbleHR = Math.min(Math.min(fortyPerOfBasicSal, actualRentPaidMinTenPer), HRAFromEmployerMonthly * 12);
		System.out.println("ExemptionAble HR = " + exemptionAbleHR);
		return exemptionAbleHR;
	}

	// TAX FREE SLAB
	// total CTC
	// HRA
	// PF
	// TAX SAVING

	/*
	 * Exemption of HRA will be available up to the the minimum of the following
	 * three options:
	 * 
	 * 1. Actual house rent allowance received from your employer 2. Actual
	 * house rent paid by you minus 10% of your basic salary 3. 50% of your
	 * basic salary if you live in a metro or 40% of your basic salary if you
	 * live in a non-metro
	 */

}
