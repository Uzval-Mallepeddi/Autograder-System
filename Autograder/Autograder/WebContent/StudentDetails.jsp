<%@page import="dto.StudentReport"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dto.Assignment"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.UserAccess"%>
<%@page import="java.net.HttpURLConnection" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	UserAccess ua = new UserAccess();
	String aid = request.getParameter("aid");
	if(aid == null || aid == "")
	{
		response.sendError(HttpURLConnection.HTTP_BAD_REQUEST, "Invaild Session");
		return;
	}
	int assignmentID = Integer.parseInt(aid);
	ArrayList<StudentReport> list = ua.getStudentReports(assignmentID);
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
%>    
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Home</title>
<link rel="stylesheet" href="css/style2.css"/>
</head>
<body>
<ul>
<li><a href="Instructor.jsp">Home</a></li>
  <li><a href="Add_assignment.jsp">Add Assignment</a></li>
  <li><a href="ViewGrade.jsp">View Feedback</a></li>
  <li id="logout-button"><a href="/Autograder/LogoutServlet">Logout</a></li>
</ul>
<br/>
  
  <h1>Assignment Table</h1>
  <div class="tbl-header">
    <table cellpadding="0" cellspacing="0" border="0">
      <thead>
        <tr>
          <th>Sno</th>
          <th>Name</th>
          <th>Description</th>
          <th>Status</th>
          <th>Submission Date</th>
          <th>Grade</th>
        </tr>
      </thead>
    </table>
  </div>
  <div class="tbl-content">
    <table cellpadding="0" cellspacing="0" border="0">
      <tbody>
      <%
        int i = 1;
      	for(StudentReport ob : list)
      	{
      %>
        <tr>
          <td ><%= i++ %></td>
          <td ><%= ob.getName() %></td>
          <td ><%= ob.getUname()%></td>
          <td ><%= ob.getStatus() %></td>
          <%
          	if(ob.getDateOfUpload() != null)
          	{
          %>
          <td align="center"><%= sdf.format(ob.getDateOfUpload()) %></td>
          <% } else
             {
          %>
          <td align="center">-</td>
          <% } %>
          <td align="center"><%= ob.getRating()%></td>
        </tr> 
       <%
      	}
       %> 
      </tbody>
    </table>
  </div>

</body>
</html>