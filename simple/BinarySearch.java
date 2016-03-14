package simple;

public class BinarySearch {
	//binary serach is only appliable to sorted arrays only
	public static void main(String[] args) {
		int[] ar = new int[] { 1, 3, 4, 5, 6, 7, 8, 9 };

		binarySearch(ar,0,ar.length, 7);
	}

	private static int binarySearch(int[] ar,int start,int end, int v) {
		int mid=(start+end)/2;
		if(v > ar[mid]){
			return binarySearch(ar,mid,end,v);
		}	
		else if(v < ar[mid]){
			return binarySearch(ar, start, mid+1, v);
		}else{
			System.out.println(mid);
			return mid;
		}
	}
}
