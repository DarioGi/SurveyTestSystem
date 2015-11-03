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
				+ "Number of answers: " + String.valueOf(numOfAnswers) + "\n"
				+ getAnswerChoices();
			
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
	public void modifyQuestion() 
	{
		printToMenu(getQuestion());
		if ( askForYN("Do you wish to modify the prompt (Y/N)?") )
		{
			enterPrompt();
			printToMenu(getQuestion());
		}

		if ( isGradeable )
		{
			while ( askForYN("Do you wish to modify the choices (Y/N)?") )
			{
				int choice = super.askForNumber("Which answer would you like to change?", 0, super.questionAnswer.getNumResults()-1);
				String newVal = super.askForString("Enter new value:");
				super.questionAnswer.changeResult(choice, newVal, "");
				printToMenu(getQuestion());
			}
		}
	}
	
	private String getAnswerChoices()
	{
		String outStr = "";
		if ( isGradeable )
		{
			for ( int i = 0; i < super.questionAnswer.getNumResults(); i++ )
			{
				outStr += String.format("%d) %s\n", i, super.questionAnswer.getResult().get(0).get(i));
			}
		}
		return outStr;
	}

	@Override
	public void askQuestion() 
	{
		// TODO Auto-generated method stub
		
	}
}
