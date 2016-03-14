package simple;

public class PassValueTest {

	public static void main(String[] args) {

		String s = "hellooo";
		changeString(s);
		System.out.println(s); //string was passed as value(copy)

		String[] stringArray = new String[] { "1", "2", "3" };
		changeArray(stringArray);
		for (String string : stringArray) {
			System.out.println(string);
		}
		////string array was passed as references but can not refer to new references

		
		
	// in java actual copy of references is passed in method arguments pointing to the same object
		

	}

	private static void changeArray(String[] stringArray) {

		//stringArray = new String[] { "changed" };
		stringArray[0]="modified zero index";

	}

	private static void changeString(String s) {
		s = "changed";
	}

}
