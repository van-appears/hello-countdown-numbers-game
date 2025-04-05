package hcng.test;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Setup {

	public static Stack<Integer> createStack(Integer... values) {
		Stack<Integer> stack = new Stack<>();
		List<Integer> list = Arrays.asList(values); 
		stack.addAll(list);
		return stack;
	}
	
}
