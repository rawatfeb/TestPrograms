package simple;

public class InnerClassesTest {
	private int privateVaraible = 9;
	public int pulicVariable = 67;
	protected int protectedVariable = 33;
	static int staticVariable = 70;

	void outerInstanceMethod() {
		System.out.println("outerInstanceMethod");
	}

	static void outerStaticMethod() {
		System.out.println("outerStaticMethod");
	}

	static class InnerClass {
		public void instanceInnerMethod() {
			//	System.out.println(privateVaraible);  //can not access non static member	
			//	System.out.println(pulicVariable);//can not access non static member
			//	System.out.println(protectedVariable);//can not access non static member
			System.out.println(staticVariable);
			//			outerInstanceMethod();  //can not access non static method
			outerStaticMethod();
		}

		public static void staticInnerMethod() {
			//	System.out.println(privateVaraible);  //can not access non static member	
			//	System.out.println(pulicVariable);//can not access non static member
			//	System.out.println(protectedVariable);//can not access non static member
			System.out.println(staticVariable);
			//			outerInstanceMethod();  //can not access non static method
			outerStaticMethod();
		}
	}

}
