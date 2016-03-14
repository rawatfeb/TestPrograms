package design_pattern;

public class BuilderDesignPattern {

	private final Object profession;
	private final Object name;

	//private constructor accepting the builder object
	private BuilderDesignPattern(BuilderDesignPatternBuilder builder) {
		this.profession = builder.profession;
		this.name = builder.name;
	}

	// Actual class appeneded with Builer is good convention for Builder class naming  here BuilderDesignPatternBuilder
	private static class BuilderDesignPatternBuilder {
		//required parameter can be set as final

		//optional parameter
		private Object profession;
		private Object name;

		//public constructor is required
		public BuilderDesignPatternBuilder() {

		}

		//every method will return builder type object  here this
		public BuilderDesignPatternBuilder addProfession(Object profession) {
			this.profession = profession;
			return this;
		}

		public BuilderDesignPatternBuilder addName(Object name) {
			this.name = name;
			return this;
		}

		//fianlly this method will be called on builder to construct real Object by instatiating the actual object with all required field.
		public BuilderDesignPattern build() {
			return new BuilderDesignPattern(this);

		}

	}

	public static void main(String[] args) {
		BuilderDesignPattern finalStateObject = new BuilderDesignPattern.BuilderDesignPatternBuilder()
				.addName("Gaurav").addProfession("software Engineer").build();
		System.out.println(finalStateObject);
	}

	@Override
	public String toString() {
		return "BuilderDesignPattern [profession=" + profession + ", name=" + name + "]";
	}

	//benefits: if there too many parameters or some are of same type for constructing an Object not easy to remember order etc.
	//better to use if parameters are more than three in constructing an Object

}
