package inquirySystem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
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
		questionAnswer.setUniqueIdentifier(getHashCode());
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
	
	//Special use only
	protected char indexToChar(int val)
	{
		char retChar = 'A';
		for ( int i = 0; i < val; i++)
			retChar++;
		return retChar;
	}
	
	protected int charToInt(char ch)
	{
		return Character.getNumericValue(ch) - Character.getNumericValue('A');
	}
	
	
	protected ArrayList<Integer> askForCharAndInt(String prompt, Vector<String> acceptableStrings, Vector<Integer> acceptableInts)
	{
		Iterator<String> it = acceptableStrings.iterator();
		String ans;
		while ( true )
		{
			printToMenu(prompt);
			it = acceptableStrings.iterator();
			ans = getInput();
			int indexLetter = -1;
			int indexInt = -1;
			if ( ans.length() < 3 )
			{
				printToMenu(String.format("Invalid input. Answer format: letter integer"));
				continue;
			}
			boolean letterChoiceFound = false;
			while ( it.hasNext() )
			{
				indexLetter++;
				if ( (String.valueOf(ans.charAt(0))).compareToIgnoreCase(it.next()) == 0 )
				{
					letterChoiceFound = true;
					break;
				}
			}
			if ( letterChoiceFound  )
			{
				if ( ans.charAt(1) == ' ' )
				{
					int choice = -1;
					try
					{
						choice = Integer.parseInt(ans.substring(2));
					}
					catch (NumberFormatException e)
					{
						printToMenu(String.format("Invalid input. A choice value you entered was already entered."));
						continue;
					}
					if ( choice != -1 )
					{
						boolean numChoiceFound = false;
						Iterator<Integer> intIt = acceptableInts.iterator();
						while ( intIt.hasNext() )
						{
							indexInt++;
							if ( choice == intIt.next() )
							{
								numChoiceFound = true;
								break;
							}
						}
						if ( numChoiceFound )
						{
							ArrayList<Integer> ret = new ArrayList<Integer>();
							ret.add(indexLetter);
							ret.add(indexInt);
							return ret;
						}
						else
						{
							printToMenu(String.format("Invalid input. A choice value you entered was already entered."));
							continue;
						}
					}
					else
					{
						printToMenu(String.format("Invalid input. Answer format: letter integer"));
						continue;
					}
				}
				else
				{
					printToMenu(String.format("Invalid input. Answer format: letter integer"));
					continue;
				}
			}
			else
			{
				printToMenu(String.format("Invalid input. A letter you entered was already entered."));
			}
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
	
	public Vector<Integer> getVectorOfInts(int numOfInts, int startVal)
	{
		Vector<Integer> list = new Vector<Integer>();
		for ( int i = 0; i < numOfInts; i++)
		{
			list.add(startVal++);
		}
		return list;
	}
	
	public String getHashCode()
	{
		Random r = new Random();
		return String.valueOf(String.valueOf(r.nextInt()).hashCode());
	}
}
