package simple;

public class DecimalToBainary {
	public static void main(String[] args) {
		int n = 25;

		decimalToBainary(n);
		builtInBinaryConversion(n);
	}

	private static void builtInBinaryConversion(int n) {
		// TODO Auto-generated method stub
		System.out.println(Integer.toBinaryString(n));
	}

	private static void decimalToBainary(int n) {
		// TODO Auto-generated method stub

		int binary[] = new int[32];
		int index = 0;
		while (n != 0) {
			binary[index++] = n % 2;
			n = n / 2;
		}
		
		for (int i : binary) {
			System.out.print(i);
			if(index-- - 1==0)break;
		}
		System.out.println("\n");
	}
}