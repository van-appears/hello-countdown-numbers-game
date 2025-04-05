package hcng;

import org.junit.Test;
import static org.junit.Assert.*;

public class OperatorTest {
	
	@Test
	public void testSymbolForPlus() {
		assertEquals(Operator.ADD.getSymbol(), "+");
	}
	
	@Test
	public void testSymbolForSubtract() {
		assertEquals(Operator.SUBTRACT.getSymbol(), "-");
	}
	
	@Test
	public void testSymbolForMultiply() {
		assertEquals(Operator.MULTIPLY.getSymbol(), "*");
	}
	
	@Test
	public void testSymbolForDivide() {
		assertEquals(Operator.DIVIDE.getSymbol(), "/");
	}

}
