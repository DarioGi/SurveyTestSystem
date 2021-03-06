package inquirySystem;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

@SuppressWarnings("serial")
public class QuestionEA extends Question implements Serializable
{
	private final static int MAX_EA_ANSWERS = 100; // Arbitrary limit
	protected static final String type = "Essay Answer";
	private int numOfAnswers = 1;
	
	QuestionEA(String question) 
	{
		super(question, false);
		super.questionType = type;
		super.answerWeight = 0;
	}

	@Override
	public String getQuestion() 
	{
		String outStr = super.question + "\n" 
								+ type + "\n" 
								+ "Number of responses: " + String.valueOf(numOfAnswers) ;
		
		return outStr;
	}

	@Override
	public void createQuestion() 
	{
		super.enterPrompt();
		numOfAnswers = super.askForNumber("Enter number of responses:", 1, MAX_EA_ANSWERS);
	}

	@Override
	public void modifyQuestion() 
	{
		printToMenu(getQuestion());
		if ( askForYN("Do you wish to modify the prompt (Y/N)?") )
		{
			enterPrompt();
			printToMenu(getQuestion());
		}
	}
	

	@Override
	public Result askQuestion() 
	{
		Result res = new Result();
		printToInquiry(super.question + "\n");
		String ans = "";
		for ( int i = 0; i < numOfAnswers; i++ )
		{
			ans = super.askForString(String.format("Response %d:", i+1));
			res.addResult(ans, "");
		}
		res.setUniqueIdentifier(super.questionAnswer.getUniqueIdentifier());
		return res;	
	}
	
	public String tabulateQuestion(Vector<Result> results)
	{
		String output = "";
		output += super.question + "\n";
		Iterator<Result> rIt = results.iterator();
		while ( rIt.hasNext() )
		{
			Result tempR = rIt.next();
			for ( int ans = 0; ans < numOfAnswers; ans++ )
			{
				output += tempR.result.get(0).get(ans) + "\n";
			}
			output += "\n";	
		}
		return output;
	}
	
	
	public boolean gradeQuestion(Result result) 
	{
		return false;
	}
}
