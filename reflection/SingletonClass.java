package reflection;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SingletonClass {

	static SingletonClass singleton;

	public static SingletonClass getInstance() {
		if (null == singleton) {
			singleton =new SingletonClass();
		}
		return singleton;
	}

	private SingletonClass() {
		super();
	}

	public static void main(String[] args) {

	}

	@Override
	public String toString() {
		return "SingletonClass [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "Date: "+new SimpleDateFormat("YYYY-MM-DD'T'HH:mm:ss:sss'Z'").format(new Date())+"]";
	}

}
