import java.util.Scanner;

import taskObj.TaskObject;
import trainers.DailyTrainer;
import trainers.Trainer;

public class WordTraining {

	public static void main(String[] args) {
		
		showGreatings();
		
		Scanner skaner = new Scanner(System.in);
		String inputString;
		
		while (true) {			
			inputString = skaner.nextLine();						
			if(inputString.equals("halt")) break;
			if(inputString.equals("help")) {
				showHelp(); 										//shows command help
				continue;
			}
			if(inputString.equals("training")) {					//starts training process		
				Trainer dailyTrainer = new DailyTrainer();
				dailyTrainer.trainMe();
				continue;
			};
			if(inputString.equals("addtasks")) {					//starts words adding process
				TaskObject.addTasksTroughConsole();
				continue;
			};
			System.out.println("Sorry. Don't know this command. Try 'help' for... help");
		}
		skaner.close();
	}

	private static void showGreatings() {
		System.out.println("WordTraining program");
		System.out.println("Waiting for commands. For help type 'help'.");
	}
	
	private static void showHelp() {		
		System.out.println("halt - exit the program");
		System.out.println("help - shows help");
		System.out.println("training - start repeating training");
		System.out.println("addtasks - add words for repeating training");
	}	
}
