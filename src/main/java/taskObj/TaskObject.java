package taskObj;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Pattern;
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
		
		String insertString =   "insert into TASK  SET " + 
								"QUESTION = ?, " + 
								"ANSWER0 = ?, " + 
								"ANSWER1 = ?, " + 
								"ANSWER2 = ?, " + 
								"REPEAT_NUMBER = ?, " + 
								"LAST_REPEAT = ?, " + 
								"NEXT_REPEAT = ?;";	
		
		try {
			Class.forName ("org.h2.Driver");
			connectionDatabse = DriverManager.getConnection ("jdbc:h2:~/TaskBase", "sa","123654");
			insertObject = connectionDatabse.prepareStatement(insertString);
			insertObject.setString(1, this.question);
			insertObject.setString(2, this.answer0);
			insertObject.setString(3, this.answer1);
			insertObject.setString(4, this.answer2);
			insertObject.setInt(5, REPEAT_NUMBER);
			insertObject.setDate(6, new java.sql.Date(0));;
			insertObject.setDate(7, new java.sql.Date(this.nextRepeat.getTimeInMillis()));	
			insertObject.executeUpdate();	
			
			System.out.println("Task was added");

		} catch (ClassNotFoundException e) {			
			System.out.println("Task wasn't added");
			e.printStackTrace();			
		} catch (SQLException e) {	       
			System.out.println("Task wasn't added");
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
					e.printStackTrace();
				}				
			}
			
		}		
		
	}

	public static void addTasksTroughConsole() {
		
		Scanner skaner = new Scanner(System.in);
		String inputString;	
		
		while (true) {			
			System.out.println("Enter a task in the following format or stop(S)");
			System.out.println("question% answer# optional answer# optional answer#");
			inputString = skaner.nextLine();						
			if (inputString.equals("S")) break;
			if (!Pattern.matches("^.+?%.+?#", inputString )) { 
				System.out.println("Wrong task format");
			}
			new TaskObject(inputString);
		}	
		skaner.close();		
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
