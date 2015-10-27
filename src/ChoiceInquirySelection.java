
public class ChoiceInquirySelection extends Selection 
{
	protected ChoiceInquirySelection(String selectionName)
	{
		super(selectionName);
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
			{
				selection.elementAt(index-1).select(arg);
				break; //Add a break if a good selection was made
			}
			else if ( index == selection.size()+1 )
				break;
		}
		return null;
	}
}
