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
	public ResponseEntity<?> saveUser(@RequestBody UserModel user) {
		try {
			userService.getDetails(user.getUsername());
		}catch(EmptyResultDataAccessException e) {
			userService.save(user);
			return new ResponseEntity<String>("User Registered Successfully", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Username is already exists,try another username", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "user/{username}", method = RequestMethod.GET)
	public ResponseEntity<?> saveUser(@PathVariable String username) {
		try {
			return new ResponseEntity<Map<String, Object>>(userService.getDetails(username), HttpStatus.OK);
		}catch(EmptyResultDataAccessException e) {
			return new ResponseEntity<String>(
					"username does not exists", 
			          HttpStatus.BAD_REQUEST);
		}
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
