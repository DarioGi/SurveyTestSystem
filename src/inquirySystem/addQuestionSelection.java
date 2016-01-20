package inquirySystem;

public class addQuestionSelection extends Selection
{
	private Question q;
	protected addQuestionSelection(String selectionName, Question q) 
	{
		super(selectionName);
	}
	
	@Override
	public Object select(Object o)
	{
		q.askQuestion();
		return null;
	}
}
