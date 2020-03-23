package taskObj;

public class TaskObjectOpenQuestion extends TaskObject {

		public TaskObjectOpenQuestion(String inputString) {
			super(inputString);
			this.taskType = TaskType.OPEN_QUESTION;
		}

		public TaskObjectOpenQuestion() {
			super();
			this.taskType = TaskType.OPEN_QUESTION;
		}

		@Override
		public void askQuestionTroughConsole() {
			System.out.println(question);			
		}

		@Override
		public boolean answerIsCorrect(String inputString) {
			inputString = inputString.trim();
			if (!inputString.isEmpty() 
					&& (inputString.equals(answer0)
						|| inputString.equals(answer1)
						|| inputString.equals(answer2))) {
				return true;
			} else {
				return false;
			}	
		}

		@Override
		public void showAnswers() {
			System.out.println("Correct answers is: " + answer0 + " " + answer1 + " " + answer2);					
		}
}
