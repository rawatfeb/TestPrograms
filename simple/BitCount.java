package simple;

public class BitCount {

	public static void main(String[] args) {
		
		
		int bitCount = 0;
		int z = 7;
		while (z != 0) {
			System.out.println(Integer.toBinaryString(z));
			// increment count if last binary digit is a 1:
			bitCount += z & 1;
			z = z >> 1; // shift Z by 1 bit to the right
			// z = z >>> 1; // shift Z by 1 bit to the right 0 will be added at
			// left
			System.out.println(Integer.toBinaryString(z));
		}
		System.out.println(bitCount);
	}
}
