package inquirySystem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

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
				+ type + "\n" + getAnswerChoices();
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
				outStr += String.format(" Rank: %s",res.get(1).get(i));
			}
			if ( i != super.questionAnswer.getNumResults()-1 )
				outStr += "\n";
		}
		return outStr;
	}
	
	private Map<Integer, Integer> generateAnswerMap()
	{
		Random rnd = new Random();
		Map<Integer, Integer> temp = new HashMap<Integer, Integer>();
		Vector<Integer> list = getVectorOfInts(numOfAnswers, 1);
		int index;
		for ( int i = 0; i < numOfAnswers; i++ )
		{
			index = rnd.nextInt(list.size());
			temp.put(i, list.get(index));
			list.remove(index);
		}
		return temp;
	}
	
	
	private String getRandomizedAnswerChoices(Map<Integer, Integer> answerMap)
	{
		char alphabet = 'A';
		String outStr = "";
		ArrayList<ArrayList<String>> res = super.questionAnswer.getResult();
		for ( int i = 0; i < super.questionAnswer.getNumResults(); i++ )
		{
			outStr += String.format("%s) %-20s\n", alphabet++, res.get(0).get(answerMap.get(i)-1));
		}
		return outStr;
	}

	@Override
	public Result askQuestion() 
	{
		Result res = new Result();
		Map<Integer, Integer> answerMap = generateAnswerMap();
		Map<String, Integer> inputMap = new HashMap<String, Integer>();
		Vector<String> availStrings = getAlphabetVector(numOfAnswers);
		printToInquiry(super.question + "\n" + getRandomizedAnswerChoices(answerMap));
		for ( int i = 0; i < numOfAnswers; i++ )
		{
			int index;
			if ( i == 0 )
				index = askForCharNum(String.format("Enter choice with highest rank:", i+1, numOfAnswers), availStrings);
			else
				index = askForCharNum(String.format("Enter choice with next highest rank:", i+1, numOfAnswers), availStrings);
			inputMap.put(String.valueOf(indexToChar(answerMap.get(index)-1)), i);
			availStrings.remove(String.valueOf(indexToChar(index)));
		}
		res.setUniqueIdentifier(super.questionAnswer.getUniqueIdentifier());
		char alph = 'A';
		for ( int i = 0; i < numOfAnswers; i++ )
		{
			res.addResult(String.valueOf(alph), String.valueOf(inputMap.get(String.valueOf(alph))));
			alph++;
		}
		return res;	
	}
	
	public String tabulateQuestion(Vector<Result> results)
	{
		String output = "";
		output += super.question + "\n"; 
		output += getAnswerChoices() + "\n\n";
		Vector<Integer> count = new Vector<Integer>();
		Map<String, Integer> ansMap = new HashMap<String, Integer>();
		Iterator<Result> rIt = results.iterator();
		for ( int res = 0; res < super.questionAnswer.getNumResults(); res++ )
			count.add(0);
		while ( rIt.hasNext() )
		{
			Result tempR = rIt.next();
			ArrayList<String> ansList = new ArrayList<String>();
			for ( int ans = 0; ans < super.questionAnswer.getNumResults(); ans++ )
			{
				ansList.add(tempR.result.get(1).get(ans));
			}
			if ( ansMap.containsKey(ansList.toString()) )
				ansMap.put(ansList.toString(), ansMap.get(ansList.toString()) + 1);
			else
				ansMap.put(ansList.toString(), 1);
		}
		for ( String key : ansMap.keySet() )
		{
			char alphabet = 'A';
			String tempStr = String.format("%d)\n", ansMap.get(key));
			key = key.substring(1, key.length()-1);
			for ( String val : key.split(", ") )
			{
				tempStr += String.format("%s) %s\n", String.valueOf(alphabet++), val.trim());
			}
			output += tempStr + "\n";
		}
		return output;
	}
	
	
	public boolean gradeQuestion(Result result) 
	{
		if ( !isGradeable )
			return true;
		if ( !result.getUniqueIdentifier().equals(questionAnswer.getUniqueIdentifier()) ||
				result.getNumResults() != questionAnswer.getNumResults() )
		{
			printToMenu("Error: Supplied result is incompatible with the question.");
			return false;
		}
		int index = 0;
		try
		{
			while ( index < result.getNumResults())
			{
				if ( index != Integer.parseInt(result.getResult().get(1).get(index)) )
					return false;
				index++;
			}
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}
}
