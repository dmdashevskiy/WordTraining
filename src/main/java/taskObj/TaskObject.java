package taskObj;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.sql.*;

public abstract class TaskObject {
	
	private static final String TASK_FORMAT_REGEX = "^.+?\\?.+?\\!";
	private static final String TASK_FORMAT_TEXT_REPRESENTATION = "open question? answer! optional answer! optional answer!";
	private static final Integer MAX_REPEATS = 5;
	private TaskType taskType;
	private Integer ID;
	private Integer numberOfRepeats = 0;	
	private String question;
	private String answer0;
	private String answer1;
	private String answer2;	
	private Calendar nextRepeat;
	
	
	private void setID(Integer iD) {
		this.ID = iD;
	}	
	
	private void setNumberOfRepeats(Integer numberOfRepeats) {
		this.numberOfRepeats = numberOfRepeats;
	}

	private void setQuestion(String question) {
		this.question = question;
	}
	
	private void setAnswer0(String answer0) {
		this.answer0 = answer0;
	}

	private void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	private void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	private void setNextRepeat(java.sql.Date date) {
		this.nextRepeat = Calendar.getInstance();
		this.nextRepeat.setTime(date);
	}
		
	public String getQuestion() {
		return question;
	}
		

	public String getAnswer0() {
		return answer0;
	}

	public String getAnswer1() {
		return answer1;
	}

	public String getAnswer2() {
		return answer2;
	}
	
	public Calendar getNextRepeat() {
		return nextRepeat;
	}

	public TaskObject(TaskType taskType){
		this.taskType = taskType;
	}
	
	public TaskObject(String stringRepresentation, TaskType taskType) { //creates taskObject from parsed stringRepresentation and saves it into the database
		
		this(taskType);				
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
		

	}

	@Override
	public String toString() {
		
		return String.format("%s? %s! %s! %s! - %td.%<tm.%<tY", question, answer0, answer1, answer2, nextRepeat);
		
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
			new TaskObjectOpenQuestion(inputString);
			this.deleteObjectFromDatabase(true);
			System.out.println("Done!");
			break;
		}	
		
	}
	
	public static void addTasksTroughConsole(TaskType taskType, boolean silentMode) {
		
		@SuppressWarnings("resource")
		Scanner skaner = new Scanner(System.in);
		String inputString;
		
		if (!silentMode) {
			System.out.println("Enter a task in the following format or (C)hange task type stop(S)");
			if (taskType == TaskType.VARIANT_QUESTION) {
				System.out.println("The first answer will be accepted as correct!");
			}
			System.out.println(TASK_FORMAT_TEXT_REPRESENTATION);
		}	
		
		while (true) {			
			inputString = skaner.nextLine();						
			if (inputString.equals("S")) break;
			if (!Pattern.matches(TASK_FORMAT_REGEX, inputString )) { 
				System.out.println("Wrong task format");
				continue;
			}
			
			if (taskType == TaskType.OPEN_QUESTION) {
				new TaskObjectOpenQuestion(inputString);
			}
			else if (taskType == TaskType.VARIANT_QUESTION) {				
				new TaskObjectVariantQuestion(inputString);
			}			
			
			if (!silentMode) {
				System.out.println("Done!");
			}
			
		}			
	}
	
	protected void saveObjectInDatabaseWithNewID() {
		
		PreparedStatement insertObject = null;
		Connection connectionDatabse = null;
		
		String insertString =   "insert into TASK  SET " + 
									"QUESTION = ?, " + 
									"ANSWER0 = ?, " + 
									"ANSWER1 = ?, " + 
									"ANSWER2 = ?, " + 
									"REPEAT_NUMBER = ?, " + 
									"LAST_REPEAT = ?, " + 
									"NEXT_REPEAT = ?," +	
									"TASK_TYPE = ?;";	
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
			insertObject.setString(8, taskType.toString());	
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
									"NEXT_REPEAT = ?, " +
									"TASK_TYPE = ? " +
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
			udateObject.setString(8, this.taskType.toString());
			udateObject.setInt(9, ID);
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

	public abstract void askQuestionTroughConsole(); 

	public abstract boolean answerIsCorrect(String inputString); 

	public void showCurrentTaskCondition() {

		System.out.printf("%d from %d and next repeat on %td.%<tm.%<tY%n", numberOfRepeats, MAX_REPEATS, nextRepeat);
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
		
		
		this.nextRepeat = GregorianCalendar.getInstance();
		this.nextRepeat.set(Calendar.HOUR_OF_DAY, 0);
		this.nextRepeat.set(Calendar.MINUTE, 0);
		this.nextRepeat.set(Calendar.SECOND, 0);	
		this.nextRepeat.set(Calendar.MILLISECOND, 0);
		nextRepeat.add(Calendar.DAY_OF_MONTH, numberOfRepeats * numberOfRepeats);		
		updateObjectInDatabase();
		
	}

	public abstract void showAnswers(); 

	public static void getTasks(ArrayList<TaskObject> tasksForTraining, Calendar untillDate) {
		
					
		ResultSet resSet = null; 
		PreparedStatement selectObject = null;
		Connection connectionDatabse = null;		
		tasksForTraining.clear();
		
		String selectionString = 	"select * " + 
									"from  TASK " + 
									(untillDate == null?  ";": "where NEXT_REPEAT <= ?;");  
		
		try {
			Class.forName ("org.h2.Driver");
			connectionDatabse = DriverManager.getConnection ("jdbc:h2:~/TaskBase", "sa","123654");
			selectObject = connectionDatabse.prepareStatement(selectionString);
			if (untillDate != null) {
				selectObject.setDate(1, new java.sql.Date(untillDate.getTimeInMillis()));
			}				
			resSet = selectObject.executeQuery();
			
			while (resSet.next()) {
				
				TaskObject readTask = null;
				
				if (resSet.getString("TASK_TYPE").equals(TaskType.OPEN_QUESTION.toString())) {
					readTask = new TaskObjectOpenQuestion();
				}
				else if (resSet.getString("TASK_TYPE").equals(TaskType.VARIANT_QUESTION.toString())) {
					readTask = new TaskObjectVariantQuestion();
				}
				
				readTask.setID(resSet.getInt("ID"));
				readTask.setQuestion(resSet.getString("QUESTION"));
				readTask.setAnswer0(resSet.getString("ANSWER0"));
				readTask.setAnswer1(resSet.getString("ANSWER1"));
				readTask.setAnswer2(resSet.getString("ANSWER2"));
				readTask.setNumberOfRepeats(resSet.getInt("REPEAT_NUMBER"));
				readTask.setNextRepeat(resSet.getDate("NEXT_REPEAT"));
				
				tasksForTraining.add(readTask);				
			}
			
	
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();			
		} catch (SQLException e) {	       
			e.printStackTrace();			
		} finally {
			if (resSet != null) {
				try {
					resSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}			
			if (selectObject != null) {
				try {
					selectObject.close();
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
	
	public static enum TaskType {
		
		OPEN_QUESTION,
		VARIANT_QUESTION	
			
	}
	
}
