import java.util.Scanner;

import taskObj.TaskObject;
import taskObj.TaskObjectOpenQuestion;
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
			if(inputString.equals("addtasks")) {					 //starts words adding process
				TaskObjectOpenQuestion.addTasksTroughConsole(true);
				showGreatings();
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
		System.out.println("quit - exit the program");
		System.out.println("help - shows help");
		System.out.println("training - start repeating training");
		System.out.println("addtasks - add words for repeating training");
	}	
}
