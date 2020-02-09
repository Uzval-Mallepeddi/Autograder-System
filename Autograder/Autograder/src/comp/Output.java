package comp;

import java.util.ArrayList;

import comp.InstanceOutput;

public class Output 
{
	private ArrayList<InstanceOutput> outputlist ;
	private String grades;
	private String compiledOutput;
	private String preCompilation;
	private boolean isCompiledSucessfully;
	
	public Output(ArrayList<InstanceOutput> outputlist, String grades) {
		super();
		this.outputlist = outputlist;
		this.grades = grades;
	}
	
	
	public Output() {
		super();
	}


	public ArrayList<InstanceOutput> getOutputlist() {
		return outputlist;
	}
	public void setOutputlist(ArrayList<InstanceOutput> outputlist) {
		this.outputlist = outputlist;
	}
	public String getGrades() {
		return grades;
	}
	public void setGrades(String grades) {
		this.grades = grades;
	}
		
	public String getCompiledOutput() {
		return compiledOutput;
	}


	public void setCompiledOutput(String compiledOutput) {
		this.compiledOutput = compiledOutput;
	}	
	
	public boolean isCompiledSucessfully() {
		return isCompiledSucessfully;
	}

	public void setCompiledSucessfully(boolean isCompiledSucessfully) {
		this.isCompiledSucessfully = isCompiledSucessfully;
	}

	public String getPreCompilation() {
		return preCompilation;
	}


	public void setPreCompilation(String preComilation) {
		this.preCompilation = preComilation;
	}


	public String computeGrade(double rating)
	{
		if(rating >= 8)
			return "A";
		else if(rating >=7 && rating < 8)
			return "B";
		else if(rating >=6 && rating < 7)
			return "C";
		else if(rating >=5 && rating < 6)
			return "D";
		else if(rating >=3 && rating < 5)
			return "E";
		else
			return "F";
	}
}
