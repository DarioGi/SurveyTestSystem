import java.io.Serializable;
import java.nio.file.*;
import java.util.Vector;
import java.io.IOException;

public abstract class Inquiry implements Serializable
{
	private final String DEFAULT_INQUIRY_EXTENSION = ".inquiry";
	private final String DEFAULT_INQUIRY_PATH = "./Inquiries";
	protected String inquiryName;
	protected String inquiryPath;
	protected boolean isInquirySaved;
	Vector<Question> questions;
	MenuSelection createAddQuestionsSelection;
	
	public Inquiry(String inquiryName)
	{
		this.inquiryName = inquiryName;
		this.inquiryPath = DEFAULT_INQUIRY_PATH;
		this.isInquirySaved = false;
		createQuestionMenu();
	}
	
	void createQuestions()
	{
		createAddQuestionsSelection.select(null);
	}
	
	public String getFilename()
	{
		return inquiryPath;
	}
	
	public String getFilepath()
	{
		return inquiryPath;
	}
	
	public boolean saveInquiry()
	{
		try 
		{
			String filename;
			if ( !isInquirySaved )
				filename = getNewInquiryName();
			else
				filename = inquiryPath;
			if ( filename.compareToIgnoreCase("") == 0 )
				return false;
			SerializationUtil.serialize(this, filename);
			if ( !isInquirySaved )
			{
				isInquirySaved = true;
				this.inquiryPath = filename;
			}
			return true;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	private String getNewInquiryName()
	{
		Path path = Paths.get(inquiryPath);
	
		if ( Files.isDirectory(path) ) 
		{
			int i = 0;
			String strPath;
			while ( true )
			{
				strPath = getFilePath(inquiryPath, inquiryName, i, DEFAULT_INQUIRY_EXTENSION);
				path = Paths.get(strPath);
				if ( Files.isRegularFile(path) )
					i++;
				else
					return strPath;
			}
		} 
		else
			return "";
	}
	
	private String getFilePath(String path, String filename, int index, String extension)
	{
		return path + "//" + filename + "_" + Integer.toString(index) + extension;
	}
	
	private void createQuestionMenu()
	{
		createAddQuestionsSelection = new MenuSelection("Create new.");
		Question q1 = new Question("Specify a T/F question");
		Question q2 = new Question("Specify a multiple choice question");
		createAddQuestionsSelection.addSelection(new addQuestionSelection("Add a new T/F question", q1));
		createAddQuestionsSelection.addSelection(new addQuestionSelection("Add a new multiple choice question", q2));
	}
}
	
