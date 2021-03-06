package com.gwing.userdetails;

import java.util.List;
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
	
	@CrossOrigin
	@RequestMapping(value = "user", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserModel user) {
		try {
			userService.getDetails(user.getFlatNo());
		}catch(EmptyResultDataAccessException e) {
			userService.save(user);
			return new ResponseEntity<String>("User Registered Successfully", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Flat no is already exists,try another flatno", HttpStatus.BAD_REQUEST);
	}
	
	@CrossOrigin
	@RequestMapping(value = "user/update", method = RequestMethod.POST)
	public ResponseEntity<?> updateUser(@RequestBody UserModel user) {
		
		try {
			userService.getUserDetailsToCheckUpdate(user);
		}catch(EmptyResultDataAccessException e) {
			userService.update(user);
			return new ResponseEntity<String>("User details updated Successfully", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Flat no is already exists,check with admin", HttpStatus.BAD_REQUEST);
		
		
	}
	
	@CrossOrigin
	@RequestMapping(value = "role", method = RequestMethod.PUT)
	public ResponseEntity<?> updateRole(@RequestBody UserModel user) {
		
		try {
			userService.updateRole(user);
			return new ResponseEntity<String>("User role updated Successfully", HttpStatus.OK);
		}catch(EmptyResultDataAccessException e) {
			
			return new ResponseEntity<String>("User doesn't exists", HttpStatus.BAD_REQUEST);
		}
				
	}
	
	@CrossOrigin
	@RequestMapping(value = "user/{flatNo}", method = RequestMethod.GET)
	public ResponseEntity<?> getUserDetails(@PathVariable String flatNo) {
		try {
			return new ResponseEntity<Map<String, Object>>(userService.getDetails(flatNo), HttpStatus.OK);
		}catch(EmptyResultDataAccessException e) {
			return new ResponseEntity<String>(
					"flatno does not exists", 
			          HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = "user", method = RequestMethod.GET)
	public ResponseEntity<?> allUser() {
		try {
			return new ResponseEntity<List<Map<String, Object>>>(userService.getAllUser(), HttpStatus.OK);
		}catch(EmptyResultDataAccessException e) {
			return new ResponseEntity<String>(
					"User list is empty", 
			          HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = "user-login", method = RequestMethod.POST)
	public ResponseEntity<?> userLogin(@RequestBody UserModel user) {
		try {
			return new ResponseEntity<Map<String, Object>>(userService.isUserExists(user), HttpStatus.OK);
		}catch(EmptyResultDataAccessException e) {
			return new ResponseEntity<>(
					"Please enter correct Flat No and password", 
			          HttpStatus.UNAUTHORIZED);
		}	
		
	}
	

}
