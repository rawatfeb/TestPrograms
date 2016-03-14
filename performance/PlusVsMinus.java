package performance;

public class PlusVsMinus {
public static void main(String[] args) {
	int number=0;
	int itr=500000000;
	
	long startTime = System.currentTimeMillis();
	for (int i = 0; i < itr; i++) {
		 number = number * 10 - 5;
	}
	System.out.println(System.currentTimeMillis()-startTime);
	
	
	startTime = System.currentTimeMillis();
	for (int i = 0; i < itr; i++) {
		 number = number * 10 + 5;
	}
	System.out.println(System.currentTimeMillis()-startTime);
	
	
	
}
}
