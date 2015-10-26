import java.awt.Menu;
import java.util.Vector;

public class InquirySelection extends Selection 
{

	protected Inquiries type;
	protected Vector<Inquiry> inquiries;
	protected CreateNewInquirySelection createInquirySelection;
	protected MenuSelection displayInquirySelection;
	protected MenuSelection loadInquirySelection;
	protected MenuSelection saveInquirySelection;
	
	protected int currentInquiryIndex;
	protected Inquiry currentInquiry;
	protected final String createAStr = "Create a new ";
	protected final String displayAStr = "Display a ";
	protected final String loadAStr = "Load a ";
	protected final String saveAStr = "Save a ";
	
	protected InquirySelection(String selectionName, Inquiries type) 
	{
		super(selectionName);
		this.type = type;
		currentInquiryIndex = -1;
		currentInquiry = null;
		inquiries = new Vector<Inquiry>();
		createMenu();
	}

	@Override
	public Object select(Object o)
	{
		while ( true )
		{
			int index = verifyInput(askForInput());
			if ( index != -1 && index != selection.size()+1 )
			{
				if ( selection.elementAt(index) instanceof CreateNewInquirySelection )
				{
					Inquiry inquiry = null;
					switch ( type )
					{
						case Survey:
							inquiry = new InquirySurvey("Survey");
							
						case Test:
							inquiry = new InquirySurvey("Test");
					}
					selection.elementAt(index).select(inquiry);
					currentInquiry = inquiry;
					inquiries.add(inquiry);
				}
				else if ( selection.elementAt(index) instanceof LoadInquirySelection )
				{
					currentInquiry = loadInquiry(type);
					addInquiryToVector(currentInquiry);
				}
			}
			else if ( index == selection.size()+1 )
				break;
		}
		return null;
	}
	
	protected String getSelectionString(String str, boolean checkIfAvailable)
	{
		return str + selectionName + (inquiries.isEmpty() && checkIfAvailable ? " <Unavailable - Load/Create a " + selectionName + " first>" : "");
	}
	
	private void createMenu()
	{
		// Create new
		createInquirySelection = new CreateNewInquirySelection(getSelectionString(createAStr, false));
		this.selection.add(createInquirySelection);
		
		// Display
		displayInquirySelection = new MenuSelection(getSelectionString(displayAStr, true));
		this.selection.add(displayInquirySelection);
		
		// Load
		loadInquirySelection = new MenuSelection(getSelectionString(loadAStr, false));
		this.selection.add(loadInquirySelection);
			
		// Save
		saveInquirySelection = new MenuSelection(getSelectionString(saveAStr, true));
		this.selection.add(saveInquirySelection);
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
	
	
	public static Inquiry loadInquiry(Inquiries type)
	{
		Inquiry inquiry = null;
		switch ( type )
		{
			case Survey:
				
			
			case Test:
		}
		return inquiry;
	}
	
	
	private void addInquiryToVector(Inquiry inquiry)
	{
		for ( int i = 0; i < inquiries.size(); i++ )
		{
			if ( inquiries.elementAt(i).inquiryName.compareToIgnoreCase(inquiry.inquiryName) == 0 )
			{
				currentInquiryIndex = i;
				return;
			}
		}
		inquiries.addElement(inquiry);
		currentInquiryIndex = inquiries.size() - 1;
	}
}
