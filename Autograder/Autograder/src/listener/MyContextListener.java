package listener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import controllers.RunAssignmentServlet;
import db.MyConnection;

public class MyContextListener implements ServletContextListener 
{
	@Override
	public void contextInitialized(ServletContextEvent sce) 
	{
		System.out.println("Context Listner Entering");
		installDB();
		createMap();
		System.out.println("Context Listner Leaving");		
	}
	
	private void installDB()
	{
		Connection myconn = new MyConnection().getMyConnection();
		System.out.println("Connection established with DB");
		PreparedStatement stmt = null;
		PreparedStatement stmt1 = null;
		try {
			stmt = myconn.prepareStatement("CREATE TABLE `Assignment` ( "+
											  "`uname` varchar(45) NOT NULL,"+
											  "`assignmentID` int(11) NOT NULL,"+
											  "`ques` text NOT NULL,"+
											  "`description` mediumtext,"+
											  "`dateOfUpload` date NOT NULL,"+
											  "`testFile` blob NOT NULL,"+
											  "`dateOfSub` date NOT NULL,"+
											  "`exOutput` blob NOT NULL,"+
											  " `nclass` int(11),"+
											  "PRIMARY KEY (`uname`,`assignmentID`)"+
											") ");
			stmt.executeUpdate();
			
			stmt1 = myconn.prepareStatement("CREATE TABLE `Assignment_Temp` ( "+
					  "`uname` varchar(45) NOT NULL,"+
					  "`assignmentID` int(11) NOT NULL,"+
					  "`ques` text NOT NULL,"+
					  "`description` mediumtext,"+
					  "`dateOfUpload` date NOT NULL,"+
					  "`testFile` blob NOT NULL,"+
					  "`dateOfSub` date NOT NULL,"+
					  "`exOutput` blob NOT NULL,"+
					  " `nclass` int(11),"+
					  "PRIMARY KEY (`uname`,`assignmentID`)"+
					") ");
			stmt1.executeUpdate();
		}
		catch(SQLException e)
		{
				e.printStackTrace();
	 	}
		
		
	  	try
		{
			stmt = myconn.prepareStatement("CREATE TABLE `Student` ("+
											  "`uname` varchar(45) NOT NULL,"+
											  "`assignmentId` int(11) NOT NULL,"+
											  "`status` varchar(45) DEFAULT NULL,"+
											  "`dateOfSub` varchar(45) DEFAULT NULL,"+
											  "`rating` varchar(45) DEFAULT NULL,"+
											  "`solutionFile` blob,"+
											  "PRIMARY KEY (`uname`,`assignmentId`)"+
											")");
			
			
			stmt.executeUpdate();
			
			stmt1 = myconn.prepareStatement("CREATE TABLE `Student_Temp` ("+
					  "`uname` varchar(45) NOT NULL,"+
					  "`assignmentId` int(11) NOT NULL,"+
					  "`status` varchar(45) DEFAULT NULL,"+
					  "`dateOfSub` varchar(45) DEFAULT NULL,"+
					  "`rating` varchar(45) DEFAULT NULL,"+
					  "`feedback` blob DEFAULT NULL,"+
					  "`solutionFile` blob,"+
					  "PRIMARY KEY (`uname`,`assignmentId`)"+
					")");


			stmt1.executeUpdate();
		}
		catch(SQLException e)
		{
				e.printStackTrace();
	 	}
	  	
	  	
	  	try
		{	
			stmt = myconn.prepareStatement("CREATE TABLE `User` ("+
											  "`uname` varchar(45) NOT NULL,"+
											  "`pass` varchar(45) NOT NULL,"+
											  "`name` varchar(45) NOT NULL,"+
											  "`type` varchar(45) NOT NULL,"+
											  "PRIMARY KEY (`uname`)"+
											")");
			
			stmt.executeUpdate();
		}
		catch(SQLException e)
		{
				e.printStackTrace();
	 	}
	  	
	  	
	  	try
		{	
			stmt = myconn.prepareStatement("INSERT INTO `Autograder`.`User`(`uname`,`pass`,`name`,`type`)VALUES('admin@gmail.com','111','Admin','Instructor');");

			stmt.executeUpdate();
		}
		catch(SQLException e)
		{
				e.printStackTrace();
	 	}
	  	
	  	
	  	try
		{	
			stmt = myconn.prepareStatement("INSERT INTO `Autograder`.`User`(`uname`,`pass`,`name`,`type`)VALUES('Student@gmail.com','123','Student','Student');");

			stmt.executeUpdate();		
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private void createMap()
	{
		System.out.println("Creating Rubic file");
		File root = new File(RunAssignmentServlet.HOME);
		
		File rubicPath = new File(root,"Autograder"+File.separator+"Rubic");
	
		if(!rubicPath.exists())
			rubicPath.mkdirs();
		
		HashMap<String, Integer> map = new HashMap<>();
		
		map.put(new ArithmeticException().toString(), -2);
		map.put(new NullPointerException("").toString(), -3);
		map.put(new IllegalArgumentException("").toString(),-3);
		map.put(new ClassCastException().toString(), -2);
		map.put(new ArrayIndexOutOfBoundsException().toString(),-3);
	
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rubicPath.getAbsolutePath()+File.separator + "grademap.rub"));
			out.writeObject(map);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Rubic file created sucessfully");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
