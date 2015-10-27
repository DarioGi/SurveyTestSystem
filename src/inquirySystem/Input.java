package inquirySystem;
import java.util.Scanner;

public class Input implements Inputable
{
	protected static Scanner input = new Scanner(System.in);
	
	public Input()
	{
	}
	
	@Override
	public String getInput() 
	{
		return input.nextLine();
	}

}
