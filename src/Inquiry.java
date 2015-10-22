import java.io.Serializable;

public abstract class Inquiry implements Selectable, Serializable
{
	protected String inquiryName;
	
	public Inquiry(String inquiryName)
	{
		this.inquiryName = inquiryName;
	}
	
}
