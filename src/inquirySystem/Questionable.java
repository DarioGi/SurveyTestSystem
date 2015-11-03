package inquirySystem;

public interface Questionable 
{
	String getQuestion();
	void setQuestion(String question);
	
	boolean setAnswerWeight(int weight);
	int getAnswerWeight();
	
	void createQuestion(); // Used when a question is being created.
	void modifyQuestion(); // Used when a question is being modified.
	Result askQuestion();    // Used when a question is being displayed to the user.
	
}
