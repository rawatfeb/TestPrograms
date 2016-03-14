package static_test;

public class StaticTest {
	static int x=4;

	static {
		System.out.println("Class is Loaded by ClassLoader="+StaticTest.class.getClassLoader()); //test when and how many times is loaded
	}
	
	
	{
		System.out.println("Instace INIT will be each time class is instaiated"+StaticTest.class.getClassLoader());
	}
	
	public static void main(String...args){
	//System.out.println(x);
	//int x=x;

	
	//intitateClass();
	//loadClassTest();
	
	
	
	staticVaraibleUpdatedByInstanceTest();
	
	
	}

	private static void staticVaraibleUpdatedByInstanceTest() {
	
		System.out.println(x);
		StaticTest si = new StaticTest();
		si.x=30;
		System.out.println(x);
		StaticTest si2 = new StaticTest();
		si2.x=50;
		System.out.println(x);
		System.out.println(si.x);
	}

	private static void loadClassTest() {
		try {
			
			
			System.out.println(StaticTest.class.getClassLoader().getParent());
			System.out.println(StaticTest.class.getClassLoader().getParent().getClass());
			
			StaticTest.class.getClassLoader().loadClass("Main");
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}

	private static void intitateClass() {
		new StaticTest();
		new StaticTest();
		new StaticTest();		
	}


public static void display(){
System.out.println("from StaticTest");	
}


}
