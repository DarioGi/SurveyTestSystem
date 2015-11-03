package inquirySystem;
import java.io.Serializable;

@SuppressWarnings("serial")
public class QuestionTF extends Question implements Serializable
{
	protected static final String type = "T/F"; 
	QuestionTF(String question, boolean isGradeable) 
	{
		super(question, isGradeable);
		super.questionType = type;
	}

	@Override
	public String getQuestion() 
	{
		String outStr = super.question + "\n" + type;
		if ( isGradeable )
		{
			outStr = outStr.concat("\nThe correct choice is " + super.questionAnswer.getResult().get(0).get(0) + ".");
		}
		return outStr;
	}

	private void askForTrueFalseInput()
	{
		String in = super.askForTF("Enter correct choice (T/F):");
		super.questionAnswer.addResult(in.compareToIgnoreCase("t") == 0 ? "True" : "False", "");
	}
	
	@Override
	public void createQuestion() 
	{
		enterPrompt(); // Asks the question, and user enter a value.
		if ( isGradeable )
			askForTrueFalseInput();
	}
	
	public void modifyQuestion()
	{
		printToMenu(String.format("Current prompt: %s", question));
		if ( askForYN("Do you wish to modify the prompt (Y/N)?") )
		{
			enterPrompt();
		}
		if ( isGradeable )
		{
			if ( askForYN("Do you wish to modify the answer (Y/N)?") )
			{
				questionAnswer.removeAllResults();
				askForTrueFalseInput();
			}
		}
	}

	@Override
	public Result askQuestion() 
	{
		return null;	
	}
}
