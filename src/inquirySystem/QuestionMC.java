package inquirySystem;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class QuestionMC extends Question implements Serializable
{
	private final static int MAX_MC_ANSWERS = 26; // Alphabet limit, arbitrary as well
	private final static int MIN_MC_ANSWERS = 2; // Doesn't make sense to have 1.
	protected static final String type = "Multiple Choice";
	private int numOfAnswers = 2;
	private int numOfInputAnswers = 1;
	
	QuestionMC(String question, boolean isGradeable) 
	{
		super(question, isGradeable);
		super.questionType = type;
	}

	@Override
	public String getQuestion() 
	{
		String outStr = super.question + "\n" 
				+ type + "\n" + getAnswerChoices() + "\n";
		return outStr;
	}

	@Override
	public void createQuestion() 
	{
		super.enterPrompt();
		numOfAnswers = super.askForNumber("Enter number of choices:", MIN_MC_ANSWERS, MAX_MC_ANSWERS);
		int numToInput = 0;
		for ( int i = 0; i < numOfAnswers; i++ )
		{
			String str= askForString(String.format("%d of %d) Enter choice:", i+1, numOfAnswers));
			if ( super.isGradeable )
			{
				boolean in = askForYN("Is this choice a correct answer? (Y/N):");
				super.questionAnswer.addResult(str, in ? "True" : "False");
				numToInput++;
			}
			else
			{
				super.questionAnswer.addResult(str, "");
			}
		}
		if ( super.isGradeable )
		{
			numOfInputAnswers = numToInput;
		}
		else
		{
			numOfInputAnswers = askForNumber("Enter number of answers for user to input: ", 1, numOfAnswers);
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
		while ( askForYN("Do you wish to modify the choices (Y/N)?") )
		{
			int choice = super.askForCharNum(String.format("Which choice would you like to modify?\n%s", getAnswerChoices()), super.getAlphabetVector(super.questionAnswer.getNumResults()));
			String newVal = super.askForString("Enter new value:");
			super.questionAnswer.changeResult(choice, newVal, questionAnswer.getResult().get(1).get(choice));
			printToMenu(getQuestion());
		}
		if ( isGradeable )
		{
			while ( askForYN("Do you wish to modify the answers (Y/N)?") )
			{
				int choice = super.askForCharNum(String.format("Which answer would you like to modify?\n%s", getAnswerChoices()), super.getAlphabetVector(super.questionAnswer.getNumResults()));
				boolean in = askForYN("Is this choice a correct answer? (Y/N):");
				super.questionAnswer.changeResult(choice, questionAnswer.getResult().get(0).get(choice), in ? "True" : "False");
				printToMenu(getQuestion());
			}
		}
	}
	
	private String getAnswerChoices()
	{
		String outStr = "";
		char alphabet = 'A';
		ArrayList<ArrayList<String>> res = super.questionAnswer.getResult();
		for ( int i = 0; i < super.questionAnswer.getNumResults(); i++ )
		{
			outStr += String.format("%s) %-20s ", alphabet++, res.get(0).get(i));
			if ( isGradeable )
			{
				outStr += String.format(" Is correct: %s",res.get(1).get(i));
			}
			if ( i != super.questionAnswer.getNumResults()-1 )
				outStr += "\n";
		}
		return outStr;
	}

	@Override
	public void askQuestion() {
		// TODO Auto-generated method stub
		
	}
}
