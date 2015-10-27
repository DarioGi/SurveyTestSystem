import java.io.Serializable;

public class QuestionEA extends Question implements Serializable
{
	QuestionEA(String question, boolean isGradeable) 
	{
		super(question, isGradeable);
	}
}
