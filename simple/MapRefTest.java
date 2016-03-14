package simple;

import java.util.HashMap;
import java.util.Map;

public class MapRefTest {
	
	//values are passed as reference you can varify in this eaxmple
	
public static void main(String[] args) {
	Map<String,MyClass> mp=new HashMap<String,MyClass>();
	MyClass myClass1=new MyClass();
	myClass1.setSerNo(1);
	mp.put("object1", myClass1);
	MyClass myClass2=new MyClass();
	myClass2.setSerNo(2);
	mp.put("object2", myClass2);
	System.out.println(mp);
	
	MyClass mc = mp.get("object2");
	mc.setSerNo(3);
	System.out.println(mp);
	
	changeState(mc);
	System.out.println(mp);
}

private static void changeState(MyClass mc){
	mc.setSerNo(4);
}

static class MyClass{
	int serNo;

	public int getSerNo() {
		return serNo;
	}

	public void setSerNo(int serNo) {
		this.serNo = serNo;
	}

	@Override
	public String toString() {
		return "MyClass [serNo=" + serNo + ", getSerNo()=" + getSerNo()+"]";
	}
}

}
