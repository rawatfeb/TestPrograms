package simple;

public class MainFinalTest {
	//we can declare main method as final if we do not want subclass to have main method
public static final void main(String[] args) {
	System.out.println("From Final Main Method");
}
}



final class MainFinalTestExtends extends MainFinalTest{
	
/*public static void main(String[] args) {
	
}*/
	
	public void display(){
		System.out.println("Hello world...");
	}
}

//final class can not be subclassed
/*class MainFinalTestExtendsExtends extends MainFinalTestExtends{
	
}*/

//We can not declare top level class as static, but only inner class can be declared static.
//class can not have protected modifier
//abstract class can not have any abstract method 
abstract class abstractTest {
	public void display(){
		System.out.println("From abstract");
	}
	
	interface StaticMethodTest{
//		void main();
//		static void main2();
	}
	
	
}