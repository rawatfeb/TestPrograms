package ExceptionHandling;

public class AutoClosableTest {
public static void main(String...args) throws Exception{
	testAutoClosable();
}

private static void testAutoClosable() throws Exception {
	 class Test implements AutoCloseable{

		@Override
		public void close() throws Exception {
			System.out.println("haa   haa   closing the resources happy reducing your burden");
		}
		
	}
	 
	 try(Test test=new Test()){
		 System.out.println("....");
	 }
	 
	 
	 
}
}
