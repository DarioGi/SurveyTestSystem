import java.awt.Menu;
import java.nio.file.Paths;
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
	public final static String INQUIRY_TEST = "Test";
	public final static String INQUIRY_SURVEY = "Survey";
	public final static String INQUIRY_DEFAULT = "INVALID";
	public final static String DEFAULT_INQUIRY_PATH = "./Inquiries";
	public final static String DEFAULT_INQUIRY_EXTENSION = ".inquiry";
	
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
				if ( selection.elementAt(index-1) instanceof CreateNewInquirySelection )
				{
					Inquiry inquiry = null;
					switch ( type )
					{
						case Survey:
							inquiry = new InquirySurvey(INQUIRY_SURVEY, DEFAULT_INQUIRY_PATH, DEFAULT_INQUIRY_EXTENSION);
							
						case Test:
							inquiry = new InquirySurvey(INQUIRY_TEST, DEFAULT_INQUIRY_PATH, DEFAULT_INQUIRY_EXTENSION);
					}
					selection.elementAt(index-1).select(inquiry);
					currentInquiry = inquiry;
					inquiries.add(inquiry);
				}
				else if ( selection.elementAt(index-1) instanceof LoadInquirySelection )
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
		String name = getInquiryName(type);
			
		return inquiry;
	}
	
	// This assumes the following filename format./PATH/NAME_*.EXTENSION
	private String[] getInquiryList(String filename)
	{
		int tempIndex = filename.lastIndexOf("//");
		String filenameRaw;
		String extension;
		if ( tempIndex < filename.length() )
			filenameRaw = filename.substring(tempIndex);
		else
			filenameRaw = filename;
		if ( filenameRaw.lastIndexOf(".") < filenameRaw.length() )
			extension = filenameRaw.substring(tempIndex);
		else
			return null;
		
		// Get the list of files in the directory, and store matching file paths into a list.
	}
	
	private static String getInquiryName(Inquiries type)
	{
		switch ( type )
		{
			case Survey:
				return INQUIRY_SURVEY;
			case Test:
				return INQUIRY_TEST;
			default:
				return INQUIRY_DEFAULT;
		}
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
