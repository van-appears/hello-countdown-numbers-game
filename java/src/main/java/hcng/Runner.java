package hcng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import hcng.countdown.CountdownEvaluator;

public class Runner {

	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(args)
			.stream()
			.map(Integer::parseInt)
			.collect(Collectors.toCollection(ArrayList::new));
		int target = numbers.remove(numbers.size() - 1);
		new Runner(numbers, target);
	}
		
	public Runner(List<Integer> numbers, int target) {
		Evaluator evaluator = new CountdownEvaluator();
		PatternPermutator permutator = new PatternPermutator(evaluator);
		Optional<List<String>> result = permutator.find(numbers, target);
		
		if (result.isPresent()) {
			System.out.println(String.join(" ", result.get()));
		} else {
			System.out.println("Nope");
		}
	}
}
