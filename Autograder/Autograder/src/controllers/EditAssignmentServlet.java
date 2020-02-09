package controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import dao.UserAccess;
import dto.Assignment;

/**
 * Servlet implementation class EditAssignmentServlet
 */
@WebServlet("/EditAssignmentServlet")
public class EditAssignmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditAssignmentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sess = request.getSession();
		
		String uname = (String)sess.getAttribute("uname");
		int assignmentId = -1;
		
		assignmentId = Integer.parseInt(request.getParameter("aid"));
		
		String ques = request.getParameter("ques").trim();
		String description = request.getParameter("description").trim();
		
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
		
		Assignment assignment = new Assignment(assignmentId, ques, description,dateOfSub);
		UserAccess ob = new UserAccess();
		
		boolean result = false;
		try {
			result = ob.editAssignment(assignment);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(result)
			response.sendRedirect("/Autograder/Instructor.jsp");
		else
			response.sendError(HttpURLConnection.HTTP_BAD_REQUEST, "Unable add assignment ");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
