package inquirySystem;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class QuestionRC extends Question implements Serializable
{
	private final static int MAX_RC_ANSWERS = 26; // Alphabet limit, arbitrary as well
	private final static int MIN_RC_ANSWERS = 2; // Doesn't make sense to have 1.
	protected static final String type = "Ranking Choice";
	private int numOfAnswers = 2;
	
	QuestionRC(String question, boolean isGradeable) 
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
			outStr += String.format("%s) %-20s ", alphabet++, res.get(0).get(i));
			if ( isGradeable )
			{
				outStr += String.format(" Rank: %s",res.get(1).get(i));
			}
			if ( i != super.questionAnswer.getNumResults()-1 )
				outStr += "\n";
		}
		return outStr;
	}

	@Override
	public void createQuestion() 
	{
		super.enterPrompt();
		numOfAnswers = super.askForNumber("Enter number of choices:", MIN_RC_ANSWERS, MAX_RC_ANSWERS);
		for ( int i = 0; i < numOfAnswers; i++ )
		{
			String rankMsg;
			if ( isGradeable )
			{
				if ( i == 0 )
					rankMsg = "Enter a highest ranking choice:";
				else
					rankMsg = "Enter a next highest ranking choice:";	
			}
			else
			{
				rankMsg = "Enter a ranking choice:";
			}
			String str = askForString(String.format("%d of %d) %s", i+1, numOfAnswers, rankMsg));

			if ( super.isGradeable )
				super.questionAnswer.addResult(str, Integer.toString(i));
			else
				super.questionAnswer.addResult(str, "");
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
