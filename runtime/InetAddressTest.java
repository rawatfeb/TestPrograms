package runtime;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressTest {

	public static void main(String[] args) {
		mailAddressTest();

		inetAddrTest();

	}

	private static void inetAddrTest() {
		try {
			InetAddress inetAddr = InetAddress.getByName("swat-tools-a.westlan.com");
			System.out.println(inetAddr);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private static void mailAddressTest() {

		//InternetAddress addressFrom = new InternetAddress(mailfrom);

	}

}
