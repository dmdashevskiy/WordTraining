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
		
		PreparedStatement insertObject = null;
		Connection connectionDatabse = null;
		
		String insertString =   "insert into TASK  SET" + 
								"QUESTION = ?," + 
								"ANSWER0 = ?," + 
								"ANSWER1 = ?," + 
								"ANSWER2 = ?," + 
								"REPEAT_NUMBER = ?," + 
								"LAST_REPEAT = ?," + 
								"NEXT_REPEAT = ?;";	
		
		try {
			Class.forName ("org.h2.Driver");
			connectionDatabse = DriverManager.getConnection (this.getClass().getResource("TañkBase.mv.db").getPath(), "sa","123654");
			insertObject = connectionDatabse.prepareStatement(insertString);
			insertObject.setString(1, this.question);
			insertObject.setString(2, this.answer0);
			insertObject.setString(3, this.answer1);
			insertObject.setString(4, this.answer2);
			insertObject.setInt(5, REPEAT_NUMBER);
			insertObject.setDate(5, new java.sql.Date(0));;
			insertObject.setDate(6, new java.sql.Date(this.nextRepeat.getTimeInMillis()));	
			insertObject.executeUpdate();			

		} catch (ClassNotFoundException e) {			
			e.printStackTrace();			
		} catch (SQLException e) {	       
			e.printStackTrace();			
		}
		finally {
			if (insertObject != null) {
				try {
					insertObject.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}			
			if (connectionDatabse != null) {
				try {
					connectionDatabse.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			
		}		
		
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
