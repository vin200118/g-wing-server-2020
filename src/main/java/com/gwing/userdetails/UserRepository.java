package com.gwing.userdetails;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
	public static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public void save(UserModel user) {
		jdbcTemplate.execute("INSERT INTO user_details "
				+ "(username,password,full_name,flat_no,contact_no1,contact_no2) "
				+ "VALUES('"+user.getUsername()+"',"+
				"'"+user.getPassword()+"',"+	
				"'"+user.getFullName()+"',"+
				"'"+user.getFlatNo()+"',"+
				"'"+user.getContactNo1()+"',"+
				"'"+user.getContactNo2()+"');");
		
		try {
			
			Map<String, Object> map = getUserDetails(user.getUsername());
			
			if(map!= null){
				jdbcTemplate.execute("INSERT INTO role "
						+ "(user_id,role_name) "
						+ "VALUES("+Integer.parseInt(""+map.get("role_id"))+",'user');");
			}
		}catch(EmptyResultDataAccessException e) {
			logger.error(e.getMessage());
		}
		
		
	}
	
	private Map<String, Object> getUserDetails(String username){
		return jdbcTemplate.queryForMap("SELECT * FROM user_details WHERE username= '"+username+"'");
	} 
	public Map<String, Object> getUserDetailsToCheckUpdate(UserModel user){
		return jdbcTemplate.queryForMap("SELECT * FROM user_details WHERE username= '"+user.getUsername()+"' and "
				+ "user_id!="+user.getId());
	} 

	public Map<String, Object> getDetails(String username) {
		return getUserDetails(username);
	}

	public Map<String, Object> getDetails(UserModel user) {
		Map<String, Object> userDetails = jdbcTemplate.queryForMap("SELECT * FROM user_details WHERE username= '"+user.getUsername()+"' and password='"+user.getPassword()+"'");
		Map<String, Object> userRole = jdbcTemplate.queryForMap("SELECT * FROM role WHERE user_id= '"+userDetails.get("user_id")+"'");
		userDetails.putAll(userRole);
		return userDetails;
	}

	public void update(UserModel user) {
		jdbcTemplate.execute("UPDATE user_details SET "
				+ "username='"+user.getUsername()+"',"
				+ "password='"+user.getPassword()+"',"
				+ "full_name='"+user.getFullName()+"',"
				+ "flat_no='"+user.getFlatNo()+"',"
				+ "contact_no1='"+user.getContactNo1()+"',"
				+ "contact_no2='"+user.getContactNo2()+"' WHERE id="+user.getId());
		
	}

	public void updateRole(UserModel user) {
		Map<String, Object> userDetails = getUserDetails(user.getUsername());
		jdbcTemplate.execute("UPDATE role SET "
				+ "role_name='"+user.getRoleName()+"' WHERE user_id="+userDetails.get("id"));
		
	}

	public List<Map<String, Object>> getAllUser() {
		return jdbcTemplate.queryForList("SELECT * FROM user_details");
	}

}
