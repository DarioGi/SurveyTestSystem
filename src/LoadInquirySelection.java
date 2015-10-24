
public class LoadInquirySelection extends Selection 
{
	
	InquirySelection parent;
	protected LoadInquirySelection(String selectionName, InquirySelection parent) 
	{
		super(selectionName);
	}

	public void select()
	{
		parent.setCurrentInquiry(null);
		// Should load the inquiries from the file, and display the selections
	}
}
