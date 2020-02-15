package gwing.userdetails;

public class UserRole {
	
	private int id;
	private String roleName;
	private int userId;
	
	public UserRole() {}
	public UserRole(int id, String roleName, int userId) {
		this.id=id;
		this.roleName=roleName;
		this.userId=userId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
