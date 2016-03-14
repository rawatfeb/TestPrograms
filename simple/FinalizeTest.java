package simple;

public class FinalizeTest {

	public static void main(String[] args) throws InterruptedException {

		while (true) {
			FinalizeTest finalizeTest = new FinalizeTest();
			finalizeTest.print();
			finalizeTest = null;
			Thread.sleep(1000);
		}

	}

	public void print() {
		System.out.println("Hi World");
		String s = new String("This is to Fill up The Heap Space Quickly");
		int c = 0;
		while (c != 10) {
			s += s;
			c++;
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		System.out.println("Finalize Method is called! Object is to be collected from Garbage. ");
	}

}
