package simple;

public class CloneTest implements Cloneable {
	public static void main(String... args) throws CloneNotSupportedException {

		CloneTest ct = new CloneTest();
		ct.clone();

	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		System.out.println("from clone");
		return super.clone();
	}

}
