package simple;

public interface InterfaceTest {

	// interface can have public class
	public class PublicClass {
		public static void main(String[] args) {
			System.out.println("From the Public Class");
			FinalClass finalClass = new FinalClass();
			finalClass.print();
		}

		public void print() {
			System.out.println("printing from the public class");
		}
	}

	// interface can have final class
	final class FinalClass {
		public static void main(String[] args) {
			System.out.println("From the Final Class");
			PublicClass publicClass = new PublicClass();
			publicClass.print();
		}

		public void print() {
			System.out.println("printing from the final class");
		}
	}

	// java 8 default method
	// default void sort() {
	//
	// }

}
