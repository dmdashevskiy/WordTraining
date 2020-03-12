package trainers;

public class LiteDailyTrainer extends DailyTrainer {

	public LiteDailyTrainer() {
		super();
	}

	@Override
	public String getQueryText() {
		return "SELECT TOP 10 * " + 
				"FROM  TASK " + 
				"WHERE NEXT_REPEAT <= ?;";		
	}	

}
