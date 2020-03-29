package taskObj;

public class TaskObjectOpenQuestion extends TaskObject {
		
		public TaskObjectOpenQuestion(String inputString) {
			
			super(inputString, TaskObject.TaskType.OPEN_QUESTION);			
			saveObjectInDatabaseWithNewID();
		}

		public TaskObjectOpenQuestion() {
			super(TaskObject.TaskType.OPEN_QUESTION);			
		}

		@Override
		public void askQuestionTroughConsole() {
			System.out.println(super.getQuestion());			
		}

		@Override
		public boolean answerIsCorrect(String inputString) {
			inputString = inputString.trim();
			if (!inputString.isEmpty() 
					&& (inputString.equals(super.getAnswer0())
						|| inputString.equals(super.getAnswer1())
						|| inputString.equals(super.getAnswer2()))) {
				return true;
			} else {
				return false;
			}	
		}

		@Override
		public void showAnswers() {
			System.out.println("Correct answers is: " + super.getAnswer0() + " " + super.getAnswer1() + " " + super.getAnswer2());					
		}
}
