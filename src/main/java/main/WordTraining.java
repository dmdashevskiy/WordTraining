package main;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Scanner;

import taskObj.TaskObject;
import trainers.DailyTrainer;
import trainers.Trainer;

public class WordTraining {

	public static void main(String[] args) {
		
		Scanner skaner = new Scanner(System.in);
		String inputString;		
		showGreatings();
		
		while (true) {						
			inputString = skaner.nextLine();						
			if(inputString.equals("quit")) break;
			if(inputString.equals("help")) {
				showHelp(); 										
				continue;
			}
			if(inputString.equals("showtasks")) {
				showAllQestions();										
				continue;
			}
			if(inputString.equals("training")) {					//starts training process		
				Trainer dailyTrainer = new DailyTrainer();
				dailyTrainer.trainMe();
				showGreatings();
				continue;
			};
			if(inputString.equals("addoq")) {					 	//starts words adding process
				TaskObject.addTasksTroughConsole(TaskObject.TaskType.OPEN_QUESTION, false);
				showGreatings();
				continue;
			};
			if(inputString.equals("addvq")) {					 	//starts words adding process
				TaskObject.addTasksTroughConsole(TaskObject.TaskType.VARIANT_QUESTION, false);
				showGreatings();
				continue;
			};
			System.out.println("Sorry. Don't know this command. Try 'help' for... getting help");			
		}
		skaner.close();
	}

	private static void showGreatings() {
		System.out.println("WordTraining program");
		System.out.println("Waiting for commands. For help type 'help'.");
	}
	
	private static void showHelp() {		
		System.out.println("quit - exit the program");
		System.out.println("help - shows help");
		System.out.println("showtasks - shows all the active tasks");
		System.out.println("training - start repeating training");
		System.out.println("addoq - add open question tasks");
		System.out.println("addvq - add variant question tasks");		
	}	

	private static void showAllQestions() {
		
		ArrayList<TaskObject> sortedTasksList = new Statistic().getTasksList();
		sortedTasksList.sort(Comparator.comparing(TaskObject::getNextRepeat));
		sortedTasksList.forEach(System.out::println);
		
	}

}
