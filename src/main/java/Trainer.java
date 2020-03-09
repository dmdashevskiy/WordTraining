import java.util.Scanner;
import java.util.regex.Pattern;

public abstract class Trainer {

	public static void addTackTroughConsole() {
		
		Scanner skaner = new Scanner(System.in);
		String inputString;	
		
		while (true) {			
			System.out.println("Enter a task in the following format or stop(S)");
			System.out.println("question% answer# optional answer# optional answer#");
			inputString = skaner.nextLine();						
			if (inputString.equals("S")) break;
			if (!Pattern.matches("^.+?%.+?#", inputString )) { 
				System.out.println("Wrong task format");
			}
			TaskObject taskObject = new TaskObject(inputString);
		}	
		skaner.close();		
	}	
	
	public static void addTack(TaskObject TackElement) {
		
	}
}	


