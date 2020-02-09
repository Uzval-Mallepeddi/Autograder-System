package dto;

import java.sql.Date;

public class StudentAssignment 
{
	private int assignmentId;
	private String ques, instructor;
	private Date dateOfSub;
	
	public StudentAssignment() {
		super();
	}

	public StudentAssignment(int assignmentId, String ques, String instructor, Date dateOfSub) {
		super();
		this.assignmentId = assignmentId;
		this.ques = ques;
		this.instructor = instructor;
		this.dateOfSub = dateOfSub;
	}

	public int getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}
	
	public String getQues() {
		return ques;
	}

	public void setQues(String ques) {
		this.ques = ques;
	}

	public String getInstructor() {
		return instructor;
	}

	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}

	public Date getDateOfSub() {
		return dateOfSub;
	}

	public void setDateOfSub(Date dateOfSub) {
		this.dateOfSub = dateOfSub;
	}
}
