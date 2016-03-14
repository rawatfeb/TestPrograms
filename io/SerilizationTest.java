package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

public class SerilizationTest {

	private static Set<String> stringSet = new HashSet<String>();

	public static void main(String[] args) {
		try {
			stringSet.add("Hello");
			stringSet.add("How");
			stringSet.add("are");
			stringSet.add("You");

			FileOutputStream fos = new FileOutputStream("stringSet.txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(stringSet);
			oos.close();
			fos.close();
			System.out.println("DONE");
			
			FileInputStream fin = new FileInputStream("stringSet.txt");
			ObjectInputStream oin = new ObjectInputStream(fin);
			Object objRead = oin.readObject();
			System.out.println(objRead);
			oin.close();
			fos.close();

			System.out.println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
