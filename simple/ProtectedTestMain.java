package simple;
import com.test.TestProtected;


public class ProtectedTestMain extends TestProtected{

	
	public static void main(String...args){
		com.test.TestProtected pt = new com.test.TestProtected();
		//pt.protectedMethod();
		
		 ProtectedTestMain t = new ProtectedTestMain();
		
		t.protectedMethod();
		
		try {
			t.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
