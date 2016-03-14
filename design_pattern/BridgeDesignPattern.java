package design_pattern;

public class BridgeDesignPattern {

	public static void main(String[] args) {

		//passing Colour for composition
		Circle circle = new Circle(new GreenColour());
		circle.applyColour();
		Triangle triangle = new Triangle(new RedColour());
		triangle.applyColour();

	}

	//prefering composition over the aggression
	public static abstract class Shape {
		Colour c;

		Shape(Colour c) {
			this.c = c;
		}

		abstract void applyColour();
	}

	interface Colour {
		void applyColour();
	}

	public static class RedColour implements Colour {

		@Override
		public void applyColour() {
			System.out.println("Filled with RED Colour");
		}

	}

	public static class GreenColour implements Colour {

		@Override
		public void applyColour() {
			System.out.println("Filled with RED Colour");
		}

	}

	public static class Triangle extends Shape {

		Triangle(Colour c) {
			super(c);
		}

		@Override
		void applyColour() {
			c.applyColour();
		}

	}

	public static class Circle extends Shape {

		Circle(Colour c) {
			super(c);
		}

		@Override
		void applyColour() {
			c.applyColour();
		}

	}

}
