package hcng;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public class Pattern {
	
	private final List<String> pattern;
	private final Stack<Integer> numbers;
	private final int result;
	private final Evaluator evaluator;

	public Pattern(Evaluator evaluator) {
		this(evaluator, new ArrayList<>(), new Stack<>(), 0);
	}
	
	private Pattern(Evaluator evaluator, List<String> pattern, Stack<Integer> numbers, int result) {
		this.evaluator = evaluator;
		this.pattern = pattern;
		this.numbers = numbers;
		this.result = result;
	}
	
	public boolean canExtendWithOperator() {
		return numbers.size() > 1;
	}
	
	public int getResult() {
		return result;
	}
	
	public List<String> getPattern() {
		return pattern;
	}
	
	public List<String> getEvaluatedPattern() {
		List<String> operatorStrings = evaluator.getAllowableOperators()
			.stream()
			.map(o -> o.getSymbol())
			.toList();
	
		long operatorCount = pattern.stream().filter(p -> operatorStrings.contains(p)).count();
		int expectedSymbols = 1 + (2 * (int)operatorCount);
		return pattern.subList(pattern.size() - expectedSymbols, pattern.size());
	}
	
	private List<String> addSymbolToPattern(String symbol) {
		List<String> extendedPattern = new ArrayList<>(pattern);
		extendedPattern.add(symbol);
		return extendedPattern;
	}
	
	private Stack<Integer> addNumberToStack(Integer number) {
		Stack<Integer> newNumbers = new Stack<>();
		newNumbers.addAll(numbers);
		newNumbers.add(number);
		return newNumbers;
	}
	
	public Pattern extend(Integer number) {
		List<String> newPattern = addSymbolToPattern(number.toString());
		Stack<Integer> newNumbers = addNumberToStack(number);
		return new Pattern(evaluator, newPattern, newNumbers, number);
	}
	
	public Optional<Pattern> extend(Operator operator) {
		if (!evaluator.isOperationAllowed(numbers, operator)) {
			return Optional.empty();
		}
		List<String> newPattern = addSymbolToPattern(operator.getSymbol());
		Stack<Integer> newNumbers = evaluator.evaluate(numbers, operator);
		return Optional.of(new Pattern(evaluator, newPattern, newNumbers, newNumbers.peek()));
	}
	
}
