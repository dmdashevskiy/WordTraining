import java.util.Scanner;

public class WordTraining {

	public static void main(String[] args) {
		
		System.out.println("WordTraining program");
		System.out.println("Waiting for commands. For help type 'help'.");
		
		Scanner skaner = new Scanner(System.in);
		String inputString;
		
		while (true) {			
			inputString = skaner.nextLine();						
			if(inputString.equals("Q")) break;
//			if(inputString.equals("help")) showHelp(); 				//shows command help
//			if(inputString.equals("training")) {					//starts training process		
//				Trainer repeatingTrainer = new RepitingTrainer;
//				repeatingTrainer.train();
//			};
//			if(inputString.equals("addwords")) {					//starts words adding process
//				Trainer repeatingTrainer = new RepitingTrainer;
//				repeatingTrainer.addWords();
//			};
		}
		skaner.close();
	}
}
