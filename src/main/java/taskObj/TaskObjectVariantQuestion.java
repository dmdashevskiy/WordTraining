package taskObj;

import java.util.ArrayList;
import java.util.Collections;

public class TaskObjectVariantQuestion extends TaskObject {
	
	int correctVariant;
	
	public TaskObjectVariantQuestion(String inputString) {
		
		super(inputString, TaskObject.TaskType.VARIANT_QUESTION);		
		saveObjectInDatabaseWithNewID();
	}

	public TaskObjectVariantQuestion() {
		super(TaskObject.TaskType.VARIANT_QUESTION);		
	}

	@Override
	public void askQuestionTroughConsole() {
		
		System.out.println(super.getQuestion());
		System.out.println("Choose the correct answer");
		
		ArrayList<String> answersList = new ArrayList<String>();
		if (!super.getAnswer0().isEmpty()) {
			answersList.add(super.getAnswer0());
		}
		if (!super.getAnswer1().isEmpty()) {
			answersList.add(super.getAnswer1());
		}
		if (!super.getAnswer2().isEmpty()) {
			answersList.add(super.getAnswer2());
		}		
		Collections.shuffle(answersList);
		
		for (int i = 0; i < answersList.size(); i++) {
			
			String answerFromArr = answersList.get(i);
			
			if (answerFromArr == super.getAnswer0()) {
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
		System.out.println("Correct answers is: " + Integer.toString(correctVariant) + ") " + super.getAnswer0());				
	}
	
}
