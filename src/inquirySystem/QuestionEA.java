package inquirySystem;
import java.io.Serializable;

@SuppressWarnings("serial")
public class QuestionEA extends Question implements Serializable
{
	private final static int MAX_EA_ANSWERS = 100; // Arbitrary limit
	protected static final String type = "Essay Answer";
	private int numOfAnswers = 1;
	
	QuestionEA(String question, boolean isGradeable) 
	{
		super(question, isGradeable);
		super.questionType = type;
		super.answerWeight = 0;
	}

	@Override
	public String getQuestion() 
	{
		String outStr = super.question + "\n" 
								+ type + "\n" 
								+ "Number of answers: " + String.valueOf(numOfAnswers) ;
		
		return outStr;
	}

	@Override
	public void createQuestion() 
	{
		super.enterPrompt();
		numOfAnswers = super.askForNumber("Enter number of answers:", 1, MAX_EA_ANSWERS);
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
		if ( askForYN("Do you wish to modify number of answers (Y/N)?") )
		{
			numOfAnswers = super.askForNumber("Enter number of answers:", 1, MAX_EA_ANSWERS);
			printToMenu(getQuestion());
		}
	}

	@Override
	public Result askQuestion() 
	{
		return null;	
	}
}
