package simple;

//implementation of ArraryList
public class MyArrayList {
	private int initialSize = 0;
	private int cursor = 0;
	Object[] internalArray;

	public MyArrayList() {
		super();
		initialSize = 10;
		internalArray = new Object[initialSize];
	}

	public Object get(int index) {
		if (index < initialSize) {
			return internalArray[index];
		} else {
			throw new RuntimeException("Index Array Out of Bounde Exception");
		}
	}

	public int get(Object obj) {
		for (int i = 0; i < internalArray.length; i++) {
			if (internalArray[i].equals(obj)) {
				return i;
			}
		}
		return -1;
	}

	public void delete(Object obj) {
		int location = get(obj);
		if (location != -1) {
			for (int i = location; i < internalArray.length; i++) {
				internalArray[location] = internalArray[location + 1];
			}
			cursor--;
		} else {
			new RuntimeException("Object not found Exception");
		}

	}

	public boolean delete(int location) {
		if (location <= cursor) {
			for (int i = location; i < cursor; i++) {
				internalArray[i] = internalArray[i+1];
			}
			cursor--;
		} else {
			throw new RuntimeException("Index Array Out of Bounde Exception");
		}
		return true;
	}

	public void add(Object obj) {
		if (cursor > initialSize) {
			addCapacity();
		}
		internalArray[cursor] = obj;
		cursor++;
	}

	private void addCapacity() {
		initialSize = initialSize * 2;
		Object[] temp = new Object[initialSize];
		System.arraycopy(internalArray, 0, temp, 0, initialSize / 2);
		internalArray = temp;
	}

	public int size() {
		return cursor;
	}

	public static void main(String[] args) {
		MyArrayList mal = new MyArrayList();
		mal.add(new Integer(2));
		mal.add(new Integer(5));
		mal.add(new Integer(1));
		mal.add(new Integer(23));
		mal.add(new Integer(14));
		for (int i = 0; i < mal.size(); i++) {
			System.out.print(mal.get(i) + " ");
		}
		mal.add(new Integer(29));
		System.out.println("Element at Index 5:" + mal.get(5));
		System.out.println("List size: " + mal.size());
		System.out.println("Removing element at index 2: " + mal.delete(2));
		for (int i = 0; i < mal.size(); i++) {
			System.out.print(mal.get(i) + " ");
		}
	}
}
