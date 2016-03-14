package junit;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CalculatorTest {
	@Test
	public void testAdd() {
		Calculator calc = new Calculator();
		calc.setA(10);
		calc.setB(5);
		assertEquals(15, calc.getA() + calc.getB());
	}
}
