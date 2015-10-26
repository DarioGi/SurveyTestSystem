
public class CreateNewInquirySelection extends Selection
{

	protected CreateNewInquirySelection(String selectionName) 
	{
		super(selectionName);
	}

	@Override
	public Object select(Object o)
	{
		Inquiry inquiry;
		if ( o instanceof Inquiry )
			inquiry = (Inquiry) o;
		else
			return null;
		
		inquiry.createQuestions();
		return null;
	}
}
