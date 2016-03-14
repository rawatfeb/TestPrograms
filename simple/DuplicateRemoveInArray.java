package simple;

public class DuplicateRemoveInArray {
	public static void main(String[] args) {
		int[] ar = new int[] { 2, 3,4,4,4 };
		removeDuplicates1(ar);
		for (int i : ar) {
			System.out.print(" "+i);
		}

		System.out.println();
		removeDuplicates2(ar);
		for (int i : ar) {
			System.out.print(" "+i);
		}

	}

	private static int[] removeDuplicates2(int[] ar) {
		for (int i = 0; i < ar.length-1; i++) {
			if (ar[i] == ar[i + 1]) {
				for (int j = i; j < ar.length-1; j++) {
					ar[j] = ar[j + 1];
				}
			}
		}
		return ar;
	}

	private static int[] removeDuplicates1(int[] ar) {
		return ar;
	}
}
