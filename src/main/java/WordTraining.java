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
				showHelp(); 										//shows command help
				continue;
			}
			if(inputString.equals("training")) {					//starts training process		
				Trainer dailyTrainer = new DailyTrainer();
				dailyTrainer.trainMe();
				showGreatings();
				continue;
			};
			if(inputString.equals("fast training")) {				//starts training with maximum 10 tasks		
				Trainer dailyTrainer = new DailyTrainer();
				dailyTrainer.trainMe();
				showGreatings();
				continue;
			};
			if(inputString.equals("addtasks")) {					 //starts words adding process
				TaskObject.addTasksTroughConsole();
				showGreatings();
				continue;
			};
			System.out.println("Sorry. Don't know this command. Try 'help' for... help");			
		}
		skaner.close();
	}

	private static void showGreatings() {
		System.out.println("WordTraining program v0.2");
		System.out.println("Waiting for commands. For help type 'help'.");
	}
	
	private static void showHelp() {		
		System.out.println("");
		System.out.println("quit - exit the program");
		System.out.println("help - shows help");
		System.out.println("training - start repeating training");
		System.out.println("fast training - start repeating training with only 10 tasks max");
		System.out.println("addtasks - add words for repeating training");
	}	
}
