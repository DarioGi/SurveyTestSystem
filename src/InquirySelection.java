import java.util.Vector;

public class InquirySelection extends Selection 
{

	protected Inquiries type;
	protected Vector<Inquiry> inquiries;
	protected MenuSelection createInquirySelection;
	protected int currentInquiryIndex;
	protected Inquiry currentInquiry;
	
	
	protected InquirySelection(String selectionName, Inquiries type) 
	{
		super(selectionName);
		this.type = type;
		currentInquiryIndex = -1;
		currentInquiry = null;
		createMenu();
		
	}

	
	private void createMenu()
	{
		createInquirySelection = new MenuSelection("Create a " + selectionName);
		this.selection.add(createInquirySelection);
	}
	
	
	protected boolean setCurrentInquiry(int inquiryIndex)
	{
		if ( inquiryIndex > 0 || inquiryIndex < inquiries.size() )
		{
			this.currentInquiryIndex = inquiryIndex;
			this.currentInquiry = inquiries.elementAt(inquiryIndex);
			return true;
		}
		else 
		{
			return false;
		}
	}
	
	
	
	

}
