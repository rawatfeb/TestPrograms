package design_pattern;

public class ProxyDesignPattern {

	public static void main(String[] args) {

		NormalClass normalClass = new NormalClassProxy();
		for (int i = 0; i < 5; i++) {
			normalClass.tryThis();
		}

	}

	public static class NormalClass {
		private String name;

		public void tryThis() {
			System.out.println("Hello");
		}

	}

	public static class NormalClassProxy extends NormalClass {
		private int ALLOWED_TRAIL = 3;
		private int trail;

		@Override
		public void tryThis() {
			if (trail < 3) {
				System.out.println("Hello");
			}
			trail++;
		}

	}

}
