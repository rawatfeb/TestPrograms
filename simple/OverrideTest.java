package simple;

public class OverrideTest implements Overridable2,Overridable1{

	// a class can implement two interfaces which has same methods in it only one time overrrding required in implementing class
	// but can not have same method name with different return type same parameter passed to both
	//can have two method with same name with return type but parameter passed is different
	
	/*@Override
	public void display() {
		System.out.println("from OverrideTest");
	}
	*/
	
	@Override
	public String display() {
		System.out.println("from OverrideTest ...");
		return null;
	}

	
	public static void main(String[] args) {
		OverrideTest overrideTest = new OverrideTest();
		overrideTest.display();
	}


	
	public static void anonymousTest () 
    {
        Object o = new Object() /* Line 5 */
        {
            public boolean equals(Object obj) 
            {
                return true;
            } 
        } ;     /* Line 11 */
        
        System.out.println(o.equals("Fred"));
    }
	
	
	
	@Override
	public String OverriededDisplay() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String OverriededDisplay(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
