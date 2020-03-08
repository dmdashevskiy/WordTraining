import java.util.Arrays;
import java.util.Calendar;
import java.sql.*;

public class TaskObject {
	
	String stringRepresentation;
	String question;
	String answer0;
	String answer1;
	String answer2;
	final Integer REPEAT_NUMBER = 5;
	Calendar nextRepeat;
	
	
	public TaskObject(String stringRepresentation) {
		
		this.stringRepresentation = stringRepresentation;
		
		String[] stringArray = parseStringRepresentation(stringRepresentation);		
		this.question = stringArray[0];
		this.answer0 = stringArray[1];
		this.answer1 = stringArray[2];
		this.answer2 = stringArray[3];
		this.nextRepeat = Calendar.getInstance();
		this.nextRepeat.add(Calendar.DATE, 1);	
		
		saveObjectInDatabase();

	}

	private void saveObjectInDatabase() {
		
		
		Class.forName ("org.h2.Driver");
		Connection connectionDatabse = DriverManager.getConnection ("jdbc:h2:D:\\Eclipse workspace\\WordTraining\\src\\main\\resources/TackBase", "sa","123654");
		connectionDatabse.createStatement();
		
		
	}

	public static String[] parseStringRepresentation(String stringRepresentation) {
				
		String[] returnArray = new String[4];
		Arrays.fill(returnArray, "");
		String[] tokensArray = stringRepresentation.split("[%,#]");		
		System.arraycopy(tokensArray, 0, returnArray, 0, Math.min(tokensArray.length, 4));		
		
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = returnArray[i].trim();			
		}
		
		return returnArray;
	}
	
	
	
	
	


}
