package trainers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import taskObj.TaskObject;

public class DailyTrainer extends Trainer {
	
	public DailyTrainer() {
		super();
		tasksForTraining = new ArrayList<TaskObject>();		
		getTasksForTrainng(tasksForTraining);
	}

	@Override
	public String getQueryText() {
		return "select * " + 
				"from  TASK " + 
				"where NEXT_REPEAT <= ?;";
	}
	
	@Override
	public void trainMe() {	
		
		@SuppressWarnings("resource")
		Scanner skaner = new Scanner(System.in);
		String inputString;		
		
		
		while (tasksForTraining.isEmpty() == false) {
			
			Collections.shuffle(tasksForTraining);
			
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
