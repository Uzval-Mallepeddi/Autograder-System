package comp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileScanner 
{
	String data;
	int blankCount,lineCount,classCount,commentCount;
	
	public FileScanner(ArrayList<File> filelist) {
		for(File file : filelist)
		{
			ReadFile(file);
			data += "\n";
		}
		getClassCountImpl(data);
		getCommentCountImpl(data);
		
	}

	/**
	 * @return the file
	 */
	public String getFile() {
		return data;
	}

	/**
	 * @return the blankCount
	 */
	public int getBlankCount() {
		return blankCount;
	}

	/**
	 * @return the lineCount
	 */
	public int getLineCount() {
		return lineCount;
	}

	/**
	 * @return the classCount
	 */
	public int getClassCount() {
		return classCount;
	}

	/**
	 * @return the commentCount
	 */
	public int getCommentCount() {
		return commentCount;
	}
	
	private String ReadFile(File file) 
	{
		String line = "";
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null){
				data=data+line;	
				lineCount++;
				 if (line.isEmpty() || line.trim().equals("") || line.trim().equals("\n")){
					 blankCount++;
				 }
				
			}
			System.out.println("Lines: "+lineCount);
			System.out.println("Empty Lines: "+blankCount);
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + file.getName() + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + file.getName() + "'");
		}
		return data;
	}

	private void getClassCountImpl(String data)
	{
		 int ind;
		 String sub="class ";
	        for(int i=0; i+sub.length()<=data.length(); i++)    //i+sub.length() is used to reduce comparisions
	        {
	            ind=data.indexOf(sub,i);
	            if(ind>=0)
	            {
	                classCount++;
	                i=ind;
	                ind=-1;
	            }
	        }
			System.out.println("Class :"+classCount);
	}

	private void getCommentCountImpl(String data) 
	{	
		 int ind;
		 String sub="//";
	        for(int i=0; i+sub.length()<=data.length(); i++)    //i+sub.length() is used to reduce comparisions
	        {
	            ind=data.indexOf(sub,i);
	            if(ind>=0)
	            {
	                commentCount++;
	                i=ind;
	                ind=-1;
	            }
	        }
			System.out.println("Single Line Comments:"+commentCount);
			
			/*For Mulitple Line Comment*/
			
			sub="/*";
			
			ind=0;
		        for(int i=0; i+sub.length()<=data.length(); i++)    //i+sub.length() is used to reduce comparisions
		        {
		            ind=data.indexOf(sub,i);
		            if(ind>=0)
		            {
		                commentCount++;
		                i=ind;
		                ind=-1;
		            }
		        }
				System.out.println("Multiple Line Comments:"+commentCount);			
	}
	
	@Override
	public String toString() {
		return "Line count : "+lineCount
			  +"\nBlank count : "+blankCount
			  +"\nClass count : "+classCount
			  +"\nComment count : "+commentCount;
	}
}
