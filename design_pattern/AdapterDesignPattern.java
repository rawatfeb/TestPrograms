package design_pattern;

public class AdapterDesignPattern {

	//An adapter helps two incompatible interfaces to work together.

	public static void main(String[] args) {

	}

	interface InterfaceAdapter {
		void move();
	}

	interface Interface1 {
		void drive();
	}

	interface Interface2 {
		void run();
	}
}
