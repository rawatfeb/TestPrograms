package simple;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class InetAddressTest {
public static void main(String...args) throws Exception{
	inetAddressTest();
}

private static void inetAddressTest() throws UnknownHostException {
	String host="128.136.179.64";
	InetAddress inet = InetAddress.getByName(host);
	System.out.println(inet.getCanonicalHostName());
	System.out.println(inet.getHostName());
	System.out.println(InetAddress.getLoopbackAddress());
}
}
