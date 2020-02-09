# Autograder-System

> Automating grading and feedback tool which automatically, compiles and executes the source code and provides a feedback associated with the grades available to both the professor and the student.

### List of Features -
-	User can login into application based on their role
-	Instructor can upload and post an assignment and upload test cases related to an assignment.
-	Student can view the posted assignment on their dashboard 
-	Student can upload the file which contains all the files of the solution 
-	Instructor can grade the uploaded solution after the due date 
-	Student can view the grade and feedback of the particular assignment

1. Student:
  -	View the uploaded assignments
  -	Submit the code in zip file
  -	View feedback of the graded assignments
2. Instructor:
  -	Upload Assignments
  -	Upload Test cases
  -	Grade Assignment
  
**Below are the requirements of the system:**
-	Unzip the Zipped files
-	Compile the assignment code
-	Test the assignment code using different test cases
-	Program conventional analysis
-	Grading assignments by taking multiple factors into account
-	Store the results in database
-	Generate feedback

## Application Features and Usage Process:

### Admin -
1. In the login page, the user (student or instructor) needs to enter the required email address and password and then click ‘login’ button to advance to the assignment table page.
2. User needs to enter the email address and password in the respective fields.
3. If the user enters an invalid email address or a password, then the application displays ‘invalid username or password’.
4. By entering the instructor’s email address and password, the page will be navigated to the instructor’s assignment table page.
5. The assignment table page consists of a table where the instructor can view the detail description of each assignment (SNO, assignment name, description, date of upload, date of submission). The assignment table also has the following buttons:
  - **_Home_**:  By clicking on the home button, the instructor can navigate to the home page.
  - **_Add Assignment_**: By clicking on the add assignment button, the instructor can add the assignment.
  - **_View Feedback_**: By clicking on the view feedback button, the instructor can view the assignment’s feedback.
  - **_Grade Assignment_**: By clicking on the grade assignment button, the instructor can grade the assignment.
  - **_Logout_**: The page also has a logout button that helps the instructor to logout from the application.
6. In the add assignment page, the instructor can create the assignment and write the description for the assignment. The instructor can also set the date of submission for the assignment.
7. The add assignment’s page also has the test cases and the expected section where the user can upload the test case file and the expected output file. The page also has the following buttons:
  - **_Upload_**: By clicking on the upload button, the instructor can upload both the test case file and the expected output file.
  - **_Clear all_**: By clicking on the clear all button, all the fields in the page will be cleared so that the instructor can   make the necessary changes.
8. In instructor page, instructors can able to see all assignments on dashboard
9. Instructor should get “Grade all” button option, whenever assignment submission date is exceeded.
10. After clicking Grade button on instructor page, Grades can see on instructor page and student page. 
11. 

### Student -
1. In the login page, the student needs to enter the required email address and password and then click ‘login’ button to advance to the assignment table page.
2. The assignment table page consists of a table where the student can see the detail description of the assignment (SNO, Assignment, question, instructor name and date of submission). The page also consists of the following buttons:
  - **_Home_**:  By clicking on the home button, the instructor can navigate to the home page.
  - **_View Feedback_**: By clicking on the view feedback button, the student can view the assignment’s feedback.
  - **_Logout_**: The page also has a logout button that helps the student to logout from the application.
3. After clicking on the assignment, the student is navigated to the submission page where he/she can select the assignment file by clicking on the choose file button. 
4. To upload the selected file, the student needs to click on the submit button.
5. Users (i.e. Student) must login with their credentials in order to view the grades of the Graded Assignment and feedback of the Graded assignment.
6. User (i.e. Student) after logging in the Graded assignment will no more be on the Assignment Table Dashboard (after they completed the assignment).
7. User (i.e. Student) navigates to Grade table to view the Graded Assignments.
8. User (i.e. Student) navigates to Feedback page of the Assignment and will be able to view the feedback of the Assignments which gives Lines of code, Class count, Comment Count and Status of the test cases.
9. User (i.e. Student) navigates to Feedback page of the Assignment and will be able to view the feedback of the Assignments which gives Lines of code, Class count, Comment Count and Status of the test cases.
