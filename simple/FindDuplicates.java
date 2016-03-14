package simple;

public class FindDuplicates {
	public static void main(String[] args) {
		int[] ar = new int[] { 2, 5, 8, 3, 5, 8, 1, 6, 0 };

		findDuplicates(ar);
	}

	private static void findDuplicates(int[] ar) {

		for (int j = 0; j < ar.length; j++) {
			int traverser = ar[j];
			for (int k = j + 1; k < ar.length; k++) {
				if (ar[k] == traverser) {
					System.out.println("Found duplicate=" + traverser);
				}
			}
		}

	}
}
