package hcng.countdown;

import java.util.List;
import java.util.Stack;

import org.junit.Before;
import org.junit.Test;

import hcng.Operator;
import hcng.countdown.CountdownEvaluator;

import static org.junit.Assert.*;
import static hcng.test.Setup.createStack;

public class CountdownEvaluatorTest {

	private CountdownEvaluator evaluator;

	@Before
	public void createNewPattern() {
		evaluator = new CountdownEvaluator();
	}
	
	@Test
	public void testAllowableOperatorsContainsFourEntries() {
		List<Operator> operators = evaluator.getAllowableOperators();
		
		assertEquals(operators.size(), 4);
		assertTrue(operators.contains(Operator.ADD));
		assertTrue(operators.contains(Operator.SUBTRACT));
		assertTrue(operators.contains(Operator.MULTIPLY));
		assertTrue(operators.contains(Operator.DIVIDE));
	}
	
	@Test
	public void testAddOperator() {
		Stack<Integer> stack = createStack(10, 2);
		Stack<Integer> result = evaluator.evaluate(stack, Operator.ADD);
		
		assertEquals(result.size(), 1);
		assertEquals(result.pop(), new Integer(12));
	}

	@Test
	public void testSubtractOperator() {
		Stack<Integer> stack = createStack(10, 2);
		Stack<Integer> result = evaluator.evaluate(stack, Operator.SUBTRACT);
		
		assertEquals(result.size(), 1);
		assertEquals(result.pop(), new Integer(8));
	}
	
	@Test
	public void testMultiplyOperator() {
		Stack<Integer> stack = createStack(10, 2);
		Stack<Integer> result = evaluator.evaluate(stack, Operator.MULTIPLY);
		
		assertEquals(result.size(), 1);
		assertEquals(result.pop(), new Integer(20));
	}
	
	@Test
	public void testDivideOperator() {
		Stack<Integer> stack = createStack(10, 2);
		Stack<Integer> result = evaluator.evaluate(stack, Operator.DIVIDE);
		
		assertEquals(result.size(), 1);
		assertEquals(result.pop(), new Integer(5));
	}

	@Test
	public void testSafeOperations() {
		Stack<Integer> stack = createStack(10, 2);
		assertTrue(evaluator.isOperationAllowed(stack, Operator.ADD));
		assertTrue(evaluator.isOperationAllowed(stack, Operator.SUBTRACT));
		assertTrue(evaluator.isOperationAllowed(stack, Operator.MULTIPLY));
		assertTrue(evaluator.isOperationAllowed(stack, Operator.DIVIDE));
	}

	@Test
	public void testUnsafeDivideOperator() {
		Stack<Integer> stack = createStack(10, 3);
		assertFalse(evaluator.isOperationAllowed(stack, Operator.DIVIDE));
	}

	@Test
	public void testUnsafeSubtractOperator() {
		Stack<Integer> stack = createStack(3, 10);
		assertFalse(evaluator.isOperationAllowed(stack, Operator.SUBTRACT));
	}

	@Test
	public void testStackIsReducedByOperation() {
		Stack<Integer> stack = createStack(1, 2, 3, 4, 5);
		Stack<Integer> result = evaluator.evaluate(stack, Operator.ADD);
		
		assertEquals(result.size(), 4);
		assertEquals(result, createStack(1, 2, 3, 9));
	}

}
