package controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.net.ssl.HttpsURLConnection;

import comp.Output;
import dao.UserAccess;

/**
 * Servlet implementation class SubmitServlet
 */
@WebServlet("/SubmitServlet")
public class SubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String uname = (String)session.getAttribute("uname");
		
		
//		Output runoutput =  (Output) session.getAttribute("runoutput");
//		String assignmentID = (String) session.getAttribute("aid");
		
		Output runoutput =  (Output)request.getAttribute("runoutput");
		String assignmentID = (String)request.getAttribute("aid");
	 	
		
		if(uname == null || runoutput == null || assignmentID == null)
		{
//			session.removeAttribute("runoutput");
//			session.removeAttribute("aid");
			response.sendRedirect("Login.html");
		}
		
//		session.removeAttribute("runoutput");
//		session.removeAttribute("aid");
		
		int aid = Integer.parseInt(assignmentID);
		
		boolean isUpdaate = false;
		try {
			isUpdaate = new UserAccess().submitAssignmentToTemp(aid, runoutput, uname);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(isUpdaate)
			response.sendRedirect("/Autograder/Student.jsp");
		else
			response.sendError(HttpURLConnection.HTTP_NOT_MODIFIED	, "Unable to udpate the DB");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
