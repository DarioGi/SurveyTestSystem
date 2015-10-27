package inquirySystem;

public abstract class SelectAction 
{
	@SuppressWarnings("unused")
	private Selection selection;
	public SelectAction(Selection selection)
	{
		this.selection = selection;
	}
}
