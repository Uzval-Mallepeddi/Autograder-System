<%@page import="java.net.HttpURLConnection"%>
<%@page import="dao.UserAccess"%>
<%@page import="comp.InstanceOutput"%>
<%@page import="java.util.ArrayList"%>
<%@page import="comp.Output"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%
 	String assignmentID = request.getParameter("aid"); 
 	UserAccess ua = new UserAccess();
	String uname = (String)session.getAttribute("uname");
	if(uname == null)
		response.sendError(HttpURLConnection.HTTP_BAD_REQUEST, "Invaild Session");
	
	String feedback = ua.getFeedback(Integer.parseInt(assignmentID),uname);
 %>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Output</title>
<link rel="stylesheet" href="css/style2.css" />
</head>
<body>
  <ul>
  <li><a href="Student.jsp">Home</a></li>
  <li id="logout-button"><a href="/Autograder/LogoutServlet">Logout</a></li>
  </ul>
  <br/>
  
<div class="form">
	 <form action="/Autograder/SubmitServlet">
 		<h1> Output</h1>
 		<div >
            <textarea class="txtarea-que-desc" rows="20" cols="50" disabled="disabled"><%= feedback %></textarea>
        </div>
 	</form>
</div>
</body>
</html>