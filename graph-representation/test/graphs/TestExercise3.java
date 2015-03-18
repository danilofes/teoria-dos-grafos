package graphs;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Assert;
import org.junit.Test;

public class TestExercise3 {

	@Test
	public void testAdjMatrixToAdjList() {
		String input = textFile(
			"4",
			"0 1 1 0",
			"0 0 1 1",
			"0 0 0 1",
			"0 0 0 0"
		);
		
		String expectedOutput = textFile(
			"4",
			"2 3",
			"3 4",
			"4",
			""
		);

		testWith(input, expectedOutput);
	}

	private void testWith(String input, String expected) {
		Reader reader = new StringReader(input);
		Writer writer = new StringWriter();
		
		Exercise3 ex3 = new Exercise3();
		ex3.adjMatrixToAdjList(reader, writer);
		
		Assert.assertEquals(expected, writer.toString());
	}

	private String textFile(String ... lines) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < lines.length; i++) {
			if (i > 0) {
				buffer.append('\n');
			}
			buffer.append(lines[i]);
		}
		return buffer.toString();
	}

}
