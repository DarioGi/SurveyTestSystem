
public class LoadInquirySelection extends Selection 
{
	
	InquirySelection parent;
	protected LoadInquirySelection(String selectionName, InquirySelection parent) 
	{
		super(selectionName);
	}

	public Object select(Object o)
	{
		String path;
		if ( o instanceof String )
			path = (String) o;
		else
			return null;
		
		

		return null;
	}
}
