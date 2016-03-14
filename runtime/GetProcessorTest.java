package runtime;

public class GetProcessorTest {

	public static void main(String[] args) throws Exception {

		Runtime.getRuntime().traceInstructions(true);

		int numberOfProcessors = Runtime.getRuntime().availableProcessors();
		System.out.println(numberOfProcessors);

	}

}
