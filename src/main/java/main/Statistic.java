package main;

import java.util.ArrayList;
import taskObj.TaskObject;

class Statistic {

	private ArrayList<TaskObject> tasksList;
	
	public Statistic() {	
		tasksList = new ArrayList<TaskObject>();
	}
	
	public ArrayList<TaskObject> getTasksList(){		
		
		TaskObject.getTasks(tasksList, null);		
		return tasksList;
		
	}
}
