package trainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import taskObj.TaskObject;

public class DailyTrainer extends Trainer {
	
	ArrayList<TaskObject> tasksForTraining;
	
	public DailyTrainer() {
		super();
		fillTasksForTrainng();
	}
	
	
	private void fillTasksForTrainng() {
		
		PreparedStatement selectObject = null;
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


	@Override
	public void trainMe() {		
	}

}
