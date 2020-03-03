package com.gwing.userdetails;

public class UserModel {
	private int id;
	private String password;
	private String fullName;
	private String flatNo;
	private String contactNo1;
	private String contactNo2;
	private String roleName;
	private String vehicleNo1;
	private String vehicleNo2;
	private String vehicleNo3;
	
	public UserModel() {
		
	}
	public UserModel(int id,String password, 
			String fullName, String flatNo, String contactNo1, String contactNo2, String roleName, String vehicleNo1, String vehicleNo2, String vehicleNo3) {
		this.id=id;
		this.password=password;
		this.fullName=fullName;
		this.flatNo=flatNo;
		this.contactNo1=contactNo1;
		this.contactNo2=contactNo2;
		this.roleName= roleName;
		this.vehicleNo1 = vehicleNo1;
		this.vehicleNo2 = vehicleNo2;
		this.vehicleNo3 = vehicleNo3;
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getVehicleNo1() {
		return vehicleNo1;
	}
	public void setVehicleNo1(String vehicleNo1) {
		this.vehicleNo1 = vehicleNo1;
	}
	public String getVehicleNo2() {
		return vehicleNo2;
	}
	public void setVehicleNo2(String vehicleNo2) {
		this.vehicleNo2 = vehicleNo2;
	}
	public String getVehicleNo3() {
		return vehicleNo3;
	}
	public void setVehicleNo3(String vehicleNo3) {
		this.vehicleNo3 = vehicleNo3;
	}
	
	
	
}
