package design_pattern;

public class FacedeDesignPattern {

	interface Address {

		String getStreet();

		String getCity();

		int getZip();

		void setStreet(String street);

		void setCity(String city);

		void setZip(int zip);
	}

	
	//facade provides more generic interface to client for easy use
	interface AddressFacade {
		String getAddress();

		void setAddress(String address);
	}

}
