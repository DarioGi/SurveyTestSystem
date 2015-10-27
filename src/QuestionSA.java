import java.io.Serializable;

public class QuestionSA extends Question implements Serializable
{
	QuestionSA(String question, boolean isGradeable) 
	{
		super(question, isGradeable);
	}
}
