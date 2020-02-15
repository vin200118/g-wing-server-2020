package com.gwing.userdetails;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin
@RestController
@RequestMapping("/api/")
public class UserController {
	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "user", method = RequestMethod.POST)
	public String saveUser(@RequestBody UserModel user) {
		userService.save(user);
		return "User Registered Successfully";
	}
	
	@RequestMapping(value = "user/{username}", method = RequestMethod.GET)
	public Map<String, Object> saveUser(@PathVariable String username) {
		return userService.getDetails(username);
	}
	
	@RequestMapping(value = "user-login", method = RequestMethod.POST)
	public ResponseEntity<String> userLogin(@RequestBody UserModel user) {
		try {
			 userService.isUserExists(user);
		}catch(EmptyResultDataAccessException e) {
			return new ResponseEntity<>(
					"Username and password are incorrect", 
			          HttpStatus.UNAUTHORIZED);
		}	
		return new ResponseEntity<>("User logged in Successfully",HttpStatus.OK);
	}
	

}
