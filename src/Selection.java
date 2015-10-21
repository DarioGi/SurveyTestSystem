import java.util.Vector;

public abstract class Selection implements Selectable, Inputable
{
	protected Vector<Selection> selection;
	protected String selectionName;
	
	protected Selection(String selectionName)
	{
		this.selectionName = selectionName;
		selection = new Vector<Selection>();
	}
	
	public void select()
	{
		boolean terminate = false;
		while ( !terminate )
		{
			askForInput();
		}
	}
	
	public boolean addSelection(Selection selection)
	{
		return true;
	}
	
	public boolean removeSelection(int selectionID)
	{
		return true;
	}
	
	public boolean getSelection(int selectionID)
	{
		return true;
	}
}
