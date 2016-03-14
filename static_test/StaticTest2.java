package static_test;

public class StaticTest2 extends StaticTest{
//Static member of child class can be referenced directly by parent class name
	public static void main(String...args) {
		StaticTest2.display();
		System.out.println(StaticTest2.x);
		StaticTest2.display();
	}
}
