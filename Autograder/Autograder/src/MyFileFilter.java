import java.io.File;

public class MyFileFilter implements java.io.FileFilter {

	@Override
	public boolean accept(File pathname) {
		
		if (pathname.isDirectory()) 
		    return true; // return directories for recursion
        
        return !(pathname.getName().endsWith(".txt")); // return false for .txt files else true 
	}

}
