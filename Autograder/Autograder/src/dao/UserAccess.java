package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import comp.InstanceOutput;
import comp.Output;
import db.MyConnection;
import dto.Assignment;
import dto.StudentAssignment;
import dto.StudentReport;
import dto.User;
import dto.ViewGrade;

public class UserAccess 
{
	public User getUser(String uname, String pass) throws SQLException
	{
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user = null;
		
		try {
			stmt = myconn.prepareStatement("SELECT * FROM User WHERE uname = ? AND pass = ?");
			stmt.setString(1, uname);
			stmt.setString(2, pass);
			
			rs  = stmt.executeQuery();
			if(rs.next())
				user = new User(rs.getString("uname"),rs.getString("name"),rs.getString("pass"),rs.getString("type"));
			else
				return null;
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{			
			stmt.close();
			myconn.close();
		}	
		
		return user;
	}
	
	public String getUserType(String uname) throws SQLException
	{
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String userType = null;
		
		try {
			stmt = myconn.prepareStatement("SELECT * FROM User WHERE uname = ? ");
			stmt.setString(1, uname);
			
			rs  = stmt.executeQuery();
			
			if(rs.next())
				userType = rs.getString("type");
						
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{			
			stmt.close();
			myconn.close();
		}	
		
		return userType;
	}
	
	public boolean addAssignment(Assignment ob) throws SQLException
	{
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null,stmt2 = null, stmt3 = null;
		int i = -1, j=0;;		
		int assignmentId = ob.getAssignmentId();
		try {
			stmt = myconn.prepareStatement("INSERT INTO Assignment (uname,assignmentId,ques,description,dateOfUpload,testfile,dateOfSub,exOutput,nclass) VALUES (?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, ob.getUname());
			stmt.setInt(2, assignmentId);
			stmt.setString(3, ob.getQues());
			stmt.setString(4, ob.getDescription());
			stmt.setDate(5, ob.getDateOfUpload());
			stmt.setBlob(6, ob.getTestFile());
			stmt.setDate(7, ob.getDateOfSub());
			stmt.setBlob(8, ob.getExOutputFile());
			stmt.setInt(9,ob.getNclass());
			
			stmt3 = myconn.prepareStatement("INSERT INTO Assignment_Temp (uname,assignmentId,ques,description,dateOfUpload,testfile,dateOfSub,exOutput,nclass) VALUES (?,?,?,?,?,?,?,?,?)");
			stmt3.setString(1, ob.getUname());
			stmt3.setInt(2, assignmentId);
			stmt3.setString(3, ob.getQues());
			stmt3.setString(4, ob.getDescription());
			stmt3.setDate(5, ob.getDateOfUpload());
			stmt3.setBlob(6, ob.getTestFile());
			stmt3.setDate(7, ob.getDateOfSub());
			stmt3.setBlob(8, ob.getExOutputFile());
			stmt3.setInt(9,ob.getNclass());
			
			ArrayList<String> stdList = getAllStudent();
			if(!stdList.isEmpty())
			{
				i = stmt.executeUpdate();
				i = stmt3.executeUpdate();
				
				myconn.setAutoCommit(false);
				
				stmt = myconn.prepareStatement("INSERT INTO Student (uname,assignmentId,status) VALUES (?,?,?)");
				stmt2 = myconn.prepareStatement("INSERT INTO Student_Temp (uname,assignmentId,status) VALUES (?,?,?)");
				
				for(String st : stdList)
				{
					j++;
					
					stmt.setString(1,st);
					stmt.setInt(2, assignmentId);
					stmt.setString(3, "NA");
					
					stmt2.setString(1,st);
					stmt2.setInt(2, assignmentId);
					stmt2.setString(3, "NA");
					
					stmt.addBatch();
					stmt2.addBatch();
					
					if(j % 1000 == 0 || j == stdList.size())
					{
						int result[] = stmt.executeBatch();
						int result2[] = stmt2.executeBatch();
						
						myconn.commit();
						for (int k = 0; k < result.length; k++) {
							System.out.println(result[k]);
						}
						
					}
				}
				stmt.close();
				stmt2.close();
				stmt3.close();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally
		{			
			
			myconn.close();
		}	
		
		if(i > 0)
			return true;
		
		return false;
	}
	
	public boolean editAssignment(Assignment ob) throws SQLException
	{
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		int i = -1;		
		
		try {
			stmt = myconn.prepareStatement("UPDATE Assignment SET ques = ?,description = ? , dateOfSub = ? WHERE assignmentId = ?");
			stmt.setString(1, ob.getQues());
			stmt.setString(2, ob.getDescription());
			stmt.setDate(3, ob.getDateOfSub());
			stmt.setInt(4, ob.getAssignmentId());			
			i = stmt.executeUpdate();
			
			if(i <= 0)
				return false;
			
			stmt = myconn.prepareStatement("UPDATE Assignment_Temp SET ques = ?,description = ? , dateOfSub = ? WHERE assignmentId = ? ");
			stmt.setString(1, ob.getQues());
			stmt.setString(2, ob.getDescription());
			stmt.setDate(3, ob.getDateOfSub());
			stmt.setInt(4, ob.getAssignmentId());
			
			i = stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally
		{			
			stmt.close();
			myconn.close();
		}	
		
		if(i > 0)
			return true;
		
		return false;
	}
	
	public ArrayList<String> getAllStudent()
	{
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		ArrayList<String> list = new ArrayList<>();
		int i = -1;		
		try {
			stmt = myconn.prepareStatement("SELECT * FROM User WHERE type = ?");
			stmt.setString(1, "Student");
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				list.add(rs.getString("uname"));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<Assignment> getAllAssignments(String uname) throws SQLException
	{
		return getAllAssignments(uname,false);
	}
	
	public ArrayList<Assignment> getAllAssignments(String uname, boolean isExpired ) throws SQLException
	{
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		Date today = new Date(System.currentTimeMillis());
		ResultSet rs = null;
		ArrayList<Assignment> list = new ArrayList<>();
				
		try {
			if(isExpired)
			{
					stmt = myconn.prepareStatement("SELECT * FROM Assignment_Temp WHERE uname = ?  ORDER BY dateOfSub DESC	");
					stmt.setString(1, uname);
			}
			else
			{
				stmt = myconn.prepareStatement("SELECT * FROM Assignment WHERE uname = ? AND dateOfSub > ? ORDER BY dateOfSub ASC	");
				stmt.setString(1, uname);
				stmt.setDate(2, today);
			}
			
			
			rs = stmt.executeQuery();
			
			while(rs.next())
			{
				Assignment temp = new Assignment(rs.getString("uname"), rs.getInt("assignmentID"), rs.getString("ques"), 
														rs.getString("description"),rs.getDate("dateOfUpload"), rs.getDate("dateOfSub"),rs.getInt("nclass"));
				list.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally
		{			
			stmt.close();
			myconn.close();
		}	
		
		return list;
	}
	
	public ArrayList<Assignment> getGradedAssignments(String uname) throws SQLException
	{
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		Date today = new Date(System.currentTimeMillis());
		ResultSet rs = null;
		ArrayList<Assignment> list = new ArrayList<>();
				
		try {
				stmt = myconn.prepareStatement("SELECT * FROM Assignment WHERE uname = ?  ORDER BY dateOfSub < ? DESC	");
				stmt.setString(1, uname);
				stmt.setDate(2, today);
			
			rs = stmt.executeQuery();
			
			while(rs.next())
			{
				Assignment temp = new Assignment(rs.getString("uname"), rs.getInt("assignmentID"), rs.getString("ques"), 
														rs.getString("description"),rs.getDate("dateOfUpload"), rs.getDate("dateOfSub"),rs.getInt("nclass"));
				list.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally
		{			
			stmt.close();
			myconn.close();
		}	
		
		return list;
	}
	
	public ArrayList<Assignment> getToGradeAssignments(String uname) throws SQLException
	{
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		Date today = new Date(System.currentTimeMillis());
		ResultSet rs = null;
		ArrayList<Assignment> list = new ArrayList<>();
				
		try {
				stmt = myconn.prepareStatement("SELECT * FROM Assignment_Temp WHERE uname = ? AND dateOfSub < ? ORDER BY dateOfSub ASC");
				stmt.setString(1, uname);
				stmt.setDate(2, today);
			
			rs = stmt.executeQuery();
			
			while(rs.next())
			{
				Assignment temp = new Assignment(rs.getString("uname"), rs.getInt("assignmentID"), rs.getString("ques"), 
														rs.getString("description"),rs.getDate("dateOfUpload"), rs.getDate("dateOfSub"),rs.getInt("nclass"));
				list.add(temp);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally
		{			
			stmt.close();
			myconn.close();
		}	
		
		return list;
	}
	
	public ArrayList<StudentReport> getStudentReports(int assignmentId)
	{
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		ArrayList<StudentReport> list = new ArrayList<>();
		boolean isNotYetGraded = false;
		try {
			stmt = myconn.prepareStatement("select * from Student_Temp where assignmentID = ?");
			stmt.setInt(1, assignmentId);
			
			ResultSet rs = stmt.executeQuery();
			if(rs.next())
				isNotYetGraded = true;
			
			if(isNotYetGraded)
			{
				stmt = myconn.prepareStatement("select User.uname,name,status,dateOfSub,solutionFile,rating from User,Student_Temp "
						+ "where Student_Temp.uname = User.uname  AND assignmentID = ?");
				stmt.setInt(1, assignmentId);
			
				rs = stmt.executeQuery();
			
				while(rs.next())
				{
//					String status = rs.getString("status");
					String rating = rs.getString("rating");
//					if(status.equals("NA"))
//						rating = "NA";
					StudentReport ob = new StudentReport(rs.getString("uname"),rs.getString("name"),rs.getString("status"),rs.getDate("dateOfSub"),
							rating,null);
					list.add(ob);
				}
			}
			else
			{
				stmt = myconn.prepareStatement("select User.uname,name,status,dateOfSub,solutionFile,rating from User,Student "
						   + "where Student.uname = User.uname  AND assignmentID = ?");
				stmt.setInt(1, assignmentId);
				
				rs = stmt.executeQuery();
				
				while(rs.next())
				{
					StudentReport ob = new StudentReport(rs.getString("uname"),rs.getString("name"),rs.getString("status"),rs.getDate("dateOfSub"),
							rs.getString("rating"),null);
					list.add(ob);
				}
			}
			stmt.close();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<StudentAssignment> getMyAssignments(String uname)
	{
		return getMyAssignments(uname, false);
	}
	
	public ArrayList<StudentAssignment> getMyAssignments(String uname, boolean isExpired)
	{
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		ArrayList<StudentAssignment> list = new ArrayList<>();		
		try {
			stmt = myconn.prepareStatement("select s.assignmentId,a.ques,u.name,a.dateOfSub" 
										+"	from "
										+"	User u," 
										+"	Assignment a,"
										+"	Student_Temp s "
										+"	where "
										+"	s.assignmentId = a.assignmentID" 
										+"	AND u.type = 'Instructor' "
										+"	AND u.uname=a.uname"
										+"	AND s.uname = ?"
										+"	AND a.dateOfSub >= ?");
			stmt.setString(1, uname);
			stmt.setDate(2, new Date(System.currentTimeMillis()));
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next())
			{
				StudentAssignment ob = new StudentAssignment(rs.getInt("assignmentId"), rs.getString("ques"), rs.getString("name"), rs.getDate("dateOfSub"));
				list.add(ob);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	public Assignment getAssignment(int assignmentId) throws SQLException
	{
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Assignment temp = null;
				
		try {
			stmt = myconn.prepareStatement("SELECT * FROM Assignment WHERE assignmentID = ?	");
			stmt.setInt(1, assignmentId);
						
			rs = stmt.executeQuery();
			
			if(rs.next())
			{
				temp = new Assignment(rs.getString("uname"), rs.getInt("assignmentID"), rs.getString("ques"), 
												rs.getString("description"),rs.getDate("dateOfUpload"), rs.getDate("dateOfSub"),
												rs.getBlob("testFile").getBinaryStream(),rs.getBlob("exOutput").getBinaryStream(),rs.getInt("nclass"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally
		{			
			stmt.close();
			myconn.close();
		}	
		
		return temp;
		
	}
	
	
	public  ArrayList<ViewGrade> getStudentAssignment(String uname){
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;   
		ArrayList<ViewGrade> list = new ArrayList<>();	
		
		try {
//			stmt = myconn.prepareStatement("Select Student.assignmentId, Assignment.ques, Student.rating, Student.dateOfSub "
//					+ "from Student,Assignment "
//					+ "where Student.assignmentId=Assignment.assignmentID and Student.uname=? and (Assignment.dateOfSub <= ? or Student.status = ?)");
			
			stmt = myconn.prepareStatement("Select Student.assignmentId, Assignment.ques, Student.status,Student.rating, Student.dateOfSub "
					+ "from Student,Assignment "
					+ "where Student.assignmentId=Assignment.assignmentID  and Student.uname=? and Student.assignmentId NOT IN (select assignmentId from Student_Temp)");
			
			stmt.setString(1, uname);
//			stmt.setDate(2, new Date(System.currentTimeMillis()));
//			stmt.setString(3, "Submitted");
			rs = stmt.executeQuery();
			
			while(rs.next())
				{
				ViewGrade ob = new ViewGrade(rs.getInt("assignmentId"),rs.getString("ques"),rs.getString("status"),rs.getString("rating"),rs.getDate("dateOfSub"));
				list.add(ob);
				}
			
			stmt = myconn.prepareStatement("Select Student_Temp.assignmentId, Assignment.ques, Student_Temp.status, Student_Temp.rating, Student_Temp.dateOfSub "
					+ "from Student_Temp,Assignment "
					+ "where Student_Temp.assignmentId=Assignment.assignmentID  and Student_Temp.uname=? ");
			
			stmt.setString(1, uname);
			rs = stmt.executeQuery();
			
			while(rs.next())
				{
				
				String rating = rs.getString("rating");
				
				ViewGrade ob = new ViewGrade(rs.getInt("assignmentId"),rs.getString("ques"),rs.getString("status") ,rating,rs.getDate("dateOfSub"));
				list.add(ob);
				}
			stmt.close();
			}
		catch(SQLException e)
			{
				e.printStackTrace();
			}
	
		return list;
	}
	
	public  ArrayList<ViewGrade> getInstructorAssignment(String uname){
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;   
		ArrayList<ViewGrade> list = new ArrayList<>();	
		
		try {
//			stmt = myconn.prepareStatement("Select Student.assignmentId, Assignment.ques, Student.rating, Student.dateOfSub "
//					+ "from Student,Assignment "
//					+ "where Student.assignmentId=Assignment.assignmentID and Student.uname=? and (Assignment.dateOfSub <= ? or Student.status = ?)");
			
			stmt = myconn.prepareStatement("Select Student.assignmentId, Assignment.ques, Student.status,Student.rating, Student.dateOfSub "
					+ "from Student,Assignment "
					+ "where Student.assignmentId=Assignment.assignmentID  and Assignment.uname=? ");
			
			stmt.setString(1, uname);
//			stmt.setDate(2, new Date(System.currentTimeMillis()));
//			stmt.setString(3, "Submitted");
			rs = stmt.executeQuery();
			
			while(rs.next())
				{
				ViewGrade ob = new ViewGrade(rs.getInt("assignmentId"),rs.getString("ques"),rs.getString("status"),rs.getString("rating"),rs.getDate("dateOfSub"));
				list.add(ob);
				}
			
			stmt = myconn.prepareStatement("Select Student_Temp.assignmentId, Assignment.ques, Student_Temp.status, Student_Temp.rating, Student_Temp.dateOfSub "
					+ "from Student_Temp,Assignment "
					+ "where Student_Temp.assignmentId=Assignment.assignmentID  and Assignment.uname=? ");
			
			stmt.setString(1, uname);
			rs = stmt.executeQuery();
			
			while(rs.next())
				{
				ViewGrade ob = new ViewGrade(rs.getInt("assignmentId"),rs.getString("ques"),rs.getString("status") ,rs.getString("rating"),rs.getDate("dateOfSub"));
				list.add(ob);
				}
			stmt.close();
			}
		catch(SQLException e)
			{
				e.printStackTrace();
			}
	
		return list;
	}
	
	public boolean submitAssignmentToTemp(int aid,Output output,String uname) throws SQLException
	{
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		
//		Assignment ob = getAssignment(aid);
//		if(ob != null)
//			deleteAssignment(aid,uname);
		
		int result = 0;
		
		String feedback = "";
		if(output != null)
			feedback = getFeedback(output);

		try {
				stmt = myconn.prepareStatement("Update Student_Temp SET status=?,dateOfSub=?,rating=?,feedback=?  where uname=? AND assignmentid=? ");
				stmt.setString(1, "Not Yet Graded");
				stmt.setDate(2, new Date(System.currentTimeMillis()));
				stmt.setString(3, output.getGrades()+"");
				System.out.println(feedback);
				stmt.setString(4, feedback);
				stmt.setString(5, uname);
				stmt.setInt(6, aid);
				
				result = stmt.executeUpdate();
				if(result > 0)
					return true;
			}
		catch(SQLException e)
			{
				e.printStackTrace();
			}

		return false;
	}

	public boolean deleteAssignment(int aid,String uname)
	{
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		
		
		
		int result = 0;
			
			try {
				stmt = myconn.prepareStatement("DELETE FROM Student_Temp where uname=? AND assignmentid=? ");
				stmt.setString(1, uname);
				stmt.setInt(2, aid);
				
				result = stmt.executeUpdate();
				if(result > 0)
					return true;
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
	
			return false;
	}
	
	
	protected String getFeedback(Output runout) 
	{
		String feedback = "";
		feedback += runout.getPreCompilation();
		feedback += "\n-------------------------------------------------------------\n";
		feedback += "\n"+runout.getCompiledOutput();
		
		if(!runout.isCompiledSucessfully())
			return feedback;
		
		feedback += "\n-------------------------------------------------------------\n";
		for(InstanceOutput ob : runout.getOutputlist())
		{
			feedback += ob.toString();
			feedback += "\n-------------------------------------------------------------\n"; 
		}
		
		return feedback;
	}
	
	public boolean gradeAllStudent(int assignmentID)
	{
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		int result = 0;
				
		try {
			
				stmt = myconn.prepareStatement("DELETE FROM Student where assignmentid=?  ");
				stmt.setInt(1, assignmentID);
				result = stmt.executeUpdate();
			
				stmt = myconn.prepareStatement("UPDATE Student_Temp set status=?,rating=? where assignmentid=? AND status='NA'");
				stmt.setString(1, "Not Submitted");
				stmt.setString(2, "F");
				stmt.setInt(3, assignmentID);
				result = stmt.executeUpdate();
				
				stmt = myconn.prepareStatement("UPDATE Student_Temp set status=? where assignmentid=? AND status='Not Yet Graded'");
				stmt.setString(1, "Submitted");
				stmt.setInt(2, assignmentID);
				result = stmt.executeUpdate();
				
				stmt = myconn.prepareStatement("INSERT INTO Student select * from Student_Temp where assignmentid=? ");
				stmt.setInt(1, assignmentID);
				result = stmt.executeUpdate();
								
				stmt = myconn.prepareStatement("DELETE FROM Student_Temp where assignmentid=?  ");
				stmt.setInt(1, assignmentID);
				result = stmt.executeUpdate();
				
				stmt = myconn.prepareStatement("DELETE FROM Assignment_Temp where assignmentid=?  ");
				stmt.setInt(1, assignmentID);
				result = stmt.executeUpdate();
				
//				if(result > 0)
					return true;
			}
		catch(SQLException e)
			{
				e.printStackTrace();
			}

		return false;
	}
	
	public String getFeedback(int assignmentID, String uname)
	{
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		ResultSet result = null;
		
		try {
				stmt = myconn.prepareStatement("SELECT feedback from Student_Temp where assignmentId=? AND uname=?");
				stmt.setInt(1,assignmentID);
				stmt.setString(2,uname);
								
				result = stmt.executeQuery();
				if(result.next())
				{	
					
					
					if(result.getString("feedback") != null)
						return result.getString("feedback");
					return "";
				}    
			}
		catch(SQLException e)
			{
				e.printStackTrace();
			}

		return null;

	}
}
