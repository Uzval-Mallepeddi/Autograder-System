<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String msg = ((request.getAttribute("msg")) != null) ? request.getAttribute("msg").toString() : null;
%>
<!DOCTYPE html>
<html >
<head>
  <meta charset="UTF-8">
  <title>Login Form</title>
  <link href='https://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
  <link rel="stylesheet" href="css/style.css">
  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>


<style>
	  /* demo styles */
@import url(https://fonts.googleapis.com/css?family=Roboto:400,500,300,700);
body{
  background: -webkit-linear-gradient(left, #25c481, #25b7c4);
  background: linear-gradient(to right, #25c481, #25b7c4);
  font-family: 'Roboto', sans-serif;
}
section{
  margin: 50px;
}
 </style>
</head>

<body>
  <div class="form">
           
      <div class="tab-content">   
      
        <div id="login">   
          <h1>Autograder</h1>
          
          <form action="/Autograder/LoginController" method="post">
          
            <div class="field-wrap">
           <label>
              Email Address<span class="req">*</span>
            </label>
            <input type="email"required autocomplete="off" name="uname"/>
          </div>
          
          <div class="field-wrap">
            <label>
              Password<span class="req">*</span>
            </label> 
            <input type="password" required autocomplete="off"  name="pass"/>
          </div>
          <%
          	if(msg != null)
          	{
          %>
          <h5 style="color: orange;"><%= msg %></h5>       
          <%
          }
          %>   
          <button class="button button-block" type="submit">Log In</button>
          
          </form>

        </div>
        
      </div><!-- tab-content -->
      
</div> <!-- /form -->

  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
    <script   type="text/javascript" src="js/index.js"></script>

</body>
</html>