package inquirySystem;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

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
		Result res = new Result();
		printToInquiry(super.question);
		String in = super.askForTF("Enter correct choice (T/F):");
		res.addResult(in.compareToIgnoreCase("t") == 0 ? "True" : "False", "");
		res.setUniqueIdentifier(super.questionAnswer.getUniqueIdentifier());
		return res;		
	}
	
	public String tabulateQuestion(Vector<Result> results)
	{
		String output = "";
		int trueCount = 0;
		int falseCount = 0;
		output += super.question + "\n";
		Iterator<Result> rIt = results.iterator();
		while ( rIt.hasNext() )
		{
			if ( rIt.next().result.get(0).get(0).equals("True") )
				trueCount++;
			else
				falseCount++;
		}
		output += String.format("True: %d\n", trueCount);
		output += String.format("False: %d\n", falseCount);
		output += "\n";
		return output;
	}

	public boolean gradeQuestion(Result result) 
	{
		if ( !isGradeable )
			return true;
		if ( !result.getUniqueIdentifier().equals(questionAnswer.getUniqueIdentifier()) ||
				result.getNumResults() !=  questionAnswer.getNumResults() )
		{
			printToMenu("Error: Supplied result is incompatible with the question.");
			return false;
		}
		if ( result.getResult().get(0).get(0).equals(questionAnswer.getResult().get(0).get(0)) )
			return true;
		else
			return false;
	}
}
