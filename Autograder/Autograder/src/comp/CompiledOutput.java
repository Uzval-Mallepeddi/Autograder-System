package comp;

public class CompiledOutput 
{
	private int exitValue;
	private String output;
	
	public CompiledOutput(int exitValue, String output) {
		super();
		this.exitValue = exitValue;
		this.output = output;
	}

	public int getExitValue() {
		return exitValue;
	}

	public void setExitValue(int exitValue) {
		this.exitValue = exitValue;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}
	
}
