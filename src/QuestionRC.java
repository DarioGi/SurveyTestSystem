import java.io.Serializable;

public class QuestionRC extends Question implements Serializable
{
	QuestionRC(String question, boolean isGradeable) 
	{
		super(question, isGradeable);
	}
}
