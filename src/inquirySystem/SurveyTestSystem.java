package inquirySystem;
/*
 * Author: Darius Remeika
 * Contact: darius.remeika@drexel.edu
 * Description: A survey/test system.
 */

public class SurveyTestSystem 
{
	public static void main(String[] args) 
	{
		MainSelectionExit mainMenu = new MainSelectionExit("Main Menu");
		mainMenu.addSelection(new InquirySelection("Test", Inquiries.Test));
		mainMenu.addSelection(new InquirySelection("Survey", Inquiries.Survey));
		mainMenu.select(null);
	}
}
