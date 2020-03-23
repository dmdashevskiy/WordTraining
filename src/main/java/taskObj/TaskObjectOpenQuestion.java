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


}
