import java.io.Serializable;

public class InquiryTest extends InquirySurvey implements Serializable
{
	public InquiryTest(String inquiryName, String inquiryPath, int inquiryIndex, String inquiryExtension, boolean isSaved) 
	{
		super(inquiryName, inquiryPath, inquiryIndex, inquiryExtension, isSaved);
	}
}
