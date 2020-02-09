package dto;

public class User 
{
	public static final String STUDENT_TYPE = "Student";
	public static final String INSTRUCTOR_TYPE = "Instructor";
	public static final String UNF_Exception = "User Not Found";
	
	private String uname,name,pass,type;
    
	public User() {
		super();
	}

	public User(String uname, String pass, String name, String type) {
		super();
		this.uname = uname;
		this.name = name;
		this.pass = pass;
		this.type = type;
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

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public String toString() {
		return "[Username : "+uname+" "
				+ "Name : "+name+" "
				+ "Type : "+type+"]";
				
	}
}
