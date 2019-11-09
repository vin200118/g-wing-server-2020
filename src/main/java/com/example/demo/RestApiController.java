package com.example.demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.postgresql.jdbc3.Jdbc3SimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@CrossOrigin
@RestController
@RequestMapping("/api/")
public class RestApiController {
	@Autowired
	JdbcTemplate jdbcTemplate;
	SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");  
	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);
	String tableName="cs_event";

	@RequestMapping(value = "delete-table/{tableName}", method = RequestMethod.GET)
	public String deleteTable(@PathVariable String tableName) {
		jdbcTemplate.execute("DROP TABLE "+tableName);
		return "drop data suceessfully";
	}
	
	@RequestMapping(value = "/create-table/{tableName}", method = RequestMethod.GET)
	public String getRestAPI(@PathVariable String tableName) {
			jdbcTemplate.execute("create table "+tableName+" (otp VARCHAR(10) PRIMARY KEY,"
															+ "registration VARCHAR(30), "
															+ "gift VARCHAR(30),"
															+ "lunch VARCHAR(30)); ");
		return "table created";
	}
	
	@RequestMapping(value = "/process/{processName}/{opt}", method = RequestMethod.GET)
	public List<Map<String, Object>> processName(@PathVariable String processName,@PathVariable String otp) {
		  	Date date = new Date();  
		   jdbcTemplate.execute("update "+tableName+" set "+processName+"="+formatter.format(date)+" where otp="+otp);  
			List<Map<String, Object>> rows = jdbcTemplate.queryForList("select * from employee");
			
		return rows;
	}
	@RequestMapping(value = "/data/{processName}", method = RequestMethod.GET)
	public List<Map<String, Object>> getData(@PathVariable String processName) {
		List<Map<String, Object>> rows = new ArrayList<>();
			if("all".equalsIgnoreCase(processName)) {
			  	 rows = jdbcTemplate.queryForList("select * from "+tableName+"");
			}else {
				rows = jdbcTemplate.queryForList("select * from "+tableName+" where "+processName+" IS NOT NULL");
			}
		return rows;
	}
}