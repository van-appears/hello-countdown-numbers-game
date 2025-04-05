package hcng.countdown;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import hcng.Evaluator;
import hcng.Operator;
import static hcng.Operator.*;

public class CountdownEvaluator implements Evaluator {
	
	@Override
	public List<Operator> getAllowableOperators() {
		return Arrays.asList(ADD, SUBTRACT, MULTIPLY, DIVIDE);
	}
	
	@Override
	public boolean isOperationAllowed(Stack<Integer> numbers, Operator operator) {
		int position = numbers.size();
		int second = numbers.get(--position);
		int first = numbers.get(--position);
		switch (operator) {
			case MULTIPLY:
				return first != 1 && second != 1;
			case SUBTRACT:
				return first - second > 0;
			case DIVIDE:
				return first % second == 0 && second != 1;
			default:
				return true;
		}
	}

	@Override
	public Stack<Integer> evaluate(Stack<Integer> numbers, Operator operator) {
		Stack<Integer> newStack = new Stack<Integer>();
		newStack.addAll(numbers);
		int second = newStack.pop();
		int first = newStack.pop();
		int result = 0;
		
		switch (operator) {
			case ADD:
				result = first + second;
				break;
			case SUBTRACT:
				result = first - second;
				break;
			case MULTIPLY:
				result = first * second;
				break;
			case DIVIDE:
				result = first / second;
				break;
		}
		
		newStack.push(result);
		return newStack;
	}
	
}
