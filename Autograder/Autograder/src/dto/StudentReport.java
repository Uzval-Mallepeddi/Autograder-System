package dto;

import java.sql.Date;

import java.sql.Blob;

public class StudentReport 
{
	private String uname,name,status;
	private Date dateOfUpload;
	private String rating;
	private Blob solutionFile;
	
	public StudentReport() {
		super();
	}

	
	
	public StudentReport(String uname, String name, String status) {
		super();
		this.uname = uname;
		this.name = name;
		this.status = status;
	}



	public StudentReport(String uname, String name, String status, Date dateOfUpload, String rating,
			Blob solutionFile) {
		super();
		this.uname = uname;
		this.name = name;
		this.status = status;
		this.dateOfUpload = dateOfUpload;
		this.rating = rating;
		this.solutionFile = solutionFile;
	}

	public String getUname() {
		return uname;
	}



	public void setUname(String uname) {
		this.uname = uname;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public Date getDateOfUpload() {
		return dateOfUpload;
	}



	public void setDateOfUpload(Date dateOfUpload) {
		this.dateOfUpload = dateOfUpload;
	}



	public String getRating() {
		return rating;
	}



	public void setRating(String rating) {
		this.rating = rating;
	}



	public Blob getSolutionFile() {
		return solutionFile;
	}



	public void setSolutionFile(Blob solutionFile) {
		this.solutionFile = solutionFile;
	}
	
}
