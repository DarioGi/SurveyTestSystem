import java.io.Serializable;

public class QuestionTF extends Question implements Serializable
{
	QuestionTF(String question, boolean isGradeable) 
	{
		super(question, isGradeable);
	}
}
