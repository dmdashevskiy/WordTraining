package taskObj;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.sql.*;
import java.text.SimpleDateFormat;

public class TaskObject {
	
	public static final String TASK_FORMAT_REGEX = "^.+?\\?.+?\\!";
	final Integer MAX_REPEATS = 5;	
	Integer ID;
	Integer numberOfRepeats = 0;	
	String stringRepresentation;
	String question;
	String answer0;
	String answer1;
	String answer2;	
	Calendar nextRepeat;
	
	public void setID(Integer iD) {
		this.ID = iD;
	}
	
	
	public void setNumberOfRepeats(Integer numberOfRepeats) {
		this.numberOfRepeats = numberOfRepeats;
	}


	public void setQuestion(String question) {
		this.question = question;
	}

	
	public void setAnswer0(String answer0) {
		this.answer0 = answer0;
	}


	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}


	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}


	public void setNextRepeat(java.sql.Date date) {
		this.nextRepeat = Calendar.getInstance();
		this.nextRepeat.setTime(date);
	}


	public TaskObject(){	
	}
	
	
	public TaskObject(String stringRepresentation) { //creates taskObject from parsed stringRepresentation and saves it into the database
		
		this.stringRepresentation = stringRepresentation;
		
		String[] stringArray = parseStringRepresentation(stringRepresentation);		
		this.question = stringArray[0];
		this.answer0 = stringArray[1];
		this.answer1 = stringArray[2];
		this.answer2 = stringArray[3];
		this.nextRepeat = GregorianCalendar.getInstance();
		this.nextRepeat.set(Calendar.HOUR_OF_DAY, 0);
		this.nextRepeat.set(Calendar.MINUTE, 0);
		this.nextRepeat.set(Calendar.SECOND, 0);	
		this.nextRepeat.set(Calendar.MILLISECOND, 0);
		
		saveObjectInDatabaseWithNewID();

	}

	private void saveObjectInDatabaseWithNewID() {
		
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
			insertObject.setInt(5, numberOfRepeats);
			insertObject.setDate(6, new java.sql.Date(0));;
			insertObject.setDate(7, new java.sql.Date(this.nextRepeat.getTimeInMillis()));	
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
					e.printStackTrace();
				}				
			}
			
		}		
		
	}
	
	private void updateObjectInDatabase() {
		
		PreparedStatement udateObject = null;
		Connection connectionDatabse = null;
		
		String updateString =   "update TASK  SET " + 
									"QUESTION = ?, " + 
									"ANSWER0 = ?, " + 
									"ANSWER1 = ?, " + 
									"ANSWER2 = ?, " + 
									"REPEAT_NUMBER = ?, " + 
									"LAST_REPEAT = ?, " + 
									"NEXT_REPEAT = ? " +
								"where ID = ?;";
								
		
		try {
			Class.forName ("org.h2.Driver");
			connectionDatabse = DriverManager.getConnection ("jdbc:h2:~/TaskBase", "sa","123654");
			udateObject = connectionDatabse.prepareStatement(updateString);
			udateObject.setString(1, this.question);
			udateObject.setString(2, this.answer0);
			udateObject.setString(3, this.answer1);
			udateObject.setString(4, this.answer2);
			udateObject.setInt(5, numberOfRepeats);
			udateObject.setDate(6, new java.sql.Date(0));;
			udateObject.setDate(7, new java.sql.Date(this.nextRepeat.getTimeInMillis()));
			udateObject.setInt(8, ID);
			udateObject.executeUpdate();			

		} catch (ClassNotFoundException e) {			
			e.printStackTrace();			
		} catch (SQLException e) {	       
			e.printStackTrace();			
		}
		finally {
			if (udateObject != null) {
				try {
					udateObject.close();
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

	public void deleteObjectFromDatabase(boolean silentInConsole) {
		
		PreparedStatement deleteObject = null;
		Connection connectionDatabse = null;
		
		String deleteString =   "DELETE FROM TASK WHERE ID = ?;"; 								
		
		try {
			Class.forName ("org.h2.Driver");
			connectionDatabse = DriverManager.getConnection ("jdbc:h2:~/TaskBase", "sa","123654");
			deleteObject = connectionDatabse.prepareStatement(deleteString);
			deleteObject.setInt(1, ID);
			deleteObject.executeUpdate();
			System.out.println("Task deleted.");

		} catch (ClassNotFoundException e) {			
			e.printStackTrace();			
		} catch (SQLException e) {			
			e.printStackTrace();			
		}
		finally {
			if (deleteObject != null) {
				try {
					deleteObject.close();
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
	
	public static void addTasksTroughConsole(boolean needsTextInstruction) {
		
		@SuppressWarnings("resource")
		Scanner skaner = new Scanner(System.in);
		String inputString;
		
		if (needsTextInstruction) {
			System.out.println("Enter a task in the following format or stop(S)");
			System.out.println("question? answer! optional answer! optional answer!");
		}	
		
		while (true) {			
			inputString = skaner.nextLine();						
			if (inputString.equals("S")) break;
			if (!Pattern.matches(TASK_FORMAT_REGEX, inputString )) { 
				System.out.println("Wrong task format");
				continue;
			}
			new TaskObject(inputString);
			System.out.println("Done!");
		}			
	}

	public static String[] parseStringRepresentation(String stringRepresentation) {
				
		String[] returnArray = new String[4];
		Arrays.fill(returnArray, "");
		String[] tokensArray = stringRepresentation.split("[\\?\\!]");		
		System.arraycopy(tokensArray, 0, returnArray, 0, Math.min(tokensArray.length, 4));		
		
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = returnArray[i].trim();			
		}
		
		return returnArray;
	}


	public void askQuestionTroughConsole() {

		System.out.println(question);
		
	}


	public boolean answerIsCorrect(String inputString) {
		inputString = inputString.trim();
		if (!inputString.isEmpty() 
				&& (inputString.equals(answer0)
					|| inputString.equals(answer1)
					|| inputString.equals(answer2))) {
			return true;
		} else {
			return false;
		}		
	}


	public void showCurrentTaskCondition() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy");
		System.out.println(String.valueOf(numberOfRepeats) 
							+ " from " + String.valueOf(MAX_REPEATS) 
							+ " next repeat on " + dateFormat.format(nextRepeat.getTime()));		
	}


	public void sceduleNextTrainingForTheTask(boolean notFromBegining) {
		
		if (notFromBegining) {
			numberOfRepeats++;
		}
		else {
			numberOfRepeats = 0;
		}
				
		if (numberOfRepeats > MAX_REPEATS) {
			deleteObjectFromDatabase(false);
			return;
		}
		
		nextRepeat.add(Calendar.DAY_OF_MONTH, numberOfRepeats * numberOfRepeats);		
		updateObjectInDatabase();
		
	}


	public void showAnswers() {
		System.out.println("Correct answers is: " + answer0 + " " + answer1 + " " + answer2);		
	}


	public void changeQuestion() {

		System.out.println("The current instance of question is presented below, thus you can copy and redact it. Or (S)top redacting.");
		System.out.println(question + "? " + answer0  + "!"
										   + (answer1.isEmpty() ? "" : " " + answer1 + "!")
										   + (answer2.isEmpty() ? "" : " " + answer2 + "!"));			
		
		@SuppressWarnings("resource")
		Scanner skaner = new Scanner(System.in);
		String inputString;
		
		while (true) {			
			inputString = skaner.nextLine();						
			if (inputString.equals("S")) break;
			if (!Pattern.matches(TASK_FORMAT_REGEX, inputString )) { 
				System.out.println("Wrong task format");
				continue;
			}
			new TaskObject(inputString);
			this.deleteObjectFromDatabase(true);
			System.out.println("Done!");
			break;
		}	
		
	}
	
}
