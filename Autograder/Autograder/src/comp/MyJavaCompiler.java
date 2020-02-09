package comp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map.Entry;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import controllers.RunAssignmentServlet;
import mystream.StringBufferOutputStream;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//import userAssignment.Solution;

public class MyJavaCompiler 
{
	private static final String OUTPUT_DELIMETER = "``##**``";

	public static class MyDiagnosticListener implements DiagnosticListener<JavaFileObject>
    {
        public void report(Diagnostic<? extends JavaFileObject> diagnostic)
        {
 
            System.out.println("Line Number->" + diagnostic.getLineNumber());
            System.out.println("code->" + diagnostic.getCode());
            System.out.println("Message->"
                               + diagnostic.getMessage(Locale.ENGLISH));
            System.out.println("Source->" + diagnostic.getSource());
            System.out.println(" ");
        }
    }
	
	@SuppressWarnings("unchecked")
//	public synchronized CompiledOutput compile(String file) throws IOException, InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException
//	{
//		System.out.println("Compiling the program ...");
//
////		appendFile
//		
//        Process p = Runtime.getRuntime().exec("javac " + file);
//        String output = getOutput(p.getInputStream());
////        getOutput("Std.Out", p.getErrorStream());
//        p.waitFor();
//        p.destroy();
////		if(p.exitValue() ==  0)
////		{
////			file = file.replaceAll("//", "/");
////			file = file.replace(".java", ".class");
////			// Create a File object on the root of the directory containing the class file
////			Class cls = Class.forName("Solution");
////			Object obj = cls.newInstance();
////
////			String args[] = {};
////			Class noparams[] = {};
////			//call the printIt method
////			Method method = null;
////			Method[] methods = null;
////			try {
//////				method = cls.getDeclaredMethod("main", noparams);
////				methods = cls.getDeclaredMethods();
////				
//////				method.invoke(obj, null);
////			} catch (IllegalArgumentException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}		
////		}
//        
//        return new CompiledOutput(p.exitValue(), output);
//	}
//	
//	protected String getOutput(InputStream in) throws IOException {      
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//        String out = "";
//        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
//        	out += line;
//            System.out.println(line);
//        }
//        
//        return out;
//    }

	/** compile your files by JavaCompiler */
    public static boolean compile(ArrayList<File> files, String classOutputFolder, Output output, String uname, String assignmentId) throws IOException
    {
	       JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	       StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
	       DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
	       Iterable options = Arrays.asList("-d", classOutputFolder);
	       
	       Iterable<? extends JavaFileObject> compilationUnits =
	           fileManager.getJavaFileObjectsFromFiles(files);
	      boolean result =  compiler.getTask(null, fileManager,diagnostics, options, null, compilationUnits).call();
	      
	      String compilationOutput = "";
	      
	      if (result) {
	    	    compilationOutput = "Compilation : Sucessfull";
	    	    output.setCompiledSucessfully(true);
	    	    output.setCompiledOutput(compilationOutput);
	    	} else 
	    	{
	    		String sourcePath = RunAssignmentServlet.EXE_ENV_DIR+File.separator+uname+File.separator+assignmentId+File.separator+"SourceCode";
	    		compilationOutput = "Compilation : Failed";
	    		for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
	    			String errorTrace = diagnostic.toString();
	    	        compilationOutput += "\n"+ errorTrace.replace(sourcePath,"");
	    	     }
	    		output.setCompiledSucessfully(false);
	    	    output.setCompiledOutput(compilationOutput);
	    	}
	       
	       fileManager.close();
	       return result;
    }
	
	public synchronized Output run(File classOutputDir,ArrayList<File> testfiles, ArrayList<File> exOutputfiles,Double compilationGrade, Output output)
	{
		System.out.println("Executing the program ...");
		Double grade = 0.0; 
		ArrayList<String> testcases = getData(testfiles);
		ArrayList<String> exOutput = getData(exOutputfiles);
		HashMap<String, Integer> map = null;
		try {
			
			ObjectInputStream mapIn = new ObjectInputStream(new FileInputStream("C:/"+File.separator+"Autograder"+File.separator+"Rubic"+File.separator + "grademap.rub"));
			map = (HashMap<String, Integer>) mapIn.readObject();
			if(map == null)
				throw new Exception("Map Not found.");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		ArrayList<InstanceOutput> outputList = new ArrayList<>();
		
        
		
		PrintStream stdout = System.out;
		PrintStream stderr = System.err;
		InputStream stdin = System.in;

		int count = 0;

		InputStream in = null;
	
		Class cls;
//		float grade = 0.0f;
		
		for(int i=0 ; i< testcases.size() && i<exOutput.size(); i++)
		{
			count++;
			String testcase = testcases.get(i);
			String expectedOutput =  exOutput.get(i);
			
			ByteArrayOutputStream bout = new ByteArrayOutputStream();			
			PrintStream out = new PrintStream(bout);
			
			ByteArrayInputStream bin = new ByteArrayInputStream(testcase.getBytes());
			System.setOut(out);

		   try 
   		   {
			 try
	         {
				System.setIn(bin);
	            // Convert File to a URL
	            URL url = classOutputDir.toURI().toURL(); // file:/classes/demo
	            URL[] urls = new URL[] { url };
	            
	            String args[] = new String[1];
	            
	            // Create a new class loader with the directory
	            ClassLoader loader = new URLClassLoader(urls);
	 
	            // Load in the class; Class.childclass should be located in
	            // the directory file:/class/demo/
	            Class thisClass = loader.loadClass("Solution");
	 
	            Object instance = thisClass.newInstance();
	            Method thisMethod = thisClass.getDeclaredMethod("main", args.getClass());
	            
	            // run the testAdd() method on the instance:
	            thisMethod.invoke(instance, args);
	        }
			catch (InvocationTargetException e) {
				String exception = e.getTargetException().toString();
				boolean flag = false;
				for (Entry<String, Integer> exce : map.entrySet()) {
				    if (exception.startsWith(exce.getKey())) {
				        grade += exce.getValue();
				        flag = true;
				    }
				}
				
				if(flag)
					grade -= 2;
			}

	        catch (MalformedURLException e)
	        {
	        }
	        catch (ClassNotFoundException e)
	        {
	        }
	        catch (Exception ex)
	        {
	            ex.printStackTrace();
	        }
			
			String s = bout.toString();
			
			InstanceOutput Ioutput = new InstanceOutput();
			Ioutput.setTestInput(testcase);
			Ioutput.setActualOutput(s);
			Ioutput.setExpectedOutput(expectedOutput);
			Ioutput.setPassed(s.equals(expectedOutput));
			
			if(Ioutput.isPassed())
				grade += 5;
						
			stdout.print(Ioutput);
			stdout.print("Grade : "+grade+"\n\n");
			outputList.add(Ioutput);
			
			bin.close();
			out.close();
			bout.close();

		  }
	      catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }	
		  finally 
		  {
			System.setOut(stdout);
			System.setIn(stdin);
		  }
		}
//		Output output = new Output();
		output.setOutputlist(outputList);
		output.setGrades(output.computeGrade((grade/count)+compilationGrade));
		
		return output;
	}
	
	private ArrayList<String> getData(ArrayList<File> fileList)
	{
		Collections.sort(fileList, new Comparator<File>(){

			@Override
			public int compare(File o1, File o2) {
				return o1.getName().compareToIgnoreCase(o2.getName());
			}
			
		});
		
		ArrayList<String> list = new ArrayList<String>();
		
		for(File file : fileList)
		{
			char[] buff = new char[1024];
			String s = "";
			FileReader fin = null;
			try 
			{
				fin = new FileReader(file);
				int i = -1;
				while((i = fin.read()) != -1)
				{
					s += (char)i;
				}
			} catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			finally {
				try {
					fin.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			list.add(s);
		}
		return list;
	}
}
