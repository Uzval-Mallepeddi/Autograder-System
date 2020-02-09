package controllers;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserAccess;
import dto.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    processRequest(request,response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		String uname = request.getParameter("uname");
		String pass  = request.getParameter("pass");
		User user = null;
		try {
			user = new UserAccess().getUser(uname,pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if(user != null)
		{	
			HttpSession sess = request.getSession();
			sess.setAttribute("uname", uname);
			if(user.getType().equals("Student"))
			{
				
				response.sendRedirect("/Autograder/Student.jsp");
			}
			else if(user.getType().equals("Instructor"))
			{
				response.sendRedirect("/Autograder/Instructor.jsp");
			}
			
		}
		else 
		{
			request.setAttribute("msg","Invalid Username or Password");
			RequestDispatcher rd=request.getRequestDispatcher("/Login.jsp");            
			rd.forward(request, response);
		}
			
	}

}
