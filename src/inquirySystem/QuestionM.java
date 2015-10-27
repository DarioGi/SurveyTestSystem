package inquirySystem;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class QuestionM extends Question implements Serializable
{
	private final static int MAX_M_ANSWERS = 26; // Alphabet limit, arbitrary as well
	private final static int MIN_M_ANSWERS = 2; // Doesn't make sense to have 1.
	protected static final String type = "Matching";
	private int numOfAnswers = 2;
	
	QuestionM(String question, boolean isGradeable) 
	{
		super(question, isGradeable);
		super.questionType = type;
	}

	@Override
	public String getQuestion() 
	{
		String outStr = super.question + "\n" 
				+ type + "\n";
		char alphabet = 'A';
		ArrayList<ArrayList<String>> res = super.questionAnswer.getResult();
		for ( int i = 0; i < super.questionAnswer.getNumResults(); i++ )
		{
			outStr += String.format("%s) %-25s  %d) %-25s\n", alphabet++, res.get(0).get(i), i+1, res.get(1).get(i));
		}
		return outStr;
	}

	@Override
	public void createQuestion() 
	{
		super.enterPrompt();
		numOfAnswers = super.askForNumber("Enter number of matches:", MIN_M_ANSWERS, MAX_M_ANSWERS);
		for ( int i = 0; i < numOfAnswers; i++ )
		{
			String str1 = askForString(String.format("%d of %d) Enter first pair choice:", i+1, numOfAnswers));
			String str2 = askForString(String.format("%d of %d) Enter second pair choice:", i+1, numOfAnswers));
			super.questionAnswer.addResult(str1, str2);
		}
	}

	@Override
	public void modifyQuestion() 
	{
		
	}

	@Override
	public void askQuestion() 
	{
		// TODO Auto-generated method stub
		
	}
}
