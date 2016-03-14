package ExceptionHandling;

public class MyCustomRuntimeException extends RuntimeException{
	//custom exception which extends the RuntimeException are unchecked Exception because RuntimeException is unchecked
	//It is also possible to create an unchecked exception by extending Error 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6601927576949177405L;

	{
		System.out.println("MyCustomRuntimeException instantaited");
	}
	
}
