import java.io.Serializable;

public class InquirySurvey extends Inquiry implements Serializable
{
	
	public InquirySurvey(String inquiryName, String inquiryPath, int inquiryIndex, String inquiryExtension, boolean isSaved) 
	{
		super(inquiryName, inquiryPath, inquiryIndex, inquiryExtension, isSaved);
	}
}
