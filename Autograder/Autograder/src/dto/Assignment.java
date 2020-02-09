package dto;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.MyConnection;

public class Assignment 
{
	private String uname,ques,description;
	private int assignmentId,nclass;
	private Date dateOfUpload,dateOfSub;
	private InputStream testFile,exOutputFile;
	
	public Assignment() {
		super();
	}
	
	public Assignment(String uname, int assignmentId, String ques, String description, Date dateOfUpload,
			Date dateOfSub, InputStream testFile,InputStream exOutputFile,int nclass) {
		super();
		this.uname = uname;
		this.assignmentId = assignmentId;
		this.ques = ques;
		this.description = description;
		this.dateOfUpload = dateOfUpload;
		this.dateOfSub = dateOfSub;
		this.testFile = testFile;
		this.exOutputFile = exOutputFile;
		this.nclass = nclass;
	}


	public Assignment(int assignmentId, String ques, String description, Date dateOfSub) {
		this.assignmentId = assignmentId;
		this.ques = ques;
		this.description = description;
		this.dateOfSub = dateOfSub;
	}

	public Assignment(String uname, int assignmentId, String ques, Date dateOfUpload, InputStream testFile, Date dateOfSub,InputStream exOutputFile,int nclass) 
	{
		super();
		this.uname = uname;
		this.assignmentId = assignmentId;
		this.ques = ques;
		this.dateOfUpload = dateOfUpload;
		this.testFile = testFile;
		this.exOutputFile = exOutputFile;
		this.nclass = nclass;
	}
	
	public Date getDateOfSub() {
		return dateOfSub;
	}

	public void setDateOfSub(Date dateOfSub) {
		this.dateOfSub = dateOfSub;
	}
	
	public Assignment(String uname, int assignmentId, String ques, String description, Date dateOfSub, int nclass) {
		super();
		this.uname = uname;
		this.assignmentId = assignmentId;
		this.ques = ques;
		this.dateOfSub = dateOfSub;
		this.description = description;
		this.nclass = nclass;
	}

	
	public Assignment(String uname, int assignmentId, String ques, String description, Date dateOfUpload, Date dateOfSub, int nclass) {
		super();
		this.uname = uname;
		this.assignmentId = assignmentId;
		this.ques = ques;
		this.dateOfUpload = dateOfUpload;
		this.description = description;
		this.dateOfSub = dateOfSub;
		this.nclass = nclass;
	}

	public Assignment(String uname, int assignmentId, String ques, String description, Date dateOfUpload,InputStream testFile, Date dateOfSub,InputStream exOutputFile, int nclass) {
		super();
		this.uname = uname;
		this.assignmentId = assignmentId;
		this.ques = ques;
		this.description = description;
		this.dateOfUpload = dateOfUpload;
		this.testFile = testFile;
		this.dateOfSub = dateOfSub;
		this.exOutputFile = exOutputFile;
		this.nclass = nclass;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getQues() {
		return ques;
	}

	public void setQues(String ques) {
		this.ques = ques;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public InputStream getTestFile() {
		return testFile;
	}

	public void setTestFile(InputStream testFile) {
		this.testFile = testFile;
	}

	public int getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}

	public Date getDateOfUpload() {
		return dateOfUpload;
	}

	public void setDateOfUpload(Date dateOfUpload) {
		this.dateOfUpload = dateOfUpload;
	}
		
	public InputStream getExOutputFile() {
		return exOutputFile;
	}

	public void setExOutputFile(InputStream exOutputFile) {
		this.exOutputFile = exOutputFile;
	}
	
	public int getNclass() {
		return nclass;
	}

	public void setNclass(int nclass) {
		this.nclass = nclass;
	}

	public static int  getNextAssignmentId() throws SQLException 
	{
		Connection myconn = new MyConnection().getMyConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int nextId = -1;
		
		try {
			stmt = myconn.prepareStatement("SELECT MAX(assignmentID) FROM Autograder.Assignment ");
						
			rs  = stmt.executeQuery();
			if(rs.next())
				nextId = rs.getInt(1)+1;
			else
				nextId = 101;
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally
		{			
			stmt.close();
			myconn.close();
		}	
		
		return nextId;
	}
	
}
