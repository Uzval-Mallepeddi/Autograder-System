<%@page import="java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
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
<title>Documents List</title>
<link type="text/css" rel="stylesheet" href="css/instructor_style.css"/>
</head>
<body onload="getTime(event)">
<ul>
  <li><a href="Instructor.jsp">Home</a></li>
  <li><a href="AddAssignment">Add Documents</a></li>
  <li><a href="ViewGrade.jsp">View Feedback</a></li>
  <li id="logout-button"><a href="/Autograder/LogoutServlet">Logout</a></li>
  </ul>

<div class="form">

<div>
<h1>Documents List</h1>
</div>

<form id="form" method="post" action="/Autograder/AddAssignmentServlet" enctype="multipart/form-data">
		 <div class="txtDiv">
            <div>
            
            
          <!--   <input class="input-date" name="nclass" type="number" min="1"  step="1" value ="1"/><label class="lbldate" >Number of Classes : </label> -->            
            <input class="input-date"  name="dateOfSub" type="date" min="<%= minDate %>" /><label class="lbldate" for="date">Date of Submission : </label>
            <span class="span_que">Question</span>             
			<textarea class="txtarea-que" rows="7" cols="55" name="ques"></textarea>
            </div>
            
            <div >
            <span class="span_que">Description</span>
            <textarea class="txtarea-que-desc" rows="12" cols="50" name="description"></textarea>
            </div>
        </div>

	<div class="div_ass">
     <span class="span_testcase">Test Cases</span><br/>
     <input class="upload-file" name="testcase" type="file" accept=".zip"/>
     <h5 style=" color: white; " align="center"><i>(Note** -> The zip file should contain *.txt file)</i></h5>
    </div>
    
    <div class="div_ass">
     <span class="span_testcase">Expected Output</span><br/>
     <input class="upload-file" name="exOutput" type="file" accept=".zip"/>
     <h5 style=" color: white; " align="center"><i>(Note** -> The zip file should contain *.txt file)</i></h5>
    </div>
    
    <div class="div_submit">
    <input class="style-button-submit" type="submit" value="Upload" autocomplete="off"/>
    <input class="style-button-reset" type="reset" value="Clear all">
    </div>
  </form>
</div>

</body>
</html>