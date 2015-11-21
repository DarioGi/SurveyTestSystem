package inquirySystem;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Vector;

public class InquiryResult implements Serializable
{
	protected String resultName;
	protected String resultPath;
	protected String resultExtension;
	protected int resultIndex;
	protected boolean isResultSaved;
	public final static String DEFAULT_RESULT_PATH = "/Results";
	public final static String DEFAULT_RESULT_EXTENSION = ".result";
	transient private OutputMenu outMenu;
	protected Vector<Result> results;
	
	public InquiryResult(String resultPath, String resultName, int resultIndex, String resultExtension)
	{
		this.resultPath = resultPath;
		this.resultName = resultName;
		this.resultExtension = resultExtension;
		this.resultIndex = resultIndex;
		this.results = null;
		isResultSaved = false;
		outMenu = new OutputMenu();
		(new File(resultPath)).mkdirs();
	}
	
	public boolean saveResult()
	{
		try 
		{
			(new File(resultPath)).mkdirs();
			SerializationUtil.serialize(this, Inquiry.getFilePath(resultPath, resultName, resultIndex, resultExtension));
			if ( !isResultSaved )
				isResultSaved = true;
			return true;
		} 
		catch (IOException e) 
		{
			printToMenu(String.format("Error: %s", e.getMessage()));
			return false;
		}
	}
	
	public void setResults(Vector<Result> results)
	{
		this.results = results;
	}
	
	public Vector<Result> getResults()
	{
		return this.results;
	}
	
	protected void printToMenu(String str)
	{
		if ( outMenu == null )
			outMenu = new OutputMenu();
		outMenu.output(str);
	}
}
