package simple;

public class ConstructorChainingTest {
	public static void main(String[] args) {
		B b = new B(3);
		System.out.println(b);
	}

	static class A {
		A() {
		}
	}

	static class B extends A {
		
		B(int i){}
		
	}

}
