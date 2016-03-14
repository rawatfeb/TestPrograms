package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SingletonTest {
public static void main(String[] args) {

	System.out.println(SingletonClass.getInstance());
	System.out.println(SingletonClass.getInstance());
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	System.out.println(SingletonClass.getInstance());
	System.out.println(SingletonClass.getInstance());
	System.out.println(SingletonClass.getInstance());
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
	
	//System.out.println(new SingletonClass());
	
	Class<?> Reflection_SingletonClass = null;
	try {
		 Reflection_SingletonClass = Class.forName("reflection.SingletonClass");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	
	  Constructor<?>[] constr = Reflection_SingletonClass.getClass().getConstructors();
	  System.out.println(constr.length);
	  for (Constructor<?> constructor : constr) {
		  
		  try {
			Object obj = constructor.newInstance();
			if(obj instanceof SingletonClass){
				System.out.println("from : "+(SingletonClass)(obj));
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
}


}

