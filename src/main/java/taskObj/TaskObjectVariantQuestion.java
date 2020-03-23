package taskObj;

import java.util.ArrayList;
import java.util.Collections;

public class TaskObjectVariantQuestion extends TaskObject {
	
	int correctVariant;
	
	public TaskObjectVariantQuestion(String inputString) {
		super(inputString);
		this.taskType = TaskType.VARIANT_QUESTION;
		saveObjectInDatabaseWithNewID();
	}

	public TaskObjectVariantQuestion() {
		super();
		this.taskType = TaskType.VARIANT_QUESTION;
	}

	@Override
	public void askQuestionTroughConsole() {
		
		System.out.println(question);
		System.out.println("Choose the correct answer");
		
		ArrayList<String> answersList = new ArrayList<String>();
		answersList.add(answer0);
		answersList.add(answer1);
		answersList.add(answer2);
		Collections.shuffle(answersList);
		
		for (int i = 0; i < answersList.size(); i++) {
			
			String answerFromArr = answersList.get(i);
			
			if (answerFromArr == answer0) {
				this.correctVariant = i;
			}			
			if (!answerFromArr.isEmpty()) {
				System.out.println(Integer.toString(i) + ") " + answerFromArr);
			}
			
		}		
	}

	@Override
	public boolean answerIsCorrect(String inputString) {

		inputString = inputString.trim();
		if (!inputString.isEmpty() && inputString.equals(Integer.toString(correctVariant))) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void showAnswers() {
		System.out.println("Correct answers is: " + Integer.toString(correctVariant) + ") " + answer0);				
	}
	
}
