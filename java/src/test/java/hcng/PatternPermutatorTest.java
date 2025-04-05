package hcng;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static hcng.test.Setup.createStack;

public class PatternPermutatorTest {

	private PatternPermutator testPermutator;
	private Evaluator evaluator = mock(Evaluator.class);
	
	@Before
	public void initialise() {
		reset(evaluator); 
		testPermutator = new PatternPermutator(evaluator);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testNoEvaluationsWhenOnlyASingleNumber() {
		List<Integer> numbers = Arrays.asList(123);
		testPermutator.find(numbers, 10);
		
		verify(evaluator, times(0)).evaluate(any(Stack.class), any(Operator.class));
	}
	
	@Test
	public void testNoResultWhenSingleNumberDoesntMatchResult() {
		List<Integer> numbers = Arrays.asList(123);
		Optional<List<String>> pattern = testPermutator.find(numbers, 10);
		
		assertFalse(pattern.isPresent());
	}
	
	@Test
	public void testResultWhenSingleMatchedResult() {
		List<Integer> numbers = Arrays.asList(123);
		Optional<List<String>> pattern = testPermutator.find(numbers, 123);
		
		List<String> expected = Arrays.asList("123");
		assertTrue(pattern.isPresent());
		assertEquals(pattern.get(), expected);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testPatternsAreCreatedForAllNumbers() {
		List<Integer> numbers = Arrays.asList(123, 456);
		List<Operator> operators = Arrays.asList(Operator.ADD);

		when(evaluator.getAllowableOperators()).thenReturn(operators);
		when(evaluator.isOperationAllowed(any(Stack.class), any(Operator.class)))
			.thenReturn(true);
		when(evaluator.evaluate(any(Stack.class), any(Operator.class)))
			.thenReturn(createStack(789));
	
		testPermutator = new PatternPermutator(evaluator);		
		testPermutator.find(numbers, 10);
		
		verify(evaluator).evaluate(createStack(123, 456), Operator.ADD);
		verify(evaluator).evaluate(createStack(456, 123), Operator.ADD);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testPatternsAreCreatedForAllOperators() {
		List<Integer> numbers = Arrays.asList(123, 456);
		List<Operator> operators = Arrays.asList(
			Operator.ADD, Operator.SUBTRACT, Operator.MULTIPLY, Operator.DIVIDE);

		when(evaluator.getAllowableOperators()).thenReturn(operators);
		when(evaluator.isOperationAllowed(any(Stack.class), any(Operator.class)))
			.thenReturn(true);
		when(evaluator.evaluate(any(Stack.class), any(Operator.class)))
			.thenReturn(createStack(789));
		
		testPermutator = new PatternPermutator(evaluator);
		testPermutator.find(numbers, 10);
		
		verify(evaluator).evaluate(createStack(123, 456), Operator.ADD);
		verify(evaluator).evaluate(createStack(123, 456), Operator.SUBTRACT);
		verify(evaluator).evaluate(createStack(123, 456), Operator.MULTIPLY);
		verify(evaluator).evaluate(createStack(123, 456), Operator.DIVIDE);
		verify(evaluator).evaluate(createStack(456, 123), Operator.ADD);
		verify(evaluator).evaluate(createStack(456, 123), Operator.SUBTRACT);
		verify(evaluator).evaluate(createStack(456, 123), Operator.MULTIPLY);
		verify(evaluator).evaluate(createStack(456, 123), Operator.DIVIDE);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testPatternExtendedWithNumberHasAResultOfThatNumber() {
		List<Integer> numbers = Arrays.asList(123, 456, 789);
		List<Operator> operators = Arrays.asList(Operator.ADD);
		
		when(evaluator.getAllowableOperators()).thenReturn(operators);
		when(evaluator.isOperationAllowed(any(Stack.class), any(Operator.class)))
			.thenReturn(true);
		when(evaluator.evaluate(any(Stack.class), any(Operator.class)))
			.thenReturn(createStack(987));
		
		testPermutator = new PatternPermutator(evaluator);
		Optional<List<String>> pattern = testPermutator.find(numbers, 789);
		
		List<String> expected = Arrays.asList("123", "456", "789");
		assertTrue(pattern.isPresent());
		assertEquals(pattern.get(), expected);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testOperationMatchedResultReturnsPattern() {
		List<Integer> numbers = Arrays.asList(123, 400, 789);
		List<Operator> operators = Arrays.asList(Operator.ADD, Operator.SUBTRACT);
		
		when(evaluator.getAllowableOperators()).thenReturn(operators);
		when(evaluator.isOperationAllowed(any(Stack.class), any(Operator.class)))
			.thenReturn(true);
		when(evaluator.evaluate(any(Stack.class), any(Operator.class)))
			.thenReturn(createStack(987));
		when(evaluator.evaluate(eq(createStack(123, 789, 400)), eq(Operator.SUBTRACT)))
			.thenReturn(createStack(123, 389));
		when(evaluator.evaluate(eq(createStack(123, 389)), eq(Operator.ADD)))
			.thenReturn(createStack(512));
		
		testPermutator = new PatternPermutator(evaluator);
		
		Optional<List<String>> pattern = testPermutator.find(numbers, 512);		
		List<String> expected = Arrays.asList("123", "789", "400", "-", "+");
		assertTrue(pattern.isPresent());
		assertEquals(pattern.get(), expected);
	}
	
	
	@Test
	@SuppressWarnings("unchecked")
	public void testNoOperationsAllowedReturnsNoPattern() {
		List<Integer> numbers = Arrays.asList(123, 400, 789);
		List<Operator> operators = Arrays.asList(Operator.ADD);
		
		when(evaluator.getAllowableOperators()).thenReturn(operators);
		when(evaluator.isOperationAllowed(any(Stack.class), any(Operator.class)))
			.thenReturn(true);
		when(evaluator.evaluate(any(Stack.class), any(Operator.class)))
			.thenReturn(createStack(987));
		
		testPermutator = new PatternPermutator(evaluator);
		
		Optional<List<String>> pattern = testPermutator.find(numbers, 512);		
		assertFalse(pattern.isPresent());
	}
}
