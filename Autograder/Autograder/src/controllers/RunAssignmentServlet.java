package controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.function.Predicate;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.sun.net.ssl.HttpsURLConnection;

import comp.CompiledOutput;
import comp.FileScanner;
import comp.MyJavaCompiler;
import comp.Output;
import dao.UserAccess;
import comp.InstanceOutput;
import db.MyConnection;
import dto.Assignment;
import unziper.FileFilter;
import unziper.ZipOut;

/**
 * Servlet implementation class RunAssignmentServlet
 */
@WebServlet("/RunAssignmentServlet")
@MultipartConfig
public class RunAssignmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024;
       
    public static final String HOME = "C://";
    public static final String APP_ROOT = HOME + File.separator+"Autograder";
    public static final String USERS_DIR = APP_ROOT+File.separator+"Users";
    public static final String ASSIGNMENTS_DIR = APP_ROOT+File.separator+"Assignments";
    public static final String EXE_ENV_DIR = APP_ROOT+File.separator+"ExeEnv";
    public static final String RUBIC_DIR = APP_ROOT+File.separator+"Rubic";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RunAssignmentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String assignmentID = request.getParameter("aid");
//		int clscount = Integer.parseInt(request.getParameter("nclass"));
		int clscount = 1;
		try {
			clscount = new UserAccess().getAssignment(Integer.parseInt(assignmentID)).getNclass();
		} catch (NumberFormatException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String uname = request.getSession().getAttribute("uname").toString();
		
		if(uname == null || assignmentID == null || uname.equals("") || assignmentID.equals(""))
			return;
		
		File root = new File(HOME);

		double grade = 0;

//		System.out.println(userassignment.getAbsolutePath());
//		System.out.println(userassignment.mkdirs());
		
		
		
		Part filePart = request.getPart("solutionFile");
		
		if(filePart == null)
			return;
		
		InputStream solutionFile=null;
		try {
			solutionFile  = filePart.getInputStream();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		byte b[] = new byte[1024];
		int i = -1;
		
		String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
//		String filepath = userassignment.getAbsolutePath()+File.separator+fileName;

//		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(filepath));
//		FileOutputStream out = new FileOutputStream(filepath);
//		
//		while((i = solutionFile.read()) != -1)
//		{
//			
//			System.out.println(new String(b));
//			out.write(b);
//		}
//		
//		out.close();
//		
//		System.out.println(filepath);
//		System.out.println(userassignment.getAbsolutePath());
		
		
		
		String applicationPath = request.getServletContext().getRealPath("");
		
		File userassignment = new File(root, "Autograder"+File.separator+"Users"+File.separator+uname+File.separator+assignmentID);
//		userassignment.mkdirs();
        // constructs path of the directory to save uploaded file
        String uploadFilePath = userassignment.getAbsolutePath() ;
         
        // creates the save directory if it does not exists
//        File fileSaveDir = new File(uploadFilePath);
        String finalFileName = uploadFilePath + File.separator + fileName;
        
        if (!userassignment.exists()) {
        	userassignment.mkdirs();
        }
//        System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());
        
//        String fileName = null;
        //Get all the parts from request and write it to the file on server
        for (Part part : request.getParts()) {
//            fileName = getFileName(part);
            part.write(finalFileName);
        }
 
//        request.setAttribute("message", fileName + " File uploaded successfully!");
//        getServletContext().getRequestDispatcher("/response.jsp").forward(
//     
//		
        String classpath = RunAssignmentServlet.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(classpath);
//        String exeEnv = classpath + File.separator+"Autograder"+File.separator+"ExeEnv"+File.separator+uname+File.separator+assignmentID;
        String exeEnv = classpath.replace("/controllers", "");
//        String exeEnv = classpath + File.separator+"comp";
//        String exeEnv = classpath + File.separator+"userAssignment";
        System.out.println(exeEnv);
        String temp_exeEnv = RunAssignmentServlet.EXE_ENV_DIR+File.separator+uname+File.separator+assignmentID;
        
        String sourcecodePath = temp_exeEnv+File.separator+"SourceCode";
        String classOutputPath = temp_exeEnv+File.separator+"classes";
        
        File sourceCodeDir = new File(sourcecodePath);
        File classesDir = new File(classOutputPath);
        
        if(!sourceCodeDir.exists())
        	sourceCodeDir.mkdirs();
        
        if(!classesDir.exists())
        	classesDir.mkdirs();
        
		ArrayList<File> files =  ZipOut.unzip(finalFileName,sourcecodePath);
		
		FileScanner fs = new FileScanner(files);
		int linecount = fs.getLineCount();
		
		if(linecount == 0 )
		{
			response.sendError(HttpURLConnection.HTTP_NOT_ACCEPTABLE, "Empty file");
			return;
		}
		int blankcount = fs.getBlankCount();
		int classCount = fs.getClassCount();
		int commentCount = fs.getCommentCount();
		
		if(blankcount >= ((2*linecount)/3))
			grade += .5;
		
		
		if(classCount == clscount)
			grade += 1;
		
		if(commentCount >= (2 * classCount))
			grade += .5;
		
		Output output = new Output();
		output.setPreCompilation(fs.toString());
		MyJavaCompiler compiler = new MyJavaCompiler();
			
			try {
			
				boolean result =  compiler.compile(files,classOutputPath,output, uname, assignmentID);
				if(!result)
				{
					request.setAttribute("runoutput", output);
					request.setAttribute("aid", assignmentID);
					request.getRequestDispatcher("/SubmitServlet").forward(request, response);
					return;
				}
				else 
					grade += 3;
			} 
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
		String assignmentFolder = null;
		try {
			assignmentFolder = new MyConnection().downlaodTestFile(Integer.parseInt(assignmentID));
			
			System.out.println(assignmentFolder);
		} catch (NumberFormatException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String testFilePath = assignmentFolder+File.separator+"Testcase.zip";
		String exOutputFilePath = assignmentFolder+File.separator+"ExOutput.zip";
				
		ArrayList<File> fileList = ZipOut.unzip(testFilePath,assignmentFolder);
		ArrayList<File> exOutputList = ZipOut.unzip(exOutputFilePath,assignmentFolder);
		
		PrintStream stdout = System.out;
		InputStream stdin = System.in;

		
		if(fileList != null)
		{
			new MyJavaCompiler().run(classesDir,fileList,exOutputList,grade,output);
		}
		
		System.setIn(stdin);
		System.setOut(stdout);
		
		System.out.println("Grade : "+grade);
//		RunOutput.setGrades((Double.parseDouble(RunOutput.getGrades())+grade)+"");
		request.setAttribute("runoutput", output);
		request.setAttribute("aid", assignmentID);
		
		request.getRequestDispatcher("/SubmitServlet").forward(request, response);
		
	}

}
