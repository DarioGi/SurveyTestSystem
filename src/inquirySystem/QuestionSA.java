package inquirySystem;
import java.io.Serializable;

@SuppressWarnings("serial")
public class QuestionSA extends Question implements Serializable
{
	protected static final String type = "Short Answer";
	
	QuestionSA(String question, boolean isGradeable) 
	{
		super(question, isGradeable);
		super.questionType = type;
	}

	@Override
	public String getQuestion() {
		String outStr = super.question + "\n" 
				+ type + "\n" ;
		outStr += String.format("The correct answer is: %s", super.questionAnswer.getResult().get(0).get(0));
		return outStr;
	}

	@Override
	public void createQuestion() 
	{
		super.enterPrompt();
		String res = askForString("Enter a correct answer: ");
		super.questionAnswer.addResult(res, "");
	}

	@Override
	public void modifyQuestion() {
		// TODO Auto-generated method stub
	}

	@Override
	public void askQuestion() {
		// TODO Auto-generated method stub
		
	}
}
