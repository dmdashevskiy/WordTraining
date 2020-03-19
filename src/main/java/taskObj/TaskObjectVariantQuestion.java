package taskObj;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TaskObjectVariantQuestion extends TaskObject {
	
	public TaskObjectVariantQuestion(String stringRepresentation) { //creates taskObject from parsed stringRepresentation and saves it into the database
		
		//TODO rebuild
		String[] stringArray = parseStringRepresentation(stringRepresentation);		
		this.question = stringArray[0];
		this.answer0 = stringArray[1];
		this.answer1 = stringArray[2];
		this.answer2 = stringArray[3];
		this.nextRepeat = GregorianCalendar.getInstance();
		this.nextRepeat.set(Calendar.HOUR_OF_DAY, 0);
		this.nextRepeat.set(Calendar.MINUTE, 0);
		this.nextRepeat.set(Calendar.SECOND, 0);	
		this.nextRepeat.set(Calendar.MILLISECOND, 0);
		
		saveObjectInDatabaseWithNewID();

	}
	
	public static void addTasksTroughConsole(boolean needsTextInstruction) {
		
		@SuppressWarnings("resource")
		Scanner skaner = new Scanner(System.in);
		String inputString;
		
		if (needsTextInstruction) {
			System.out.println("Enter a task in the following format or (C)hange task type stop(S)");
			System.out.println(TASK_FORMAT_TEXT_REPRESENTATION);
		}	
		
		while (true) {			
			inputString = skaner.nextLine();						
			if (inputString.equals("S")) break;
			if (!Pattern.matches(TASK_FORMAT_REGEX, inputString )) { 
				System.out.println("Wrong task format");
				continue;
			}
			new TaskObjectVariantQuestion(inputString);
			System.out.println("Done!");
		}			
	}

	public void changeQuestion() {
	
		//TODO rebuild
		
		System.out.println("The current instance of question is presented below, thus you can copy and redact it. Or (S)top redacting.");
		System.out.println(question + "? " + answer0  + "!"
										   + (answer1.isEmpty() ? "" : " " + answer1 + "!")
										   + (answer2.isEmpty() ? "" : " " + answer2 + "!"));			
		
		@SuppressWarnings("resource")
		Scanner skaner = new Scanner(System.in);
		String inputString;
		
		while (true) {			
			inputString = skaner.nextLine();						
			if (inputString.equals("S")) break;
			if (!Pattern.matches(TASK_FORMAT_REGEX, inputString )) { 
				System.out.println("Wrong task format");
				continue;
			}
			new TaskObjectVariantQuestion(inputString);
			this.deleteObjectFromDatabase(true);
			System.out.println("Done!");
			break;
		}	
		
	}
	
}
