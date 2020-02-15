package com.gwing.userdetails;

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
				+ "VALUES("+user.getUsername()+","+
				user.getPassword()+","+	
				user.getFullName()+","+
				user.getFlatNo()+","+
				user.getContactNo1()+","+
				user.getContactNo2()+");");
		
		try {
			
			Map<String, Object> map = getUserDetails(user.getUsername());
			
			if(map!= null){
				jdbcTemplate.execute("INSERT INTO role "
						+ "(user_id,role_name) "
						+ "VALUES("+map.get("id")+",'user');");
			}
		}catch(EmptyResultDataAccessException e) {
			logger.error(e.getMessage());
		}
		
		
	}
	
	private Map<String, Object> getUserDetails(String username){
		return jdbcTemplate.queryForMap("SELECT * FROM user_details WHERE username= "+username);
	} 

	public Map<String, Object> getDetails(String username) {
		return getUserDetails(username);
	}

	public Map<String, Object> getDetails(UserModel user) {
		return jdbcTemplate.queryForMap("SELECT * FROM user_details WHERE username= "+user.getUsername()+" and password="+user.getPassword());
	}

}