import java.io.Serializable;

public class Question implements Serializable
{
	private String question;
	private String questionType;
	Question(String question, boolean isGradeable)
	{
		this.question = question;
	}
	
	public void askQuestion()
	{
		
	}
	
	public void createQuestion()
	{
		
	}
}
