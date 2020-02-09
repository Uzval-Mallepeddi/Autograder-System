<%@page import="dto.StudentAssignment"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="dto.Assignment"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
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
	Assignment ob = ua.getAssignment(assignmentID);
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");	
	String link = "/Autograder/EditAssignmentServlet";    //+"&nclass="+ob.getNclass();
	
	Date dt = new Date();
	Calendar c = Calendar.getInstance(); 
	c.setTime(dt); 
	c.add(Calendar.DATE, 1);
	dt = c.getTime();
	String minDate = (dt.getYear()+1900)+"-"+(dt.getMonth()+1)+"-"+dt.getDate();
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Upload Assignment</title>
<link rel="stylesheet" href="css/style2.css"/>
<link type="text/css" rel="stylesheet" href="css/instructor_style.css"/>
</head>
<body>
<ul>
<li><a href="Instructor.jsp">Home</a></li>
  <li><a href="StudentViewGrade.jsp">View Feedback</a></li>
  <li id="logout-button"><a href="/Autograder/LogoutServlet">Logout</a></li>
  </ul>
  <br/>
  
<div class="form">       
      <div class="tab-content">   
        <div id="login">
        <form action="<%=link %>" >
            <div class="txtDiv">
            <input type="hidden" name="aid" value="<%= assignmentID%>">
            <input class="input-date"  name="dateOfSub" type="date" min="<%= minDate %>" value="<%= ob.getDateOfSub()%>"/><label class="lbldate" for="date">Date of Submission : </label>
            <div >
            <span>Question</span>
            <textarea class="txtarea-que" rows="5" cols="50" name="ques"><%= ob.getQues() %></textarea>
            </div>
            
            <div >
            <span>Description</span>
            <textarea class="txtarea-que-desc" rows="8" cols="50" name="description"><%= ob.getDescription() %></textarea>
            </div>
            </div>
     		
            <div>	
			<input class="style-button-submit" onmouseover="style-button:hover" type="submit" autocomplete="off" value="submit"/>
          </div> 
          </form>
        </div>
      </div> 
</div>       
</body>
</html>