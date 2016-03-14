package design_pattern;

import java.util.HashMap;
import java.util.Map;

public class FlyweightDesignPattern {

	static interface Shape {
		void draw();
	}

	public static class Line implements Shape {

		@Override
		public void draw() {
			// TODO Auto-generated method stub

		}
	}

	public static class Circle implements Shape {

		@Override
		public void draw() {
			// TODO Auto-generated method stub

		}
	}

	
	//ShapeFactory caches the Objects
	public static class ShapeFactory {

		Map<ShapeType, Shape> shapeCache = new HashMap<>();

		Shape getShape(ShapeType type) {
			Shape shape = shapeCache.get(type);
			if (null == shape) {
				if (ShapeType.LINE.equals(type)) {
					shape = new Line();
				}
				if (ShapeType.CIRCLE.equals(type)) {
					shape = new Circle();
				}
				shapeCache.put(type, shape);
			}
			return shape;
		}

		public static enum ShapeType {
			LINE, CIRCLE
		}

	}

}
