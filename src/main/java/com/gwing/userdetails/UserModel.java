package com.gwing.userdetails;

public class UserModel {
	private int id;
	private String username;
	private String password;
	private String fullName;
	private String flatNo;
	private String contactNo1;
	private String contactNo2;
	private String roleName;
	
	public UserModel() {
		
	}
	public UserModel(int id, String username, String password, 
			String fullName, String flatNo, String contactNo1, String contactNo2, String roleName) {
		this.id=id;
		this.username= username;
		this.password=password;
		this.fullName=fullName;
		this.flatNo=flatNo;
		this.contactNo1=contactNo1;
		this.contactNo2=contactNo2;
		this.roleName= roleName;
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getFlatNo() {
		return flatNo;
	}
	public void setFlatNo(String flatNo) {
		this.flatNo = flatNo;
	}
	public String getContactNo1() {
		return contactNo1;
	}
	public void setContactNo1(String contactNo1) {
		this.contactNo1 = contactNo1;
	}
	public String getContactNo2() {
		return contactNo2;
	}
	public void setContactNo2(String contactNo2) {
		this.contactNo2 = contactNo2;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
