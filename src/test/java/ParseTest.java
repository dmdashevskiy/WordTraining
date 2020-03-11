import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import taskObj.TaskObject;

public class ParseTest {

	@Test
	public void testParseStringRepresentation() {
		
		String[] strArray;
		
		strArray = new String[] {"asdf", "adf", "", ""};
		assertArrayEquals(strArray, TaskObject.parseStringRepresentation("asdf? adf!"));
		
		strArray = new String[] {"asdf", "adf", "adfas", ""};
		assertArrayEquals(strArray, TaskObject.parseStringRepresentation("asdf? adf! adfas!"));
		
		strArray = new String[] {"asdf", "adf", "adfas", "asdfasfd"};
		assertArrayEquals(strArray, TaskObject.parseStringRepresentation("asdf? adf! adfas! asdfasfd!"));
		
		strArray = new String[] {"asdf", "adf", "", ""};
		assertFalse(Arrays.equals(strArray, TaskObject.parseStringRepresentation("asdf, adf?")));
	}

}
