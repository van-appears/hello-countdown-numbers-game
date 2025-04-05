package hcng;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class RunnerTest {

	private ByteArrayOutputStream outContent;
	
	@Before
	public void init() {
		outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
	}
	
	@Test
	public void integrationTestSuccess() {
		Runner.main(new String[]{"10", "2", "5"});
		
		String expected = "10 2 /\n";
		String actual = outContent.toString();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void integrationTestFailure() {
		Runner.main(new String[]{"10", "2", "99"});
		
		String expected = "Nope\n";
		String actual = outContent.toString();
		
		assertEquals(expected, actual);
	}
	
}
