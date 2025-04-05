package hcng;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatternPermutator {

	private final Evaluator evaluator;
	private final List<Operator> operators;

	public PatternPermutator(Evaluator evaluator) {
		this.evaluator = evaluator;
		operators = evaluator.getAllowableOperators();
	}
	
	public Optional<List<String>> find(List<Integer> numbers, int target) {
		Permutator permutator = new Permutator(numbers, target);
		Optional<Pattern> found = permutator.find();
		if (found.isPresent()) {
			return Optional.of(found.get().getPattern());	
		}
		return Optional.empty();
	}
	
	private class Permutator {
		
		private final List<Integer> remaining;
		private final int target;
		
		public Permutator(List<Integer> numbers, int target) {
			this.remaining = numbers;
			this.target = target;
		}
		
		public Optional<Pattern> find() {
			return extend(new Pattern(evaluator), remaining);
		}
		
		private Optional<Pattern> extend(Pattern pattern, List<Integer> remaining) {
			Optional<Pattern> found = extendWithNumbers(pattern, remaining);
			if (!found.isPresent() && pattern.canExtendWithOperator()) {
				found = extendWithOperators(pattern, remaining);
			}
			return found;
		}
	
		private Optional<Pattern> extendWithNumbers(Pattern pattern, List<Integer> remaining) {
			for (int index=0; index<remaining.size(); index++) {
				Pattern newPattern = pattern.extend(remaining.get(index));
				if (newPattern.getResult() == target) {
					return Optional.of(newPattern);
				} else {
					List<Integer> remainingWithout = new ArrayList<>(remaining);
					remainingWithout.remove(index);
					Optional<Pattern> found = extend(newPattern, remainingWithout);
					if (found.isPresent()) {return found;}
				}
			}
			return Optional.empty();
		}
		
		private Optional<Pattern> extendWithOperators(Pattern pattern, List<Integer> remaining) {
			for (Operator op: operators) {
				Optional<Pattern> newPattern = pattern.extend(op);
				if (newPattern.isPresent()) {
					if (newPattern.get().getResult() == target) {
						return newPattern;
					}
					
					Optional<Pattern> found = extend(newPattern.get(), remaining);
					if (found.isPresent()) {
						return found;
					}
				}
			}
			return Optional.empty();
		}
	}
}
