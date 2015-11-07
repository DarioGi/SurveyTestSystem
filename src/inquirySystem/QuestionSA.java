package inquirySystem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

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
	public Result askQuestion() 
	{
		Result res = new Result();
		printToInquiry(super.question + "\n");
		String ans = "";
		for ( int i = 0; i < numOfAnswers; i++ )
		{
			ans = super.askForString(String.format("Answer %d:", i+1));
			res.addResult(ans, "");
		}
		res.setUniqueIdentifier(super.questionAnswer.getUniqueIdentifier());
		return res;		
	}
	
	public String tabulateQuestion(Vector<Result> results)
	{
		String output = "";
		output += super.question + "\n"; 
		output += getAnswerChoices() + "\n";
		Map<String, Integer> ansMap = new HashMap<String, Integer>();
		Iterator<Result> rIt = results.iterator();
		while ( rIt.hasNext() )
		{
			Result tempR = rIt.next();
			ArrayList<String> ansList = new ArrayList<String>();
			for ( int ans = 0; ans < tempR.getNumResults(); ans++ )
			{
				ansList.add(tempR.result.get(0).get(ans));
			}
			if ( ansMap.containsKey(ansList.toString()) )
				ansMap.put(ansList.toString(), ansMap.get(ansList.toString()) + 1);
			else
				ansMap.put(ansList.toString(), 1);
		}
		for ( String key : ansMap.keySet() )
		{
			int index = 1;
			String tempStr = String.format("%d)\n", ansMap.get(key));
			key = key.substring(1, key.length()-1);
			for ( String val : key.split(", ") )
			{
				tempStr += String.format("%s. %s\n", String.valueOf(index++), val.trim());
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
				if ( !questionAnswer.getResult().get(0).get(index).equalsIgnoreCase(result.getResult().get(0).get(index)) )
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
