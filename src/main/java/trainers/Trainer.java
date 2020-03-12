package trainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import taskObj.TaskObject;

public abstract class Trainer {

	protected ArrayList<TaskObject> tasksForTraining;

	public abstract void trainMe();
	
	public abstract String getQueryText();
	
	protected void getTasksForTrainng(ArrayList<TaskObject> tasksForTraining) {
		
				
		Calendar today = Calendar.getInstance();		
		ResultSet resSet = null; 
		PreparedStatement selectObject = null;
		Connection connectionDatabse = null;		
		
		today.set(Calendar.HOUR_OF_DAY, 23);
		today.set(Calendar.MINUTE, 59);
		today.set(Calendar.SECOND, 59);
		today.set(Calendar.MILLISECOND, 0);
		
		tasksForTraining.clear();
		
		String selectionString = 	getQueryText();  
		
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
			
}	


