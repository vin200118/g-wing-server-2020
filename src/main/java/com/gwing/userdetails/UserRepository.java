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
				+ "(flat_no,password,full_name,contact_no1,contact_no2) "
				+ "VALUES('"+user.getFlatNo()+"',"+
				"'"+user.getPassword()+"',"+	
				"'"+user.getFullName()+"',"+
				"'"+user.getContactNo1()+"',"+
				"'"+user.getContactNo2()+"');");
		
		try {
			
			Map<String, Object> map = getUserDetails(user.getFlatNo());
			
			if(map!= null){
				jdbcTemplate.execute("INSERT INTO role "
						+ "(user_id,role_name) "
						+ "VALUES("+Integer.parseInt(""+map.get("user_id"))+",'user');");
			}
		}catch(EmptyResultDataAccessException e) {
			logger.error(e.getMessage());
		}
		
		
	}
	
	private Map<String, Object> getUserDetails(String flatNo){
		return jdbcTemplate.queryForMap("SELECT * FROM user_details WHERE flat_no= '"+flatNo+"'");
	} 
	public Map<String, Object> getUserDetailsToCheckUpdate(UserModel user){
		return jdbcTemplate.queryForMap("SELECT * FROM user_details WHERE flat_no= '"+user.getFlatNo()+"' and "
				+ "user_id!="+user.getId());
	} 

	public Map<String, Object> getDetails(String flatNo) {
		return getUserDetails(flatNo);
	}

	public Map<String, Object> getDetails(UserModel user) {
		Map<String, Object> userDetails = jdbcTemplate.queryForMap("SELECT * FROM user_details WHERE flat_no= '"+user.getFlatNo()+"' and password='"+user.getPassword()+"'");
		Map<String, Object> userRole = jdbcTemplate.queryForMap("SELECT * FROM role WHERE user_id= '"+userDetails.get("user_id")+"'");
		userDetails.putAll(userRole);
		return userDetails;
	}

	public void update(UserModel user) {
		jdbcTemplate.execute("UPDATE user_details SET "
				+ "password='"+user.getPassword()+"',"
				+ "full_name='"+user.getFullName()+"',"
				+ "flat_no='"+user.getFlatNo()+"',"
				+ "contact_no1='"+user.getContactNo1()+"',"
				+ "contact_no2='"+user.getContactNo2()+"' WHERE user_id="+user.getId());
		
	}

	public void updateRole(UserModel user) {
		Map<String, Object> userDetails = getUserDetails(user.getFlatNo());
		jdbcTemplate.execute("UPDATE role SET "
				+ "role_name='"+user.getRoleName()+"' WHERE user_id="+userDetails.get("user_id"));
		
	}

	public List<Map<String, Object>> getAllUser() {
		return jdbcTemplate.queryForList("SELECT * FROM user_details order by flat_no asc");
	}

}
