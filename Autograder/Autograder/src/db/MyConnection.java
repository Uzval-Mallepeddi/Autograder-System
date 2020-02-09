package db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import controllers.RunAssignmentServlet;
import dto.User;

public class MyConnection 
{
	public Connection getMyConnection()
	{
		try
		{  
			Class.forName("com.mysql.jdbc.Driver");  
			System.out.println("Driver Loaded");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/Autograder","root","uzval@123");
			System.out.println("Connection created in MyConn.java");
			System.out.println("jdbc:mysql://localhost:3306/Autograder");
			System.out.println(con);
			return con;
		}
		catch(Exception e)
		{ System.out.println(e);}

		return null;
	}

	@Override
	public String toString() {
		return "mysql://localhost:3306/Autograder";
	}

	public static void main(String args[]) throws SQLException
	{
		MyConnection myconn = new MyConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user = null;

		//		try {
		//			stmt = myconn.getMyConnecton().prepareStatement("SELECT * FROM User WHERE uname = ? AND pass = ?");
		//			stmt.setString(1, "Student@gmail.com");
		//			stmt.setString(2, "123");
		//			
		//			rs  = stmt.executeQuery();
		//			rs.next();
		//			user = new User(rs.getString("uname"),rs.getString("name"),rs.getString("pass"),rs.getString("type"));
		//			rs.close();
		//		} catch (SQLException e) {
		//			e.printStackTrace();
		//		}
		//		finally
		//		{			
		//			stmt.close();
		//			
		//		}


		//		System.out.println(c.getMyConnecton());

		try {
			stmt = myconn.getMyConnection().prepareStatement("Insert into Assignment (uname,assignmentId,ques,description,dateOfUpload,testfile");
			stmt.setString(1, "Student@gmail.com");
			stmt.setString(2, "123");
			

			rs  = stmt.executeQuery();
			rs.next();
			user = new User(rs.getString("uname"),rs.getString("name"),rs.getString("pass"),rs.getString("type"));
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{			
			stmt.close();

		}
	}

	public String downlaodTestFile(int assignmentId) throws SQLException
	{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Blob b = null;
		Blob botp = null;

		File assignmentFolder = new File(RunAssignmentServlet.HOME+File.separator+"Autograder"+File.separator+"Assignments"+File.separator+assignmentId);
		Connection con = getMyConnection();;
		if(!assignmentFolder.exists())
			assignmentFolder.mkdirs();

		String testFile = assignmentFolder.getAbsolutePath()+File.separator+"Testcase.zip";
		String exOutputFile = assignmentFolder.getAbsolutePath()+File.separator+"ExOutput.zip";
		try {

			stmt = con.prepareStatement("SELECT testFile,exOutput FROM Assignment where assignmentID = ?");
			stmt.setInt(1, assignmentId);

			rs  = stmt.executeQuery();
			if(rs.next())
			{

				b = rs.getBlob(1);
				botp = rs.getBlob(2);

				FileOutputStream out = new FileOutputStream(testFile);
				FileOutputStream eoutput = new FileOutputStream(exOutputFile);

				byte data[] = new byte[1024];
				long length = b.length();
				int pos = 1;
				while(pos < length)
				{
					if((length - pos) >= 1024)
					{
						data = b.getBytes(pos, 1024);
						pos += 1024;
						out.write(data);
					}
					else
					{
						int diff = (int)(length - pos + 1 );
						data = b.getBytes(pos, diff);
						pos += diff;
						out.write(data);
					}
				}
				out.close();;

				//				eoutput = new FileOutputStream(exOutputFile);
				pos = 1;
				length = botp.length();
				while(pos < length)
				{
					if((length - pos) >= 1024)
					{
						data = botp.getBytes(pos, 1024);
						pos += 1024;
						eoutput.write(data);
					}
					else
					{
						int diff = (int)(length - pos + 1 );
						data = botp.getBytes(pos, diff);
						pos += diff;
						eoutput.write(data);
					}
				}

				eoutput.close();
				out.close();
			}
			else
				return null;

			rs.close();

			return assignmentFolder.getAbsolutePath();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		finally
		{			
			stmt.close();
			con.close();
		}
	}
}
