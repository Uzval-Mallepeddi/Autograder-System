package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.UserAccess;
import dto.Assignment;

/**
 * Servlet implementation class AddAssignment
 */
@WebServlet("/AddAssignmentServlet")
@MultipartConfig(maxFileSize = 16177215)    // upload file's size up to 16MB
public class AddAssignmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final int MAX_MEMORY_SIZE = 1024 * 1024 * 2;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddAssignmentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sess = request.getSession();
		
		Part filePart = request.getPart("testcase");
		Part exOutputPart = request.getPart("exOutput");
		
		String uname = (String)sess.getAttribute("uname");
		int assignmentId = -1;
		try {
			assignmentId = Assignment.getNextAssignmentId();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String ques = request.getParameter("ques").trim();
		String description = request.getParameter("description").trim();
		Date dateOfUpload = new Date(System.currentTimeMillis());
		//int nclass = Integer.parseInt(request.getParameter("nclass").trim());
		System.out.println(request.getParameter("dateOfSub"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateOfSub = null;
		try {
			String s = request.getParameter("dateOfSub").trim();
			System.out.println(s);
			java.util.Date d = sdf.parse(s);
			System.out.println(d);
			dateOfSub = new Date(d.getTime());
			
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		InputStream testFile=null;
		InputStream exOutputFile=null;
		try {
			testFile  = filePart.getInputStream();
			exOutputFile  = exOutputPart.getInputStream();
			if(testFile == null || exOutputFile == null)
				throw new Exception("Unable to get file stream");
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		
		Assignment assignment = new Assignment(uname, assignmentId, ques, description, dateOfUpload, testFile,dateOfSub,exOutputFile,-1);
		UserAccess ob = new UserAccess();
		
		boolean result = false;
		try {
			result = ob.addAssignment(assignment);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(result)
			response.sendRedirect("/Autograder/Instructor.jsp");
		else
			response.sendError(HttpURLConnection.HTTP_BAD_REQUEST, "Unable add assignment ");
	}

}
