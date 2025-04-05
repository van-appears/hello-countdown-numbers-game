package hcng;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static hcng.test.Setup.createStack;

public class PatternTest {

	private Pattern testPattern;
	private Evaluator evaluator = mock(Evaluator.class);
	
	@Before
	public void createNewPattern() {
		reset(evaluator); 
		testPattern = new Pattern(evaluator);
	}
	
	@Test
	public void testRollingPatternIsEmpty() {
		assertEquals(0, testPattern.getResult());
		assertEquals(0, testPattern.getPattern().size());
	}
	
	@Test
	public void testExtendingWithIntegerAddsToPattern() {
		Pattern newPattern = testPattern.extend(1);
		
		assertEquals(1, newPattern.getPattern().size());
		assertEquals("1", newPattern.getPattern().get(0));
	}

	@Test
	public void testExtendingWithIntegerSetsResultToThatInteger() {
		Pattern newPattern = testPattern.extend(1); 
		
		assertEquals(1, newPattern.getResult());
	}
	
	@Test
	public void testASingleIntegerCannotBeExtendedWithAnOperator() {
		Pattern newPattern = testPattern.extend(1); 
		
		assertFalse(newPattern.canExtendWithOperator());
	}
	
	@Test
	public void testExtendingWithTwoIntegersAddsToPattern() {
		Pattern newPattern1 = testPattern.extend(1); 
		Pattern newPattern2 = newPattern1.extend(2);
		
		assertEquals(newPattern2.getPattern().size(), 2);
		assertEquals(newPattern2.getPattern().get(0), "1");
		assertEquals(newPattern2.getPattern().get(1), "2");
	}
	
	@Test
	public void testTwoIntegersCanBeExtendedWithAnOperator() {
		Pattern newPattern1 = testPattern.extend(1);
		Pattern newPattern2 = newPattern1.extend(2);
		
		assertTrue(newPattern2.canExtendWithOperator());
	}
	
	@Test
	public void testExtendingTwoIntegersWithOperatorEvaluatesPattern() {
		Stack<Integer> expectedStack = createStack(1, 2);
		Stack<Integer> resultStack = createStack(3);
		when(evaluator.isOperationAllowed(eq(expectedStack), eq(Operator.ADD)))
			.thenReturn(true);
		when(evaluator.evaluate(eq(expectedStack), eq(Operator.ADD)))
			.thenReturn(resultStack);
		
		Pattern newPattern1 = testPattern.extend(1);
		Pattern newPattern2 = newPattern1.extend(2);
		Optional<Pattern> result3 = newPattern2.extend(Operator.ADD);
		Pattern newPattern3 = result3.get();
		
		assertEquals(3, newPattern3.getResult());	
	}
	
	@Test
	public void testExtensionAfterEvaluationReducesTheStack() {
		Stack<Integer> expectedStack = createStack(1, 2);
		Stack<Integer> resultStack = createStack(3);
		when(evaluator.isOperationAllowed(eq(expectedStack), eq(Operator.ADD)))
			.thenReturn(true);
		when(evaluator.evaluate(eq(expectedStack), eq(Operator.ADD)))
			.thenReturn(resultStack);
		
		Pattern newPattern1 = testPattern.extend(1);
		Pattern newPattern2 = newPattern1.extend(2);
		Optional<Pattern> result3 = newPattern2.extend(Operator.ADD);
		Pattern newPattern3 = result3.get();
			
		assertFalse(newPattern3.canExtendWithOperator());	
	}
	
	@Test
	public void testUnsafeResultsReturnEmptyOptional() {
		Stack<Integer> expectedStack = createStack(1, 2);
		when(evaluator.isOperationAllowed(eq(expectedStack), eq(Operator.ADD)))
			.thenReturn(false);
		
		Pattern newPattern1 = testPattern.extend(1); 
		Pattern newPattern2 = newPattern1.extend(2);
		Optional<Pattern> result3 = newPattern2.extend(Operator.SUBTRACT);
		
		assertFalse(result3.isPresent());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testPatternEndingWithANumberReturnsJustThatNumber() {
		when(evaluator.isOperationAllowed(any(Stack.class), any(Operator.class)))
			.thenReturn(true);
		when(evaluator.evaluate(any(Stack.class), any(Operator.class)))
			.thenReturn(createStack(1, 2, 3));
		
		Pattern newPattern = testPattern.extend(1).extend(2).extend(3);
		List<String> evaluatedPattern = newPattern.getEvaluatedPattern();
		assertEquals(Arrays.asList("3"), evaluatedPattern);
		
		Pattern newPattern2 = newPattern.extend(Operator.ADD).get().extend(4);
		evaluatedPattern = newPattern2.getEvaluatedPattern();
		assertEquals(Arrays.asList("4"), evaluatedPattern);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testSingleOperatorReturnsTwoNumbers() {
		when(evaluator.getAllowableOperators())
			.thenReturn(Arrays.asList(Operator.ADD));
		when(evaluator.isOperationAllowed(any(Stack.class), any(Operator.class)))
			.thenReturn(true);
		when(evaluator.evaluate(any(Stack.class), any(Operator.class)))
			.thenReturn(createStack(1, 2, 3));
		
		Pattern newPattern = testPattern.extend(1).extend(2).extend(3).extend(Operator.ADD).get();
		List<String> evaluatedPattern = newPattern.getEvaluatedPattern();
		assertEquals(Arrays.asList("2", "3", "+"), evaluatedPattern);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testPatternReturnsNumbersForCountOfOperators() {
		when(evaluator.getAllowableOperators())
			.thenReturn(Arrays.asList(Operator.ADD));
		when(evaluator.isOperationAllowed(any(Stack.class), any(Operator.class)))
			.thenReturn(true);
		when(evaluator.evaluate(any(Stack.class), any(Operator.class)))
			.thenReturn(createStack(1, 2, 3));
		
		Pattern newPattern = testPattern
			.extend(1)
			.extend(2)
			.extend(3)
			.extend(4)
			.extend(Operator.ADD).get()
			.extend(5)
			.extend(6)
			.extend(Operator.ADD).get()
			.extend(Operator.ADD).get();
		List<String> evaluatedPattern = newPattern.getEvaluatedPattern();
		assertEquals(Arrays.asList("3", "4", "+", "5", "6", "+", "+"), evaluatedPattern);
	}
	
}
