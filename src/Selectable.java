/* Interface used to define an object which can be selected. */
public interface Selectable 
{
	//Each selection can hold multiple selections.
	void select();
	boolean addSelection(Selection selection);
	boolean removeSelection(int selectionID);
	Selectable getSelection(int selectionID);
}
