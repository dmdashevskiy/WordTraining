import java.util.Scanner;

public class WordTraining {

	public static void main(String[] args) {
		
		System.out.println("WordTraining program");
		System.out.println("Waiting for commands. For help type 'help'.");
		
		Scanner skaner = new Scanner(System.in);
		String inputString;
		
		while (true) {			
			inputString = skaner.nextLine();						
			if(inputString.equals("halt")) break;
			if(inputString.equals("help")) {
				showHelp(); 										//shows command help
				continue;
			}
//			if(inputString.equals("training")) {					//starts training process		
//				Trainer repeatingTrainer = new RepitingTrainer;
//				repeatingTrainer.train();
//				continue;
//			};
//			if(inputString.equals("addwords")) {					//starts words adding process
//				Trainer repeatingTrainer = new RepitingTrainer;
//				repeatingTrainer.addTack();
//				continue;
//			};
			System.out.println("Sorry. Don't know this command. Try 'help' for... help");
		}
		skaner.close();
	}
	
	private static void showHelp() {
		System.out.println("halt - exit the program");
		System.out.println("help - shows help");
		System.out.println("training - start repeating training");
		System.out.println("addwords - add words for repeating training");
	}	
}
