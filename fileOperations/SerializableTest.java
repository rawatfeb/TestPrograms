package fileOperations;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

public class SerializableTest  implements java.io.Serializable {
	    private static final long serialVersionUID = 4L;            //Default serial version uid
	    private static final String fileName = "DemoClassBytes.ser"; //Any random name
	    private static final Logger logger = Logger.getLogger("");
	    //Few data fields
	    //Able to serialize
	    private static String staticVariable;
	    private int intVariable;
	     
	    //Not able to serialize
	    transient private String transientVariable = "this is a transient instance field";
	    private Thread threadClass;
	      
	    public static void main(String[] args) throws IOException, ClassNotFoundException
	    {
	        //Serialization
	     
	    	SerializableTest test = new SerializableTest();
	        test.intVariable = 1;
	        staticVariable = "this is a static variable";
	        writeOut(test);
	        System.out.println("DemoClass to be saved: " + test);
	     
	        //De-serialization
	         
	        System.out.println("DemoClass deserialized: " + readIn());
	    }
	      
	    private static Object readIn() throws IOException, ClassNotFoundException {
	        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(fileName)));
	        return ois.readObject();
	    }
	      
	    private static void writeOut(java.io.Serializable obj) throws IOException {
	        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
	        oos.writeObject(obj);
	        oos.close();
	    }
	      
	    @Override public String toString() {
	        return "DemoClass: final static fileName=" + fileName + ", final static logger=" + logger
	                + ", non-final static staticVariable=" + staticVariable + ", instance intVariable=" + intVariable
	                + ", transient instance transientVariable=" + transientVariable + ", non-serializable instance field threadClass:=" + threadClass;
	    }
	
}
