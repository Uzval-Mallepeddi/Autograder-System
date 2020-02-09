package dto;

import java.sql.Date;

public class Student 
{
	String uname,status;
	int assignmentId;
	Date dateOfUpload;
	
	public Student() {
		super();
	}

	public Student(String uname, String status, int assignmentId, Date dateOfUpload) {
		super();
		this.uname = uname;
		this.status = status;
		this.assignmentId = assignmentId;
		this.dateOfUpload = dateOfUpload;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
	
}
