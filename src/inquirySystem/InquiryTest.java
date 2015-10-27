package inquirySystem;
import java.io.Serializable;

@SuppressWarnings("serial")
public class InquiryTest extends Inquiry implements Serializable
{
	public InquiryTest(String inquiryName, String inquiryPath, int inquiryIndex, String inquiryExtension, boolean isSaved) 
	{
		super(inquiryName, inquiryPath, inquiryIndex, inquiryExtension, isSaved, true);
	}
}
