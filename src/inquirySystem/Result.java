package inquirySystem;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Result implements Resultable, Serializable
{
	ArrayList<ArrayList<String>> result;
	private int numResults = 0;
	public Result()
	{
		result = new ArrayList<ArrayList<String>>();
		result.add(new ArrayList<String>());
		result.add(new ArrayList<String>());
	}
	
	@Override
	public ArrayList<ArrayList<String>> getResult() 
	{
		return result;
	}

	@Override
	public boolean compare(Result r) // Basic result compare, good for now
	{
		if ( r.getNumResults() != this.numResults || //the number of results doesn't much, it means they aren't equal.
				r.getNumResults() == 0 || this.numResults == 0 )
			return false;
		else
		{
			ArrayList<ArrayList<String>> rResult = r.getResult();
			for (int i = 0; i < numResults; i++ )
			{
				if ( result.get(0).get(i).compareToIgnoreCase(rResult.get(0).get(i)) == 0 && 
					 result.get(1).get(i).compareToIgnoreCase(rResult.get(1).get(i)) == 0 )
					continue;
				else
					return false;
			}
			return true;
		}
	}

	@Override
	public void addResult(String r1, String r2) 
	{
		result.get(0).add(r1);
		result.get(1).add(r2);
		numResults++;
	}

	@Override
	public boolean removeResult(int index) 
	{
		if (index < 0 && index > numResults-1)
			return false;
		else
		{
			result.get(0).remove(index);
			result.get(1).remove(index);
			numResults--;
		}
		return false;
	}

	@Override
	public void removeAllResults(int index) 
	{
		result.get(0).clear();
		result.get(1).clear();
		numResults = 0;
	}

	@Override
	public int getNumResults() 
	{
		return numResults;
	}

}