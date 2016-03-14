package ExceptionHandling;

import java.net.InetAddress;

public class ReturnInCacth {
	public static void main(String[] args) {
		System.out.println(testReturn());
	}

	public static String testReturn() {

		try {
			return InetAddress.getByName("localhost2").getCanonicalHostName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Hello";

	}
}
