<%@page import="dto.ViewGrade"%>
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
	if(uname == null)
		response.sendError(HttpURLConnection.HTTP_BAD_REQUEST, "Invaild Session");
	ArrayList<ViewGrade> list = ua.getStudentAssignment(uname);
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="css/style2.css"/>
<title>View Grade</title>
</head>
<body>
<ul>
<li><a href="Student.jsp">Home</a></li>
  <li><a href="StudentViewGrade.jsp">View Feedback</a></li>
  <li id="logout-button"><a href="/Autograder/LogoutServlet">Logout</a></li>
  </ul>
  <br/>
  
  <h1>Grade Table</h1>
  <div class="tbl-header">
    <table cellpadding="0" cellspacing="0" border="0">
      <thead>
        <tr>
          <th class="sn">Sno</th>
          <th class="sn">Assignment</th>
          <th class="desc">Description</th>
          <th >Status</th>
          <th >Rating</th>
          <th >Date of submission</th>
        </tr>
      </thead>
    </table>
  </div>


<div class="tbl-content">
    <table cellpadding="0" cellspacing="0" border="0">
      <tbody>
      <%
        int i = 1;
      	for(ViewGrade ob : list)
      	{
      		int aid = ob.getAssignmentid();
      		String link = "MyFeedback.jsp?aid="+aid;
      		String ques =  ob.getQues();
      		if(ques.length() > 85)
      			ques = ques.substring(0, 85)+"...";
      		
      %>
        <tr>
          <td class="sn"><%= i++ %></td>
          <td class="sn"><a href="<%=link%>">Assignment_<%= aid %></a></td>
          <td class="desc"><%=ques%></td>
          <td ><%= ob.getStatus() %></td>
          <%
          	if(ob.getRating() != null && ob.getDateOfSub() != null)
          	{
          %>
          <td ><%= ob.getRating() %></td>
          <td><%= sdf.format(ob.getDateOfSub()) %></td>
          <%
          	}
          	else
          	{
          %>
          <td>NA</td>
          <td>NA</td>
        </tr>
       <%
          }	 
      	}
       %> 
      </tbody>
    </table>
  </div>
</body>
</html>