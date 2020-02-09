package comp;

public class InstanceOutput 
{
	String testInput,expectedOutput,actualOutput;
	boolean isPassed;
	
	public InstanceOutput() {
		super();
	}

	public InstanceOutput(String testInput, String expectedOutput, String actualOutput, boolean isPassed) {
		super();
		this.testInput = testInput;
		this.expectedOutput = expectedOutput;
		this.actualOutput = actualOutput;
		this.isPassed = isPassed;
	}

	public String getTestInput() {
		return testInput;
	}

	public void setTestInput(String testInput) {
		this.testInput = testInput;
	}

	public String getExpectedOutput() {
		return expectedOutput;
	}

	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
	}

	public String getActualOutput() {
		return actualOutput;
	}

	public void setActualOutput(String actualOutput) {
		this.actualOutput = actualOutput;
	}

	public boolean isPassed() {
		return isPassed;
	}

	public void setPassed(boolean isPassed) {
		this.isPassed = isPassed;
	}

	@Override
	public String toString() {
		String status = ""; 
				
		if(isPassed)
			status = "Passed";
		else
			status = "Failed";
		
		String s = "Status : "+status;
		s += "\nInput : \n"+testInput;
		s += "\nExpectedOutput : \n"+expectedOutput;
		s += "\nYour Output : \n"+actualOutput;
		
//		return "Output [testInput=" + testInput + ", expectedOutput=" + expectedOutput + ", actualOutput="
//				+ actualOutput + ", isPassed=" + isPassed + "]";
		
		return s;
	}
	
	
}
