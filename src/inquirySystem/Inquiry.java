package inquirySystem;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;
import java.io.IOException;

@SuppressWarnings("serial")
public abstract class Inquiry implements Serializable
{
	protected String inquiryName;
	protected String inquiryPath;
	protected String inquiryExtension;
	protected boolean isInquirySaved;
	protected boolean areQuestionsGradeable;
	protected int inquiryIndex;
	private Vector<Question> questions;
	transient ChoiceInquirySelection questionAskSelection;
	transient private Vector<SelectionChoice> questionMenuSelections;
	transient protected OutputInquiry outInquiry;
	transient protected OutputMenu outMenu;
	
	public Inquiry(String inquiryName, String inquiryPath, int inquiryIndex, String inquiryExtension, boolean isSaved, boolean areQuestionsGradeable)
	{
		// Use this when creating a brand new one.
		this.inquiryName = inquiryName;
		this.inquiryPath = inquiryPath;
		this.inquiryExtension = inquiryExtension;
		this.isInquirySaved = isSaved;
		this.inquiryIndex = inquiryIndex;
		this.areQuestionsGradeable = areQuestionsGradeable;
		questions = new Vector<Question>();
	}
	
	public Inquiry(String inquiryName, String inquiryPath, int inquiryIndex, String inquiryExtension, boolean isSaved)
	{
		this(inquiryName, inquiryPath, inquiryIndex, inquiryExtension, isSaved, false);
	}
	
	void createQuestions()
	{
		
		while ( true )
		{
			createQuestionMenu();
			int choice = -1;
			questionAskSelection.select(null);
			Iterator<SelectionChoice> itSC = questionMenuSelections.iterator();	
			int count = 1;
			while ( itSC.hasNext() )
			{
				choice = itSC.next().getSelectionChoice() ;
				if ( choice != -1 )
					break;
				else
					count++;
			}
			if ( choice <= 0 )
				break;
			
			// Not very elegant, but will do in this case...
			// This would be need to be changed if the questions were added in different order, etc.
			Question q;
			if ( count == 1 )
			{
				q = new QuestionTF("", this.areQuestionsGradeable);
				q.createQuestion();
				questions.addElement(q);
			}
			else if ( count == 2)
			{
				q = new QuestionMC("", this.areQuestionsGradeable);
				q.createQuestion();
				questions.addElement(q);
			}
			else if ( count == 3)
			{
				q = new QuestionSA("", this.areQuestionsGradeable);
				q.createQuestion();
				questions.addElement(q);
			}
			else if ( count == 4)
			{
				q = new QuestionEA("", this.areQuestionsGradeable);
				q.createQuestion();
				questions.addElement(q);
			}
			else if ( count == 5)
			{
				q = new QuestionRC("", this.areQuestionsGradeable);
				q.createQuestion();
				questions.addElement(q);
			}
			else if ( count == 6)
			{
				q = new QuestionM("", this.areQuestionsGradeable);
				q.createQuestion();
				questions.addElement(q);
			}
			else
				break;
		}
	}
	
	public String getFilename()
	{
		return inquiryName;
	}
	
	public String getFilepath()
	{
		return inquiryPath;
	}
	
	public boolean saveInquiry()
	{
		try 
		{
			SerializationUtil.serialize(this, getFilePath(inquiryPath, inquiryName, inquiryIndex, inquiryExtension));
			if ( !isInquirySaved )
				isInquirySaved = true;
			return true;
		} 
		catch (IOException e) 
		{
			printToMenu(String.format("Error: %s", e.getMessage()));
			return false;
		}
	}
	
	
	public static String getFilePath(String path, String filename, int index, String extension)
	{
		return path + "/" + filename + "_" + Integer.toString(index) + extension;
	}
	
	
	private void createQuestionMenu()
	{
		questionMenuSelections = new Vector<SelectionChoice>();
		questionAskSelection = new ChoiceInquirySelection("None");
		
		addQuestionToMenu("Add a new T/F question");
		addQuestionToMenu("Add a new multiple choice question");
		addQuestionToMenu("Add a new short answer question");
		addQuestionToMenu("Add a new essay question");
		addQuestionToMenu("Add a new ranking question");
		addQuestionToMenu("Add a new matching question");
	}
	
	
	private void addQuestionToMenu(String name)
	{
		SelectionChoice tempSC = new SelectionChoice();
		questionMenuSelections.add(tempSC);
		questionAskSelection.addSelection(new ChoiceSelection(name, tempSC));
	}
	
	protected void displayInquiry()
	{
		for ( int q = 0; q < questions.size(); q++ )
		{
			printToInquiry(q+1 + ") " + questions.elementAt(q).getQuestion() + "\n");
		}
	}
	
	protected void printToMenu(String str)
	{
		if ( outMenu == null )
			outMenu = new OutputMenu();
		outMenu.output(str);
	}
	
	protected void printToInquiry(String str)
	{
		if ( outInquiry == null );
			outInquiry = new OutputInquiry();
		outInquiry.output(str);
	}
	
	public int getInquiryIndex()
	{
		return this.inquiryIndex;
	}
}
	