package trainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

import taskObj.TaskObject;

public class DailyTrainer extends Trainer {
	
	ArrayList<TaskObject> tasksForTraining;
	
	public DailyTrainer() {
		super();
		tasksForTraining = new ArrayList<TaskObject>();		
		getTasksForTrainng(tasksForTraining);
	}
	
	
	private void getTasksForTrainng(ArrayList<TaskObject> tasksForTraining) {
		
				
		Calendar today = Calendar.getInstance();		
		ResultSet resSet = null; 
		PreparedStatement selectObject = null;
		Connection connectionDatabse = null;		
		
		today.set(Calendar.HOUR_OF_DAY, 23);
		today.set(Calendar.MINUTE, 59);
		today.set(Calendar.SECOND, 59);
		today.set(Calendar.MILLISECOND, 0);
		
		tasksForTraining.clear();
		
		String selectionString = 	"select * " + 
									"from  TASK " + 
									"where NEXT_REPEAT <= ?;";  
		
		try {
			Class.forName ("org.h2.Driver");
			connectionDatabse = DriverManager.getConnection ("jdbc:h2:~/TaskBase", "sa","123654");
			selectObject = connectionDatabse.prepareStatement(selectionString);
			selectObject.setDate(1, new java.sql.Date(today.getTimeInMillis()));	
			resSet = selectObject.executeQuery();
			
			while (resSet.next()) {
				TaskObject readTask = new TaskObject();
				readTask.setID(resSet.getInt("ID"));
				readTask.setQuestion(resSet.getString("QUESTION"));
				readTask.setAnswer0(resSet.getString("ANSWER0"));
				readTask.setAnswer1(resSet.getString("ANSWER1"));
				readTask.setAnswer2(resSet.getString("ANSWER2"));
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


	@Override
	public void trainMe() {	
		
		@SuppressWarnings("resource")
		Scanner skaner = new Scanner(System.in);
		String inputString;		
		
		
		while (tasksForTraining.isEmpty() == false) {
		
			
			for (TaskObject taskObject : tasksForTraining) {			
				System.out.println("Answer the question. Or (D)elete question. Or (S)top this hell.");			
				taskObject.askQuestionTroughConsole();
				inputString = skaner.nextLine();
				if (inputString.contentEquals("S")) {					
					return;				
				}
				if (inputString.contentEquals("D")) {
					taskObject.deleteObjectFromDatabase();
					continue;
				}
				if (inputString.isEmpty()) {
					System.out.println("It is so empty :(");
					continue;
				}
				
				if (taskObject.answerIsCorrect(inputString)) {
					System.out.println("Correct!");
					taskObject.sceduleNextTrainingForTheTask(true);
					taskObject.showCurrentTaskCondition();
					
				} else {
					System.out.println("Ow my. How can one be so wrong?");
					taskObject.showAnswers();
					taskObject.sceduleNextTrainingForTheTask(false);				
				}			
			}			
			getTasksForTrainng(tasksForTraining);
		}				
	}
}
