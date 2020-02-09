<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dto.Assignment"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.UserAccess"%>
<%@page import="java.net.HttpURLConnection" %>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	UserAccess ua = new UserAccess();
	String uname = (String)session.getAttribute("uname");
	if(uname == null)
		response.sendError(HttpURLConnection.HTTP_BAD_REQUEST, "Invaild Session");
	ArrayList<Assignment> list = ua.getToGradeAssignments(uname);
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
  <li><a href="SelectToGrade.jsp">Grade Assignment</a></li>
  <li id="logout-button"><a href="/Autograder/LogoutServlet">Logout</a></li>
</ul>
<br/>
  
  <h1>Assignment Table</h1>
  <div class="tbl-header">
    <table cellpadding="0" cellspacing="0" border="0">
      <thead>
        <tr>
          <th class="sn">Sno</th>
          <th class="sn">Assignment</th>
          <th class="desc">Description</th>
          <th >Date of Upload</th>
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
		
      	for(Assignment ob : list)
      	{
      		int aid = ob.getAssignmentId();
      		String link = "GradeAssignment.jsp?aid="+aid;
      		String ques=ob.getQues();
      		if(ques.length() > 85)
      			ques = ques.substring(0, 85)+"...";
      %>
        <tr>
          <td class="sn"><%= "Test" %></td>
          <td class="sn"><a href="<%= link%>">Assignment_<%= aid %></a> </td>
          <td class="desc"><%=ques%></td>
          <td ><%= sdf.format(ob.getDateOfUpload()) %></td>
          <td ><%= sdf.format(ob.getDateOfSub()) %></td>
        </tr> 
       <%
      	}
       %> 
      </tbody>
    </table>
  </div>

</body>
</html>