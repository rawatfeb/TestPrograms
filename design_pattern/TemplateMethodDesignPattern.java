package design_pattern;

public class TemplateMethodDesignPattern {

	public static void main(String[] args) {
		StealingMethod stealingMethod1 = new StealingMethod1();
		stealingMethod1.steal();

		StealingMethod stealingMethod2 = new StealingMethod2();
		stealingMethod2.steal();
	}

	public static abstract class StealingMethod {

		abstract void confuseTheTarget();

		abstract void pickTheTarget();

		abstract void stealTheItem();

		void steal() {
			pickTheTarget();
			confuseTheTarget();
			stealTheItem();

		}

	}

	public static class StealingMethod1 extends StealingMethod {

		@Override
		void pickTheTarget() {
			System.out.println("choosing the target new in the city");
		}

		@Override
		void confuseTheTarget() {
			System.out.println("confusing the target by throwing the down the coin newr to him");
		}

		@Override
		void stealTheItem() {
			System.out.println("stealing the walle while bending down for the coin");
		}

	}

	public static class StealingMethod2 extends StealingMethod {

		@Override
		void pickTheTarget() {
			System.out.println("choosing the target randomly");
		}

		@Override
		void confuseTheTarget() {
			System.out.println("Confusing the target by talking");
		}

		@Override
		void stealTheItem() {
			System.out.println("stealing the item");
		}

	}

}
