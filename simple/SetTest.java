package simple;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class SetTest {
	public static void main(String[] args) {

		addStringsToTreeSet();

		// addHetrogeneousToHashSet();
		// addHetrogeneousToLinkedHashSet();
		// addHetrogeneousToTreeSet();
	}

	public static void addStringsToTreeSet() {
		TreeSet ts = new TreeSet();
		String s = new String("StringObject");
		ts.add(s);
		String s2 = new String("StringObject");
		ts.add(s2);
		ts.add(new String("StringObject"));
		ts.add(new String("StringObject"));
		ts.add(new String("StringObject"));
		// ts.add(new NameBean("NameBean"));
		// ts.add(new NameBean("NameBean"));
		System.out.println(ts);
		// whatever you add in TreeSet should be comparable
	}

	public static void addHetrogeneousToTreeSet() {
		TreeSet ts = new TreeSet();
		ts.add("StringObject");
		ts.add("StringObject2");
		ts.add(20);
		System.out.println(ts);
	}

	public static void addHetrogeneousToHashSet() {
		Set hs = new HashSet();
		hs.add("StringObject");
		hs.add(20);
		System.out.println(hs);
	}

	public static void addHetrogeneousToLinkedHashSet() {
		Set lhs = new LinkedHashSet();
		lhs.add("StringObject");
		lhs.add(20);
		System.out.println(lhs);
	}

	static class NameBean {
		private String value;

		NameBean(String v) {
			this.value = v;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}

}
