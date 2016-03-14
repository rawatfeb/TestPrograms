package simple;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SingleTonTest implements Cloneable {

	private int data = 5;
	private Custom classTypeData = new Custom();

	SingleTonTest() {
		//throw new RuntimeException();
	}

	static SingleTonTest singleTon = new SingleTonTest();

	public static SingleTonTest getInstance() {
		return singleTon;

	}

	public static void main(String... args) {
		SingleTonTest singleTon = new SingleTonTest();
		System.out.println(singleTon);

		System.out.println(SingleTonTest.getInstance());
		System.out.println(SingleTonTest.getInstance());
		System.out.println(SingleTonTest.getInstance());
		System.out.println(SingleTonTest.getInstance());
		System.out.println(SingleTonTest.getInstance());

		try {
			//cloning the singleTon
			Object cl = singleTon.clone();

			System.out.println("clone one: " + cl);
			SingleTonTest cloneSingleTon = (SingleTonTest) cl;
			System.out.println(cloneSingleTon.data == singleTon.data);
			System.out.println(cloneSingleTon.classTypeData == singleTon.classTypeData);

			System.out.println(cloneSingleTon.classTypeData);

			cloneSingleTon.classTypeData = new Custom();

			System.out.println(singleTon.classTypeData);

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			SingleTonTest singleTonIntance = SingleTonTest.getInstance();
			Class<? extends SingleTonTest> singleTonClass = singleTonIntance.getClass();
			Constructor<?>[] constructors = singleTonClass.getConstructors();
			constructors = singleTonClass.getDeclaredConstructors();
			for (Constructor<?> constructor : constructors) {
				SingleTonTest hackedInstance = (SingleTonTest) constructor.newInstance();
				System.out.println("hackedInstance=" + hackedInstance.data);
			}

		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println();

	}

	private void arrayListClone() {
		ArrayList al = new ArrayList();
		for (int i = 0; i < 10; i++)
			al.add(new Random());

		ArrayList al1 = (ArrayList) al.clone();
		Object vl;
		// Increment all al1's elements:
		for (Iterator e = al1.iterator(); e.hasNext();)
			vl = ((Random) e.next());
	}

	static class Custom {
		int d = 17;
	}

}
