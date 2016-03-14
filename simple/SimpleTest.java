package simple;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SimpleTest {
	{
		System.out.println("init");
	}

	public static void main(String... args) throws Exception {
		//long_to_int_Test();
		//double_NaN_test();
		//int_to_byte_Test();
		//arrayAccessTest();
		//		stringToIntTest();
		//		unBoxingTest();
		//		StringInternTest();
		defaultSortingTest();
	}

	//1. Adding in HashSet where hashCode always returns same int value for all values : hashCode only used to quickly identify that two are not equal
	// if two hashCode are equal then it will go to equals to really check are they equal or not.
	//2.toString of HashMap {key=value, key2=value2}
	//3. Collection Subtypes: List Set SortedSet NavigableSet Queue Deque // Map is not a subtype of Collection
	//4. String.charAt() is inclusive
	//5. cannot create generic array new T[10]  //Generics is used for type checking during compile time:
	//6. you can always do String[] strings=(String[])(new Object[10]);
	//7. class will be loaded before main executes and then static block //instace init only will be called when new instance has been created
	//8. find longest sequence and minimum differance in splitting in two set 
	//9. minimum  difference in sum of two set splitted from one.

	public static void defaultSortingTest() {
		List<String> list = new ArrayList<String>();
		list.add(new String("009"));
		list.add(new String("0010"));
		list.add(new String("0021"));
		list.add(new String("0011"));
		Collections.sort(list);
		System.out.println(list);
	}

	public static void dateTest(){
		java.util.Date  d=new java.util.Date(System.currentTimeMillis());
		System.out.println(d);
		java.util.Date  sd=new java.sql.Date(System.currentTimeMillis());
		System.out.println(sd);
	}
	
	public static void longestSequence() {
		int a[] = { 6, 21, 4, 22, 1, 3, 2, 23, 24, 25, 26, 27, 28 };
		int psl = 0;
		int pss = 0;
		if (a.length == 0) {
			return;
		} else if (a.length == 1) {
			System.out.println(Arrays.toString(a));
		} else {
			Arrays.sort(a);
			System.out.println(Arrays.toString(a));
			int sl = 0;
			int ss = 0;
			for (int i = 0; i < a.length - 1; i++) {

				if (a[i + 1] - a[i] == 1) {
					sl++;
				} else {
					if (sl >= psl) {
						psl = sl;
						sl = 0;
						pss = ss;
						ss = i + 1;
					}
				}

			}
		}
		psl++;
		int[] outputArray = new int[psl];
		System.arraycopy(a, pss, outputArray, 0, psl);
		System.out.println(Arrays.toString(outputArray));
	}

	static void WrapperTest() {
		// values from -128 to 127 cached valueOf returns the cached instance (or sometime it also cached 128 or more than it) 
		//caching range -128 to sun.misc.VM.getSavedProperty("java.lang.Integer.IntegerCache.high"); ( normally 127 )
		System.out.println((new Short((short) 20)) == (new Short((short) 20)));
		System.out.println((new Long(20L)) == (new Long(20L)));
		System.out.println("Long valueOf=" + (Long.valueOf(450) == Long.valueOf(450)));
		System.out.println((new Integer("20")) == (new Integer("20")));
		System.out.println("Integer valueOf=" + (Integer.valueOf(450) == Integer.valueOf(450)));
		System.out.println("Integer valueOf=" + (Integer.valueOf(450) == Integer.valueOf(450)));
		System.out.println("Integer valueOf=" + (Integer.valueOf(450) == Integer.valueOf(450)));
		System.out.println("Integer valueOf=" + (Integer.valueOf(126) == Integer.valueOf(126)));
		System.out.println("Integer valueOf=" + (Integer.valueOf(126) == Integer.valueOf(126)));
		System.out.println("Integer valueOf=" + (Integer.valueOf(127) == Integer.valueOf(127)));
		System.out.println("Integer valueOf 128=" + (Integer.valueOf(128) == Integer.valueOf(128)));
		System.out.println("Integer valueOf 128=" + (Integer.valueOf(128) == Integer.valueOf(128)));
		System.out.println((new Double(20)) == (new Double(20)));
		System.out.println("Double valueOf=" + (Double.valueOf(450) == Double.valueOf(450)));
		System.out.println((new Boolean("true")) == (new Boolean("truE")));
		//		short s=127;
		//		short s=128;
		Integer a = 1000;

	}

	//isntatian order test

	static void valueOfTest() {
		//		valueOf returns the cached instance
		System.out.println(Boolean.valueOf("true"));
		System.out.println(Boolean.valueOf("truE"));
		System.out.println(Boolean.valueOf("false"));
		System.out.println(Boolean.valueOf("FALSE"));
		System.out.println(Boolean.valueOf("true").equals(Boolean.valueOf("truE")));
		System.out.println(Boolean.valueOf("true") == Boolean.valueOf("truE"));
		boolean parsedBoolean = Boolean.parseBoolean("tRue"); // parseBoolean (parseXXX) returns primitive type value
	}

	static void arrayDeclarationTest() {
		int[] intArray = { 1, 5, 6 };
		int[] intArray2 = new int[] { 1, 5, 6 };
		int[][] int2DArray = { { 1, 5, 6 }, { 4, 8, 9 } };
		int[][] multi = new int[5][10];
		int[][] multi2 = new int[5][];
		int[][] multi3 = new int[][] { { 0, 0, }, { 0, 0, 0 }, };

		//		int[][] int2DArray={1,5,6}{4,8,9};		//not valid
		//		int[][] int2DArray={1,5,6},{4,8,9};	//not valid

	}

	static void parseString() {
		int number = 0;

		String inputString = "8892";

		char[] charArray = inputString.toCharArray();

		for (char c : charArray) {
			int intValue = c - '0';
			if (intValue >= 0 && intValue <= 9) {
				number = number * 10 - intValue;
			}
		}
		System.out.println(-number);

	}

	static class Reflector {
		public static void main(String[] args) throws Exception {
			Set<String> s = new HashSet<String>();
			s.add("foo");
			Iterator it = s.iterator();
			Method m = it.getClass().getMethod("hasNext");
			System.out.println(m.invoke(it));
		}

		//In this program, the method is selected from the class represented by the Class object that is returned by it.getClass.
		//This is the dynamic type of the iterator, which happens to be the private nested class java.util.HashMap.KeyIterator.
		//The reason for the IllegalAccessException is that this class is not public and comes from another package: You cannot legally access a member of a nonpublic type from another package
	}

	public static class Name {
		private final String first, last;

		public Name(String first, String last) {
			this.first = first;
			this.last = last;
		}

		public boolean equals(Object o) {
			if (!(o instanceof Name))
				return false;
			Name n = (Name) o;
			return n.first.equals(first) && n.last.equals(last);
		}

		public static void main(String[] args) {
			Set<Name> s = new HashSet<Name>();
			s.add(new Name("Mickey", "Mouse"));
			System.out.println(s.contains(new Name("Mickey", "Mouse")));
			//will print false because hashCode are different which tells the objects are also different 
			//if hashCode is same which tells objects might be same we need to confirm with it toString()
		}
	}

	public static class Elvis {
		//cycle initilization problem
		public static final Elvis INSTANCE = new Elvis();
		private final int beltSize;
		private static final int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);

		private Elvis() {
			beltSize = CURRENT_YEAR - 1930;
		}

		public int beltSize() {
			return beltSize;
		}

		public static void main(String[] args) {
			System.out.println("Elvis wears a size " + INSTANCE.beltSize() + " belt.");
		}
	}

	private static void HelloGoodbye() {
		try {
			System.out.println("Hello world");
			System.exit(0);
		} finally {
			System.out.println("Goodbye world");
		}

		//Use shutdown hooks for behavior that must occur before the VM exits.
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Goodbye world");
			}
		});
		System.exit(0);
		//In summary, System.exit stops all program threads immediately; it does not cause finally blocks to execute, but it does run shutdown hooks before halting the VM.
	}

	private static void BigDelight() {
		System.out.println(Byte.MIN_VALUE + " " + Byte.MAX_VALUE);
		System.out.println(0x90); //1001_0000 = 2^7+2^4=128+16=144  //widen to int
		System.out.println((byte) 0x90);
		for (byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE; b++) {
			if (b == 0x90)
				System.out.print("Joy!");
		}
		//Avoid mixed-type comparisons, because they are inherently confusing.
	}

	private static void LinePrinter() {
		/* // Note: is Unicode representation of linefeed (LF) */
		char c = 0x000A; //0x000A
		System.out.println(c);
		System.out.println("Done");
		//initialize c with an escape sequence instead of a hex integer literal, and Avoid Unicode escapes except where they are truly necessary.
	}

	private static void StringInternTest() {

		String test1 = "Test";
		String test2 = "Test";
		String test3 = new String("Test2");
		String test4 = test3.intern(); //intern will try to find string in String costant pool and will refer that if not will create there

		System.out.println(test1 == test2);
		System.out.println(test2 == test3);
		System.out.println(test3 == test4);
		System.out.println(test1 == test4);

	}

	static class ArrayTest<T> {
		public <T> T[] returnArray() {
			System.out.println("");
			//			return new T[10];
			return (T[]) new Object[10];
		}
	}

	private static void unBoxingTest() {

		Integer i = 0;
		Integer j = 0;

		if (i == j) {
			System.out.println("i and j both are unboxed automatically");
		}

		if (i != j) {
			System.out.println("i and j reference check");
		}

	}

	private static void genericMapTest() {
		//Generic Syntax Test
		Map<String, String> mp = new HashMap();
		mp = new HashMap<>();
		mp = new HashMap<String, String>();
		mp = new HashMap<String, String>();
	}

	private static void stringToIntTest() {
		String s = "345";
		System.out.println(Integer.parseInt(s)); // parseInt static utility method
		System.out.println(Integer.parseInt(s, 6));
	}

	private static void arrayAccessTest() {
		int ar[][] = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 } };
		System.out.println(ar[1][1]);
	}

	private static void int_to_byte_Test() {
		int i = 128;
		int i2 = 127;
		byte b = (byte) i;
		byte b2 = (byte) i2;
		System.out.println(b); // -128
		System.out.println(b2); //127
	}

	private static void double_NaN_test() {
		double d = Double.NaN;
		double e = 0;
		System.out.println(d);
		System.out.println(d * e); // will print NaN not 0

		Double D = Double.NaN;
		System.out.println(D.doubleValue());
		System.out.println(Double.NaN == 0.0);

	}

	private static void long_to_int_Test() {
		long l = 100000;
		int i = (int) l;
		System.out.println("explicit casting or narrowing from long to int" + i);
		System.out.println(Long.MAX_VALUE); // 9223372036854775807 	  19 digit   start with 9		// 9.223372036854776E18
		System.out.println(Long.MIN_VALUE); // -9223372036854775808
		System.out.println(Long.SIZE); // 64 bit
		System.out.println(Math.pow(2, 63)); // a to power b

		System.out.println(Double.SIZE);
		System.out.println(Float.SIZE);
		System.out.println(Integer.SIZE);
		System.out.println(Integer.MAX_VALUE); //2147483647

		long li = 2147483648L;
		long li2 = 2147483647;
		int i2 = (int) li;
		int i3 = (int) li2;
		System.out.println(i2);
		System.out.println(i3);

	}

}
