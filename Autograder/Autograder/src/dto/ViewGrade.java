package dto;


import java.sql.Date;


public class ViewGrade {

	private int assignmentid;
	private String rating;
	private String ques;
	private Date dateOfSub;
	private String status;
	
	public ViewGrade() {
		super();
	}
	
	public ViewGrade(int assignmentid,String ques,String status,String rating,Date dateOfSub){
		super();
		this.assignmentid=assignmentid;
		this.ques=ques;
		this.rating=rating;
		this.dateOfSub=dateOfSub;
		this.status = status;
	}

	public int getAssignmentid() {
		return assignmentid;
	}

	public void setAssignmentid(int assignmentid) {
		this.assignmentid = assignmentid;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getQues() {
		return ques;
	}

	public void setQues(String ques) {
		this.ques = ques;
	}

	public Date getDateOfSub() {
		return dateOfSub;
	}

	public void setDateOfSub(Date dateOfSub) {
		this.dateOfSub = dateOfSub;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
