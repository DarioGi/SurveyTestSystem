package inquirySystem;
import java.io.Serializable;

@SuppressWarnings("serial")
public class InquirySurvey extends Inquiry implements Serializable
{
	public InquirySurvey(String inquiryName, String inquiryPath, int inquiryIndex, String inquiryExtension, boolean isSaved) 
	{
		super(inquiryName, inquiryPath, inquiryIndex, inquiryExtension, isSaved);
	}
}
