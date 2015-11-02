package inquirySystem;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class InquirySelection extends Selection 
{
	protected Inquiries type;
	protected Vector<Inquiry> inquiries;
	protected CreateNewInquirySelection createInquirySelection;
	protected DisplayInquirySelection displayInquirySelection;
	protected ChoiceInquirySelection loadInquirySelection;
	protected SaveInquirySelection saveInquirySelection;
	protected ModifyInquirySelection modifyInquirySelection;
	
	protected int currentInquiryIndex;
	protected Inquiry currentInquiry;
	protected final String createAStr = "Create a new ";
	protected final String displayAStr = "Display a ";
	protected final String loadAStr = "Load a ";
	protected final String saveAStr = "Save a ";
	protected final String modifyAStr = "Modify a ";
	public final static String INQUIRY_TEST = "Test";
	public final static String INQUIRY_SURVEY = "Survey";
	public final static String INQUIRY_DEFAULT = "INVALID";
	public final static String DEFAULT_INQUIRY_PATH = "./Inquiries";
	public final static String DEFAULT_INQUIRY_EXTENSION = ".inquiry";
	private Vector<SelectionChoice> loadInquirySelections;
	
	protected InquirySelection(String selectionName, Inquiries type) 
	{
		super(selectionName);
		this.type = type;
		currentInquiryIndex = -1;
		currentInquiry = null;
		inquiries = new Vector<Inquiry>();
		loadInquirySelections = new Vector<SelectionChoice>();
		(new File(DEFAULT_INQUIRY_PATH)).mkdirs();
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
							inquiry = new InquirySurvey(INQUIRY_SURVEY, DEFAULT_INQUIRY_PATH, getFirstAvailableInquiryIndex(), DEFAULT_INQUIRY_EXTENSION, false);
							break;
						case Test:
							inquiry = new InquiryTest(INQUIRY_TEST, DEFAULT_INQUIRY_PATH, getFirstAvailableInquiryIndex(), DEFAULT_INQUIRY_EXTENSION, false);
					}
					selection.elementAt(index-1).select(inquiry);
					currentInquiry = inquiry;
					inquiries.add(inquiry);
					createMenu();
					continue;
				}
				else if ( selection.elementAt(index-1) instanceof ChoiceInquirySelection )
				{
					Inquiry temp = loadInquiry();
					if ( temp != null )
					{
						currentInquiry = temp;
						addInquiryToVector(currentInquiry);
						createMenu();
					}
					continue;
				}
				else if ( selection.elementAt(index -1) instanceof SaveInquirySelection )
				{
					if ( currentInquiry != null )
					{
						boolean didSucceed = currentInquiry.saveInquiry();
						out.output(String.format("Save %s: %s %d.", didSucceed ? "succesful":"unsuccessful", currentInquiry.getFilename(), currentInquiry.getInquiryIndex()));
					}
					else
					{
						continue;
					}
				}
				else if ( selection.elementAt(index -1) instanceof DisplayInquirySelection )
				{
					if ( currentInquiry != null )
					{
						currentInquiry.displayInquiry();
					}
					else
					{
						continue;
					}
				}
				else if ( selection.elementAt(index -1) instanceof ModifyInquirySelection )
				{
					Inquiry temp = loadInquiry();
					if ( temp != null )
					{
						currentInquiry = temp;
						addInquiryToVector(currentInquiry);
						currentInquiry.modifyInquiry();
					}
					continue;
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
		this.selection.removeAllElements();
		// Create new
		createInquirySelection = new CreateNewInquirySelection(getSelectionString(createAStr, false));
		this.selection.add(createInquirySelection);
		
		// Display
		displayInquirySelection = new DisplayInquirySelection(getSelectionString(displayAStr, true));
		this.selection.add(displayInquirySelection);
		
		// Load
		loadInquirySelection = new ChoiceInquirySelection(getSelectionString(loadAStr, false));
		this.selection.add(loadInquirySelection);
			
		// Save
		saveInquirySelection = new SaveInquirySelection(getSelectionString(saveAStr, true));
		this.selection.add(saveInquirySelection);
		
		// Modify
		modifyInquirySelection = new ModifyInquirySelection(getSelectionString(modifyAStr, false));
		this.selection.add(modifyInquirySelection);
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
	
	
	// This method creates a menu with all available Inquiries
	private Inquiry loadInquiry()
	{
		Inquiry inquiry = null;
		int selectionIndex = createLoadInquiryMenu();// Decrement from selection
		if ( selectionIndex >= 0 )
		{
			try 
			{
				switch ( type )
				{
					case Survey:
						inquiry = (InquirySurvey) SerializationUtil.deserialize(Inquiry.getFilePath(DEFAULT_INQUIRY_PATH, getInquiryName(type), selectionIndex, DEFAULT_INQUIRY_EXTENSION));
						break;
					case Test:
						inquiry = (InquiryTest) SerializationUtil.deserialize(Inquiry.getFilePath(DEFAULT_INQUIRY_PATH, getInquiryName(type), selectionIndex, DEFAULT_INQUIRY_EXTENSION));
				}

				inquiry.inquiryIndex = selectionIndex;
				inquiry.isInquirySaved = true;
				out.output("Succesfully loaded " + getInquiryName(type) + " " + Integer.toString(selectionIndex));
			} 
			catch ( ClassNotFoundException | IOException e) 
			{
				out.output(String.format("Error: Unable to load %s file.", Inquiry.getFilePath(DEFAULT_INQUIRY_PATH, getInquiryName(type), selectionIndex, DEFAULT_INQUIRY_EXTENSION)));
			}
			catch (ClassCastException ca)
			{
				out.output(String.format("Error: Unable to load %s file.",
						Inquiry.getFilePath(DEFAULT_INQUIRY_PATH, getInquiryName(type), selectionIndex, DEFAULT_INQUIRY_EXTENSION), 
						"Did you rename this to another type?"));
			}
			catch (Exception e)
			{
				out.output(String.format("Unexpected Error: Unable to load %s file.", Inquiry.getFilePath(DEFAULT_INQUIRY_PATH, getInquiryName(type), selectionIndex, DEFAULT_INQUIRY_EXTENSION)));
			}
		}
		else
		{
			out.output("Nothing found.");
		}
		return inquiry;
	}
	
	private int createLoadInquiryMenu()
	{
		Vector<Integer> availableInquiries = getInquiryList(getFullSearchFilePath());
		loadInquirySelection.removeAllSelections();
		loadInquirySelections.removeAllElements();
		Iterator<Integer> it = availableInquiries.iterator();
		while ( it.hasNext() )
		{
			loadInquirySelections.addElement(new SelectionChoice());
			loadInquirySelection.addSelection(new ChoiceSelection(getInquiryName(type) + " " + Integer.toString(it.next()), loadInquirySelections.lastElement()));
		}
		loadInquirySelection.select(null);
		Iterator<SelectionChoice> itSC = loadInquirySelections.iterator();
		int count = 0;
		while ( itSC.hasNext() )
		{
			int choice = itSC.next().getSelectionChoice();
			if ( choice != -1)
				return availableInquiries.elementAt(count);
			count++;
		}
		return -1;
	}
	
	
	private int getFirstAvailableInquiryIndex()
	{
		List<Integer> availableInquiries = getInquiryList(getFullSearchFilePath());
		Collections.sort(availableInquiries);
		int available = 0;
		Iterator<Integer> it = availableInquiries.iterator();
		while ( it.hasNext() )
		{
			if ( available != it.next() )
				return available;
			else
				available++;
		}
		return available;
	}
	
	// This assumes the following filename format-> /PATH/NAME_*.EXTENSION
	private Vector<Integer> getInquiryList(String filename)
	{
		String filenameRaw;
		String path;
		int tempIndex = filename.lastIndexOf("/");
		
		if ( tempIndex != -1 )
		{
			filenameRaw = filename.substring(tempIndex+1);
			path = filename.substring(0, tempIndex);
		}
		else
		{
			filenameRaw = filename;
			path = ".//";
		}
		if ( filenameRaw.lastIndexOf(".") == -1 )
			return null;
		
		filenameRaw = filenameRaw.substring(0, filenameRaw.lastIndexOf(".")); // Remove Extension
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		Vector<Integer> foundFiles = new Vector<Integer>();
		if ( listOfFiles == null )
			return null;
		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if ( listOfFiles[i].isFile() ) 
			{
				String tempFile = listOfFiles[i].getName();
				int index = tempFile.lastIndexOf("/");
				
				if ( index != -1 )
					tempFile = tempFile.substring(index+1);
				
				int extIndex = tempFile.lastIndexOf(".");
				if ( extIndex != -1 )
				{
					int underscoreIndex = tempFile.lastIndexOf("_");
					if ( underscoreIndex != -1 )
					{
						String temp = tempFile.substring(0, underscoreIndex);
						if ( temp.compareTo(filenameRaw) == 0 )
						{
							try
							{
								String str = tempFile.substring(underscoreIndex+1, extIndex);
								int value = Integer.parseInt(tempFile.substring(underscoreIndex+1, extIndex));
								if ( str.length() != String.valueOf(value).length() ) // A case where random crap is added to the filename.
									continue;
								foundFiles.addElement(value);
							}
							catch (NumberFormatException e)
							{
							}
						}
					}
				}
		    }
		}
		return foundFiles;
	}
	
	
	private String getInquiryName(Inquiries type)
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
	
	
	private String getFullSearchFilePath()
	{
		return DEFAULT_INQUIRY_PATH +  "/" + getInquiryName(type) + DEFAULT_INQUIRY_EXTENSION;
	}
	
	
	private void addInquiryToVector(Inquiry inquiry)
	{
		for ( int i = 0; i < inquiries.size(); i++ )
		{
			if ( inquiries.elementAt(i).inquiryName.compareToIgnoreCase(inquiry.inquiryName) == 0 )
			{
				inquiries.removeElementAt(i);
			}
		}
		inquiries.addElement(inquiry);
	}
}
