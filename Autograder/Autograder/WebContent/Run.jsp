<%@page import="comp.InstanceOutput"%>
<%@page import="java.util.ArrayList"%>
<%@page import="comp.Output"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%
 	Output runoutput =  (Output)request.getAttribute("runoutput");
 	ArrayList<InstanceOutput> outputList = runoutput.getOutputlist();
 	String grade = runoutput.getGrades();
 	System.out.println(grade);
 	
 	session.setAttribute("runoutput", runoutput);
	session.setAttribute("aid",(String)request.getAttribute("aid"));
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
 		<%
 		   int i = 0;
 			for(InstanceOutput output : outputList)
 		  	{
 				if(i >= 2)
 					break;
 		%>
     		<div >
            <textarea class="txtarea-que-desc" rows="8" cols="50" disabled="disabled"><%= output %></textarea>
            </div>
        <%
 		  }
        %>    
            <div>	
			<input class="style-button-submit"onmouseover="style-button:hover" type="submit" autocomplete="off" value="submit"/>
          	</div>
 	</form>
</div>
</body>
</html>