package inquirySystem;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

@SuppressWarnings("serial")
public abstract class Question implements Questionable, Serializable
{
	public static final int DEFAULT_ANSWER_WEIGHT = 1;
	protected String question;
	protected String questionType;
	protected boolean isGradeable;
	protected Result questionAnswer;
	protected int answerWeight;
	transient private OutputInquiry outInquiry;
	transient private OutputMenu outMenu;
	transient private Input input;
	
	Question(String question, boolean isGradeable)
	{
		this.question = question;
		this.isGradeable = isGradeable;
		questionAnswer = new Result();
		this.answerWeight = DEFAULT_ANSWER_WEIGHT;
		outInquiry = new OutputInquiry();
		outMenu = new OutputMenu();
		input = new Input();
	}
	
	@Override
	public void setQuestion(String question) 
	{
		this.question = question;
	}

	@Override
	public boolean setAnswerWeight(int weight) 
	{
		if ( weight < 0 )
		{
			return false;
		}
		else
		{
			this.answerWeight = weight;
			return true;
		}
	}

	@Override
	public int getAnswerWeight() 
	{
		return answerWeight;
	}
	
	protected void enterPrompt()
	{
		outMenu.output(String.format("Enter a prompt for your %s question:", questionType));
		setQuestion(input.getInput());
	}
	
	
	protected String getInput()
	{
		if ( input == null )
			input = new Input();
		return input.getInput();
	}
	
	protected void printToMenu(String str)
	{
		if ( outMenu == null )
			outMenu = new OutputMenu();
		outMenu.output(str);
	}
	
	protected void printToInquiry(String str)
	{
		if ( outInquiry == null );
			outInquiry = new OutputInquiry();
		outInquiry.output(str);
	}
	
	
	protected int askForNumber(String prompt, int min, int max)
	{
		int val;
		while ( true )
		{
			printToMenu(String.format(prompt));
			String strVal = getInput();
			try
			{
				val = Integer.parseInt(strVal);
				if ( val >= min && val <= max )
					break;
				printToMenu(String.format("Error: Enter a value [%d, %d].", min, max));
			}
			catch (NumberFormatException e)
			{
				printToMenu(String.format("Error: Enter a value [%d, %d].", min, max));
			}
		}
		return val;
	}
	
	protected String askForString(String prompt)
	{
		printToMenu(String.format(prompt));
		return getInput();
	}
	
	// askForTF and askForYN can be a same function, but who cares..
	protected String askForTF(String prompt)
	{
		String in = "";
		while ( true )
		{
			printToMenu(prompt);
			in = getInput();
			if ( in.compareToIgnoreCase("t") == 0 ||
					in.compareToIgnoreCase("f") == 0 )
				break;
		}
		return in;
	}
	
	protected String askForCharStr(String prompt, Vector<String> acceptableStrings)
	{
		String choice;
		Iterator<String> it = acceptableStrings.iterator();
		boolean choiceFound = false;
		while ( true )
		{
			printToMenu(prompt);
			it = acceptableStrings.iterator();
			choice = getInput();
			while ( it.hasNext() )
			{
				if ( choice.compareToIgnoreCase(it.next()) == 0 )
					choiceFound = true;
			}
			if ( choiceFound )
			{
				return choice.toUpperCase();
			}
			else
			{
				printToMenu(String.format("Invalid input. Acceptable answers: %s", acceptableStrings.toString()));
			}
		}
	}
	// -1 error, 1-26 A-Z
	protected int askForCharNum(String prompt, Vector<String> acceptableStrings)
	{
		String out = askForCharStr(prompt, acceptableStrings);
		if ( out.length() == 1 )
			return Character.getNumericValue(out.charAt(0)) - Character.getNumericValue('A');
		else
			return -1;
	}
	
	protected boolean askForYN(String prompt)
	{
		String in = "";
		while ( true )
		{
			printToMenu(prompt);
			in = getInput();
			if ( in.compareToIgnoreCase("y") == 0 )
				return true;
			else if ( in.compareToIgnoreCase("n") == 0)
				return false;
		}
	}
	
	
	protected Vector<String> getAlphabetVector(int numOfChars)
	{
		if ( numOfChars < 1 || numOfChars > 26 )
			return null;
		Vector<String> vS = new Vector<String>();
		char elem = 'A';
		for ( int i = 0; i < numOfChars; i++ )
		{
			vS.addElement(String.valueOf(elem));
			elem++;
		}
		return vS;
	}
	
}
