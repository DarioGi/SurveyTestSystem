package inquirySystem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

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
				+ type + "\n" + getAnswerChoices(true) + "\n";
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
				if ( in )
					numToInput++;
			}
			else
			{
				super.questionAnswer.addResult(str, "");
			}
		}
		if ( !super.isGradeable )
		{
			numOfInputAnswers = askForNumber("Enter number of answers for user to input: ", 1, numOfAnswers);
		}
		else
		{
			numOfInputAnswers = numToInput;
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
			int choice = super.askForCharNum(String.format("Which choice would you like to modify?\n%s", getAnswerChoices(true)), super.getAlphabetVector(super.questionAnswer.getNumResults()));
			String newVal = super.askForString("Enter new value:");
			super.questionAnswer.changeResult(choice, newVal, questionAnswer.getResult().get(1).get(choice));
			printToMenu(getQuestion());
		}
		if ( isGradeable )
		{
			while ( askForYN("Do you wish to modify the answers (Y/N)?") )
			{
				int choice = super.askForCharNum(String.format("Which answer would you like to modify?\n%s", getAnswerChoices(true)), super.getAlphabetVector(super.questionAnswer.getNumResults()));
				boolean in = askForYN("Is this choice a correct answer? (Y/N):");
				super.questionAnswer.changeResult(choice, questionAnswer.getResult().get(0).get(choice), in ? "True" : "False");
				printToMenu(getQuestion());
			}
		}
	}
	
	private String getAnswerChoices(boolean showAnswers)
	{
		String outStr = "";
		char alphabet = 'A';
		ArrayList<ArrayList<String>> res = super.questionAnswer.getResult();
		for ( int i = 0; i < super.questionAnswer.getNumResults(); i++ )
		{
			outStr += String.format("%s) %-20s ", alphabet++, res.get(0).get(i));
			if ( isGradeable && showAnswers )
			{
				outStr += String.format(" Is correct: %s",res.get(1).get(i));
			}
			if ( i != super.questionAnswer.getNumResults()-1 )
				outStr += "\n";
		}
		return outStr;
	}

	@Override
	public Result askQuestion() 
	{
		Result res = new Result();
		printToInquiry(super.question + "\n" + getAnswerChoices(false));
		Vector<String> availStrings = getAlphabetVector(numOfAnswers);
		for ( int i = 0; i < numOfInputAnswers; i++ )
		{
			int index = askForCharNum(String.format("Enter answer %d of %d",i+1, numOfInputAnswers), availStrings);
			res.addResult(String.valueOf(super.indexToChar(index)), "True");
			availStrings.remove(String.valueOf(indexToChar(index)));
		}
		res.setUniqueIdentifier(super.questionAnswer.getUniqueIdentifier());
		return res;			
	}
	
	public String tabulateQuestion(Vector<Result> results)
	{
		String output = "";
		char alphabet = 'A';
		output += super.question + "\n"; 
		output += getAnswerChoices(false) + "\n\n";
		Vector<Integer> count = new Vector<Integer>();
		Iterator<Result> rIt = results.iterator();
		for ( int res = 0; res < super.questionAnswer.getNumResults(); res++ )
			count.add(0);
		while ( rIt.hasNext() )
		{
			Result tempR = rIt.next();
			for ( int ans = 0; ans < numOfInputAnswers; ans++ )
			{
				int index = super.charToInt(tempR.result.get(0).get(ans).charAt(0));
				count.setElementAt(count.elementAt(index)+1, index);
			}
		}
		for ( int res = 0; res < super.questionAnswer.getNumResults(); res++ )
		{
			output += String.format("%s: %d\n", alphabet, count.elementAt(res));
			alphabet++;
		}
		return output + "\n";
	}
	
	
	public boolean gradeQuestion(Result result) 
	{
		// TODO Auto-generated method stub
		return false;
	}
}
