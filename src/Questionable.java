
public interface Questionable 
{
	String[] getQuestion();
	void setQuestion(String question);
	
	boolean setAnswerWeight();
	int getAnswerWeight();
	
	void createQuestion(); // Used when a question is being created.
	void modifyQuestion(); // Used when a question is being modified.
	void askQuestion();    // Used when a question is being displayed to the user.
	
}
