package hcng;

import java.util.List;
import java.util.Stack;

public interface Evaluator {

	List<Operator> getAllowableOperators();
	
	boolean isOperationAllowed(Stack<Integer> numbers, Operator operator);
	
	Stack<Integer> evaluate(Stack<Integer> numbers, Operator operator);
	
}
