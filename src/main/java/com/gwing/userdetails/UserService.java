package com.gwing.userdetails;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public void save(UserModel user) {
		userRepository.save(user);		
	}

	public Map<String, Object> getDetails(String flatNo) {
		return userRepository.getDetails(flatNo);
	}

	public Map<String, Object> isUserExists(UserModel user) {
		 return userRepository.getDetails(user);
	}
	public Map<String, Object> getUserDetailsToCheckUpdate(UserModel user) {
		 return userRepository.getUserDetailsToCheckUpdate(user);
	}
	
	public void update(UserModel user) {
		userRepository.update(user);	
	}

	public void updateRole(UserModel user) {
		userRepository.updateRole(user);
	}

	public List<Map<String, Object>> getAllUser() {
		List<Map<String, Object>> userList = userRepository.getAllUser();
		for(Map<String, Object> map : userList) {
			map.remove("password");
		}
		return userList;
	}

}
