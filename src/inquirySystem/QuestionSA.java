package inquirySystem;
import java.io.Serializable;

@SuppressWarnings("serial")
public class QuestionSA extends Question implements Serializable
{
	private final static int MAX_EA_ANSWERS = 100; // Arbitrary limit
	protected static final String type = "Short Answer";
	private int numOfAnswers = 1;
	
	QuestionSA(String question, boolean isGradeable) 
	{
		super(question, isGradeable);
		super.questionType = type;
	}

	@Override
	public String getQuestion() {
		String outStr = super.question + "\n" 
				+ type + "\n"
				+ "Number of answers: " + String.valueOf(numOfAnswers) + "\n";
		if ( isGradeable )
		{
			for ( int i = 0; i < super.questionAnswer.getNumResults(); i++ )
			{
				outStr += String.format("%d): %s\n", i, super.questionAnswer.getResult().get(0).get(i));
			}
		}
			
		return outStr;
	}

	@Override
	public void createQuestion() 
	{
		super.enterPrompt();
		numOfAnswers = super.askForNumber("Enter number of answers:", 1, MAX_EA_ANSWERS);
		if ( isGradeable )
		{
			for ( int i = 0; i < numOfAnswers; i++ )
			{
				String str = askForString(String.format("%d of %d) Answer:", i+1, numOfAnswers));
				super.questionAnswer.addResult(str, "");
			}
		}
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
