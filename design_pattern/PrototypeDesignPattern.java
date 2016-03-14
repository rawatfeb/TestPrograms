package design_pattern;

public class PrototypeDesignPattern {

	//This pattern uses java cloning to copy the existing object to reduce the overhead of creating new object is heavy process in some cases.

	public static void main(String[] args) {
		UserDetail userDetail = new UserDetail();
		userDetail.setName(new StringBuilder("hello"));
		userDetail.setAge(20);
		System.out.println(userDetail);
		Object clonedUserDetail = userDetail.clone();
		System.out.println(clonedUserDetail);

	}

	static class UserDetail implements Cloneable {
		private StringBuilder name;
		private int age;


		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		@Override
		public Object clone() {
			UserDetail newUser = new UserDetail();
			newUser.setName(new StringBuilder(name));
			return newUser;
		}

		@Override
		public String toString() {
			return "UserDetail [name=" + name + " "+System.identityHashCode(name)+", age=" + age + "]";
		}

		public StringBuilder getName() {
			return name;
		}

		public void setName(StringBuilder name) {
			this.name = name;
		}

	}

}
