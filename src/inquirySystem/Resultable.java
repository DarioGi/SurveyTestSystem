package inquirySystem;
import java.util.ArrayList;

public interface Resultable 
{
	ArrayList<ArrayList<String>> getResult();
	boolean compare(Result r);
	void addResult(String r1, String r2);
	boolean changeResult(int resIndex, String r1, String r2);
	boolean removeResult(int index);
	void removeAllResults();
	int getNumResults();
}
