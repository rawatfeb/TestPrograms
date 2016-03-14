package design_pattern;

public class FactoryDesignPattern {

	interface commonFunctionalityVehicle {
		String vehicleNo = "";

		void drive();
	}

	static class Car implements commonFunctionalityVehicle {
		String wheel = "four";

		@Override
		public void drive() {
		}

	}

	static class Bike implements commonFunctionalityVehicle {
		String wheel = "two";

		@Override
		public void drive() {
		}

	}

	public static commonFunctionalityVehicle getCar() {
		return new Car();
	}

	public static commonFunctionalityVehicle getBike() {
		return new Bike();
	}

}
