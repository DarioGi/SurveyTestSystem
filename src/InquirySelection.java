import java.util.Vector;

public class InquirySelection extends Selection 
{

	protected Inquiries type;
	protected Vector<Inquiry> inquiries;
	protected InquirySelection(String selectionName, Inquiries type) 
	{
		super(selectionName);
		this.type = type;
	}

//	@Override
//	public void select() 
//	{
//		createMenu();
//	}
//	
//	private void createMenu()
//	{
//		
//	}


}
