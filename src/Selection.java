import java.util.Scanner;
import java.util.Vector;

public abstract class Selection implements Selectable, Inputable
{
	protected Vector<Selection> selection;
	protected String selectionName;
	protected Scanner input;
	protected String inputQuestion = "Enter a selection: ";
	protected String invalidInputMsg = "Invalid input, try again...";
	
	protected Selection(String selectionName)
	{
		this.selectionName = selectionName;
		selection = new Vector<Selection>();
		input = new Scanner(System.in);
	}
	
	public void select()
	{
		while ( true )
		{
			int index =  verifyInput(askForInput());
			if ( index != -1 && index != selection.size()+1 )
				selection.elementAt(index-1).select();
			else if ( index == selection.size()+1 )
				break;
		}
	}
	
	public boolean addSelection(Selection selection)
	{
		this.selection.add(selection);
		return true;
	}
	
	public boolean removeSelection(int selectionID)
	{
		if ( selectionID < 0 || selectionID > selection.size() )
			return false;
		else
			selection.removeElementAt(selectionID);
		return true;
	}
	
	public Selectable getSelection(int selectionID)
	{
		if ( selectionID < 0 || selectionID > selection.size() )
			return null;
		else
			return selection.get(selectionID);
	}
	
	public String askForInput()
	{
		String in = "";
		while (true)
		{
			try
			{
				printPrompt();
				System.out.print(inputQuestion);
				in = input.nextLine();
				break;
			}
			catch(Exception e)
			{
				System.out.println(invalidInputMsg);
			}
		}
		return in;
	}
	
	
	public int verifyInput(String in)
	{
		try
		{
			int val = Integer.parseInt(in);
			if ( val > 0 && val <= selection.size()+1 )
			{
				return val;
			}
			else
			{
				System.out.println(invalidInputMsg);
				return -1;
			}
		}
		catch (Exception e)
		{
			System.out.println(invalidInputMsg);
			return -1;
		}
	}
	
	
	public void printPrompt()
	{
		for ( int i = 0; i < selection.size(); i++ )
		{
			System.out.printf("%d. %s\n", i+1, selection.elementAt(i).selectionName);
		}
		if ( selection.size() == 0 )
			System.out.println("Nothing to see here..");
		System.out.printf("%d. Exit\n", selection.size()+1);
	}
}
