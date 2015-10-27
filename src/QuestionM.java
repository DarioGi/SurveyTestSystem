import java.io.Serializable;

public class QuestionM extends Question implements Serializable
{
	QuestionM(String question, boolean isGradeable) 
	{
		super(question, isGradeable);
	}
}
