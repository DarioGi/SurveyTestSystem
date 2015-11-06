package inquirySystem;

import java.util.Vector;

public interface Questionable 
{
	String getQuestion();
	void setQuestion(String question);
	
	boolean setAnswerWeight(int weight);
	int getAnswerWeight();
	
	void createQuestion(); // Used when a question is being created.
	void modifyQuestion(); // Used when a question is being modified.
	Result askQuestion();    // Used when a question is being displayed to the user.
	String tabulateQuestion(Vector<Result> results); // Used for tabulating the results.
	boolean gradeQuestion(Result result); //  Returns true if the answer matches.
}
