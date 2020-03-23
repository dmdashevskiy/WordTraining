package taskObj;


public class TaskObjectVariantQuestion extends TaskObject {
	
	public TaskObjectVariantQuestion(String inputString) {
		super(inputString);
		this.taskType = TaskType.VARIANT_QUESTION;
	}

	public TaskObjectVariantQuestion() {
		super();
		this.taskType = TaskType.VARIANT_QUESTION;
	}
	
}
