import java.io.Serializable;
import java.nio.file.*;
import java.io.IOException;

public abstract class Inquiry implements Serializable
{
	private final String DEFAULT_INQUIRY_EXTENSION = ".inquiry";
	private final String DEFAULT_INQUIRY_PATH = "./Inquiries";
	protected String inquiryName;
	protected String inquiryPath; 
	
	public Inquiry(String inquiryName)
	{
		this.inquiryName = inquiryName;
		this.inquiryPath = DEFAULT_INQUIRY_PATH;
	}
	
	abstract public void createQuestions();
	
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
			String filename = getAValidName();
			if ( filename.compareToIgnoreCase("") == 0 )
				return false;
			SerializationUtil.serialize(this, getAValidName());
			return true;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	private String getAValidName()
	{
		Path path = Paths.get(inquiryPath);
	
		if ( Files.isDirectory(path) ) 
		{
			int i = 1;
			String strPath;
			while ( true )
			{
				strPath = inquiryPath + "//" + inquiryName + "_" + Integer.toString(i) + DEFAULT_INQUIRY_EXTENSION;
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
}
	
