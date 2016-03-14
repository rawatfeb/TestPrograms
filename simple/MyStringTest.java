package simple;

public class MyStringTest {

	public static void main(String[] args) {
		String s = new String("Hello world");
		System.out.println(s);
		//Avoid reusing the names of platform classes, and never reuse class names from java.lang,
	}

	public static class String {
		private final java.lang.String s;

		public String(java.lang.String s) {
			this.s = s;
		}

		public java.lang.String toString() {
			return s;
		}
	}
}
