package inquirySystem;

public class ChoiceSelection extends Selection
{
	SelectionChoice choice;
	protected ChoiceSelection(String selectionName, SelectionChoice choice) 
	{
		super(selectionName);
		this.choice = choice;
	}

	public Object select(Object o)
	{
		choice.setSelectionChoise(1);
		return null;
	}
}
