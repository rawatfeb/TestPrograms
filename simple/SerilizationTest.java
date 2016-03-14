package simple;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerilizationTest implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String name;
	public String address;
	public transient int SSN;
	public int number;

	public void mailCheck() {
		System.out.println("Mailing a check to " + name + " " + address);
	}
	
	public static void main(String[] args) {
		serilizeTest();
		deserilizeTest();
		
	}
	
	 public static void serilizeTest()
	   {
		 SerilizationTest e = new SerilizationTest();
	      e.name = "Reyan li";
	      e.address = "Phokka Kuan, Ambehta Peer";
	      e.SSN = 11122333;
	      e.number = 105;
	      try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream("/tmp/SerilizationTest.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(e);
	         out.close();
	         fileOut.close();
	         System.out.println("Serialized data is saved in /tmp/SerilizationTest.ser");
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	   }
	
	 public static void deserilizeTest()
	   {
		 SerilizationTest e = null;
	      try
	      {
	         FileInputStream fileIn = new FileInputStream("/tmp/SerilizationTest.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         e = (SerilizationTest) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("SerilizationTest class not found");
	         c.printStackTrace();
	         return;
	      }
	      System.out.println(" Deserialized Employee...");
	      System.out.println("Name: " + e.name);
	      System.out.println("Address: " + e.address);
	      System.out.println("SSN: " + e.SSN);
	      System.out.println("Number: " + e.number);
	    }
	

}
