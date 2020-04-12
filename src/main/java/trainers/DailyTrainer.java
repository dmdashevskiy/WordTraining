package trainers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Scanner;

import taskObj.TaskObject;

public class DailyTrainer extends Trainer {
	
	private ArrayList<TaskObject> tasksForTraining;
	
	public DailyTrainer() {
		super();
		tasksForTraining = new ArrayList<TaskObject>();	
		Calendar today = Calendar.getInstance();
		today.set(Calendar.HOUR_OF_DAY, 23);
		today.set(Calendar.MINUTE, 59);
		today.set(Calendar.SECOND, 59);
		TaskObject.getTasks(tasksForTraining, today);
	}
	
	
	@Override
	public void trainMe() {	
		
		@SuppressWarnings("resource")
		Scanner skaner = new Scanner(System.in);
		String inputString;		
		
		
		while (tasksForTraining.isEmpty() == false) {
			
			Calendar today = Calendar.getInstance();
			today.set(Calendar.HOUR_OF_DAY, 23);
			today.set(Calendar.MINUTE, 59);
			today.set(Calendar.SECOND, 59);
			Collections.shuffle(tasksForTraining);
			
			for (TaskObject taskObject : tasksForTraining) {			
				System.out.println("Answer the question. (D)elete question, or (C)hange it. Or (S)top.");			
				taskObject.askQuestionTroughConsole();
				inputString = skaner.nextLine();
				if (inputString.contentEquals("S")) {					
					return;				
				}
				if (inputString.contentEquals("D")) {
					taskObject.deleteObjectFromDatabase(false);
					continue;
				}
				if (inputString.contentEquals("C")) {
					taskObject.changeQuestion();
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
			TaskObject.getTasks(tasksForTraining, today);
		}				
	}
}
