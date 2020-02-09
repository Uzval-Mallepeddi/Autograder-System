<%@page import="dto.StudentAssignment"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dto.Assignment"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.UserAccess"%>
<%@page import="java.net.HttpURLConnection" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	UserAccess ua = new UserAccess();
	String uname = (String)session.getAttribute("uname");
	session.removeAttribute("runoutput");
	session.removeAttribute("aid");
	if(uname == null)
		response.sendError(HttpURLConnection.HTTP_BAD_REQUEST, "Invaild Session");
	ArrayList<StudentAssignment> list = ua.getMyAssignments(uname);
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Student Home</title>
<link rel="stylesheet" href="css/style2.css"/>
</head>
<body>
<ul>
<li><a href="Student.jsp">Home</a></li>
  <li><a href="StudentViewGrade.jsp">View Feedback</a></li>
  <li id="logout-button"><a href="/Autograder/LogoutServlet">Logout</a></li>
  </ul>
  <br/>
  
<section>
  <h1>Assignment Table</h1>
  <div class="tbl-header">
    <table cellpadding="0" cellspacing="0" border="0">
      <thead>
        <tr>
          <th>Sno</th>
          <th>Assignment</th>
          <th class="desc">Question</th>
          <th>Instructor</th>
          <th>Date of submission</th>
        </tr>
      </thead>
    </table>
  </div>
  <div class="tbl-content">
    <table cellpadding="0" cellspacing="0" border="0">
      <tbody>
      	<%
      		int i = 1;
      		for(StudentAssignment a : list)
      		{
      			int aid = a.getAssignmentId();
      			String link = "RunAssignment.jsp?aid="+aid;
      			String ques= a.getQues();
          		if(ques.length() > 85)
          			ques = ques.substring(0, 85)+"...";
      	%>
        <tr>
          <td><%=i++ %></td>
          <td><a href="<%= link%>">Assignment_<%= aid %></a></td>
          <td class="desc"><%= ques %></td>
          <td><%= a.getInstructor() %></td>
          <td><%= sdf.format(a.getDateOfSub()) %></td>
        </tr>
        <%
      		}
        %>
      </tbody>
	    </table>
  </div>
</section>

</body>
</html>