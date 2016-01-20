package inquirySystem;
import java.io.Serializable;

@SuppressWarnings("serial")
public class MenuSelection extends Selection implements Serializable
{
	protected MenuSelection(String selectionName)
	{
		super(selectionName);
	}
}
