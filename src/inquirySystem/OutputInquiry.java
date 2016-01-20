package inquirySystem;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class OutputInquiry extends Output
{
	private static VoiceManager vManager = null;
	private static String voiceName = "kevin16";
	private static Voice voice = null;
		
	public OutputInquiry() 
	{
		if ( vManager == null )
		{
			vManager = VoiceManager.getInstance();
			voice = vManager.getVoice(voiceName);
			voice.allocate();
		}
	}
		
	@Override
	public void output(String arg) 
	{
		 System.out.printf(arg);
		 voice.speak(arg);
	}
}
