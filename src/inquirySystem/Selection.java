package inquirySystem;
import java.util.Vector;

public abstract class Selection implements Selectable
{
	protected Vector<Selection> selection;
	protected String selectionName;
	protected String inputQuestion = "Enter a selection: ";
	protected String invalidInputMsg = "Invalid input, try again...";
	protected OutputMenu out;
	protected Input input;
	protected String exitString = "Go Back";
	
	protected Selection(String selectionName)
	{
		input = new Input();
		this.selectionName = selectionName;
		selection = new Vector<Selection>();
		out = new OutputMenu();
	}
	
	public Object select(Object o)
	{
		if ( selection.isEmpty() )
			return null; //Simply return if empty
		Object arg = null;
		if ( o != null )
			arg = o;
		while ( true )
		{
			int index = verifyInput(askForInput());
			if ( index != -1 && index != selection.size()+1 )
				selection.elementAt(index-1).select(arg);
			else if ( index == selection.size()+1 )
				break;
		}
		return null;
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
	
	
	public void removeAllSelections()
	{
		selection.clear();
	}
	
	
	public Selectable getSelection(int selectionID)
	{
		if ( selectionID < 0 || selectionID > selection.size() )
			return null;
		else
			return selection.get(selectionID);
	}
	
	public int getSelectionSize()
	{
		return selection.size();
	}
	
	public String askForInput()
	{
		String in = "";
		while (true)
		{
			try
			{
				printPrompt();
				out.output(inputQuestion);
				in = input.getInput();
				break;
			}
			catch(Exception e)
			{
				out.output(invalidInputMsg);
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
			out.output(String.format("%d. %s", i+1, selection.elementAt(i).selectionName));
		}
		if ( selection.size() == 0 )
			out.output("Nothing found.");
		out.output(String.format("%d. %s\n", selection.size()+1, exitString));
	}
}
