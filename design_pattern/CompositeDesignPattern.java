package design_pattern;

import java.util.ArrayList;
import java.util.List;

public class CompositeDesignPattern {

	public interface Shape {

		public void draw(String fillColor);
	}

	//composite class   //all the child will be treated as same for required same operation on all
	public class Drawing implements Shape {

		//collection of Shapes
		private List<Shape> shapes = new ArrayList<Shape>();

		@Override
		public void draw(String fillColor) {
			for (Shape sh : shapes) {
				sh.draw(fillColor);
			}
		}

		//adding shape to drawing
		public void add(Shape s) {
			this.shapes.add(s);
		}

		//removing shape from drawing
		public void remove(Shape s) {
			shapes.remove(s);
		}

		//removing all the shapes
		public void clear() {
			System.out.println("Clearing all the shapes from drawing");
			this.shapes.clear();
		}
	}

	public static class Triangle implements Shape {

		@Override
		public void draw(String fillColor) {
			System.out.println("Drawing Triangle with color " + fillColor);
		}

	}

	public static class Circle implements Shape {

		@Override
		public void draw(String fillColor) {
			System.out.println("Drawing Circle with color " + fillColor);
		}

	}

}
