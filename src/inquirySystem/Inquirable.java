package inquirySystem;

public interface Inquirable 
{
	void createQuestions();
	Question getQuestion(int index);
	int getQuestionSize();
	void removeQuestion(int index);
	boolean saveInquiry();
	String getFilename();
	String getFilepath();
	void displayInquiry();
}
