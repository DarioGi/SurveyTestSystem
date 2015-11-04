package inquirySystem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

@SuppressWarnings("serial")
public class QuestionM extends Question implements Serializable
{
	private final static int MAX_M_ANSWERS = 26; // Alphabet limit, arbitrary as well
	private final static int MIN_M_ANSWERS = 2; // Doesn't make sense to have 1.
	protected static final String type = "Matching";
	private int numOfAnswers = 2;
	
	QuestionM(String question, boolean isGradeable) 
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
		numOfAnswers = super.askForNumber("Enter number of matches:", MIN_M_ANSWERS, MAX_M_ANSWERS);
		for ( int i = 0; i < numOfAnswers; i++ )
		{
			String str1 = askForString(String.format("%d of %d) Enter first pair choice:", i+1, numOfAnswers));
			String str2 = askForString(String.format("%d of %d) Enter second pair choice:", i+1, numOfAnswers));
			super.questionAnswer.addResult(str1, str2);
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
			String ans1 = super.askForString("Enter first pair choice:");
			String ans2 = super.askForString("Enter second pair choice:");
			super.questionAnswer.changeResult(choice, ans1, ans2);
			printToMenu(getQuestion());
		}
	}
	
	private String getAnswerChoices()
	{
		char alphabet = 'A';
		String outStr = "";
		ArrayList<ArrayList<String>> res = super.questionAnswer.getResult();
		for ( int i = 0; i < super.questionAnswer.getNumResults(); i++ )
		{
			outStr += String.format("%s) %-25s  %d) %-25s\n", alphabet++, res.get(0).get(i), i+1, res.get(1).get(i));
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
			outStr += String.format("%s) %-25s  %d) %-25s\n", alphabet++, res.get(0).get(i), i+1, res.get(1).get(answerMap.get(i)-1));
		}
		return outStr;
	}
	
	@Override
	public Result askQuestion() 
	{
		Result res = new Result();
		Map<Integer, Integer> answerMap = generateAnswerMap();
		printToInquiry(super.question + "\n" + getRandomizedAnswerChoices(answerMap));
		Vector<String> availStrings = getAlphabetVector(numOfAnswers);
		Vector<Integer> availInts = getVectorOfInts(numOfAnswers, 1);
		ArrayList<Integer> tempRes;
		for ( int i = 0; i < numOfAnswers; i++ )
		{
			tempRes = askForCharAndInt("Enter choice and value pair:", availStrings, availInts);
			res.addResult(questionAnswer.result.get(0).get(tempRes.get(0)),
					questionAnswer.result.get(1).get(answerMap.get(tempRes.get(1))-1));
			availStrings.removeElementAt(tempRes.get(0));
			availInts.removeElementAt(tempRes.get(1));
		}
		res.setUniqueIdentifier(super.questionAnswer.getUniqueIdentifier());
		return res;		
	}
	
	public String tabulateQuestion(Vector<Result> results)
	{
		return "";
	}
}
