package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Hello {
	public static void main(String[] args) {
		System.out.println("Hi");

		Class<?> loadedClass;
		try {
			loadedClass = Class.forName("reflection.TestClass");
			System.out.println(loadedClass.getClass());
			Method[] methods = loadedClass.getMethods();
			Constructor<?>[] constr = loadedClass.getConstructors();
			
			for (int i = 0; i < methods.length; i++) {
				Object arg0 = null;
				Object arg1 = null;
				try {
					methods[i].invoke(arg0, arg1);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
				System.out.println(methods[i]);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
