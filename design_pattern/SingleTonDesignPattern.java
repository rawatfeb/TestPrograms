package design_pattern;

public final class SingleTonDesignPattern {

	public static SingleTonDesignPattern INSTANCE;

	private SingleTonDesignPattern() {
		if (null != INSTANCE) {
			new RuntimeException("Multiple instances are not supported");
		}
	}

	public static SingleTonDesignPattern getInstance() {
		if (null == INSTANCE) {
			synchronized (SingleTonDesignPattern.class) {
				if (null == INSTANCE) {
					INSTANCE = new SingleTonDesignPattern();
				}
			}
		}
		return INSTANCE;
	}

	public static void main(String[] args) {
		SingleTonDesignPattern sinst = SingleTonDesignPattern.INSTANCE;
		System.out.println(sinst);
		sinst = sinst.getInstance();
		System.out.println(sinst);
		System.out.println(sinst.INSTANCE);
		System.out.println(sinst.INSTANCE.INSTANCE);
		System.out.println(sinst.INSTANCE.INSTANCE.INSTANCE.INSTANCE.getInstance());
		System.out.println(sinst.INSTANCE.INSTANCE.INSTANCE.INSTANCE.getInstance().INSTANCE); //8 loop to same address only returns the same object always
	}
}
