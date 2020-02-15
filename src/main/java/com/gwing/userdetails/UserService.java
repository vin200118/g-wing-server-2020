package com.gwing.userdetails;

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

	public Map<String, Object> getDetails(String username) {
		return userRepository.getDetails(username);
	}

	public Map<String, Object> isUserExists(UserModel user) {
		 return userRepository.getDetails(user);
	}

	public void update(UserModel user) {
		userRepository.update(user);	
	}

}
