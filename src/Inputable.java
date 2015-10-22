/* Interface used to verify the input from the user. */
public interface Inputable 
{
	void printPrompt();
	String askForInput();
	int verifyInput(String input);
}
