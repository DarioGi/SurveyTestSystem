import java.io.Serializable;

public class QuestionMC extends Question implements Serializable
{
	QuestionMC(String question, boolean isGradeable) 
	{
		super(question, isGradeable);
	}
}
