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
	String aid = request.getParameter("aid");
	if(aid == null || aid == "")
	{
		response.sendError(HttpURLConnection.HTTP_BAD_REQUEST, "Invaild Session");
		return;
	}
	int assignmentID = Integer.parseInt(aid);
	Assignment ob = ua.getAssignment(assignmentID);
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");	
	String link = "/Autograder/RunAssignmentServlet?aid="+assignmentID;//+"&nclass="+ob.getNclass();
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Upload Assignment</title>
<link rel="stylesheet" href="css/style2.css"/>
</head>
<body>
<ul>
<li><a href="Student.jsp">Home</a></li>
  <li><a href="StudentViewGrade.jsp">View Feedback</a></li>
  <li id="logout-button"><a href="/Autograder/LogoutServlet">Logout</a></li>
  </ul>
  <br/>
  
<div class="form">       
      <div class="tab-content">   
        <div id="login">
        <form  method="post" enctype="multipart/form-data" action="<%=link %>" >
           
          <h1> Upload your assignment here!</h1>
          <%-- <label class="lbldate" style="color:white">Number of Classes : </label><input  name="nclass" type="number" value="<%=ob.getNclass()%>" disabled="disabled"/> --%>
            <div class="txtDiv">
            
            <div >
            <span>Question</span>
           <textarea class="txtarea-que" rows="5" cols="50" disabled="disabled"><%= ob.getQues() %></textarea>
            </div>
            
            <div >
            <span>Description</span>
            <textarea class="txtarea-que-desc" rows="8" cols="50" disabled="disabled"><%= ob.getDescription() %></textarea>
            </div>
            </div>
     		
             <div class="part2">
            <input class="upload-file" name="solutionFile" type="file" accept=".zip" />
            <h5 style=" color: white; " align="center"><i>(Note** -> The zip file should contain *.java file)</i></h5>
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