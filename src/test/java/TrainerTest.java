import static org.junit.Assert.*;

import org.junit.Test;

public class TrainerTest {

	@Test
	public void testTackFormatValid() {
		Trainer trainer = new Trainer();
		
		assertFalse((trainer.tackFormatValid("")));
		assertFalse((trainer.tackFormatValid("%")));
		assertFalse((trainer.tackFormatValid("%%")));
		assertFalse((trainer.tackFormatValid(" % %")));
		assertFalse((trainer.tackFormatValid("1% %")));
		assertFalse((trainer.tackFormatValid("% 1%")));
				
	}

}
