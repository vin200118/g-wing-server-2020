package com.example.demo;

import java.sql.Connection;
import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/api/")
public class RestApiController {
	@Autowired
	JdbcTemplate jdbcTemplate;
	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@RequestMapping(value = "delete-table/{tableName}", method = RequestMethod.GET)
	public String deleteTable(@PathVariable String tableName) {
		jdbcTemplate.execute("DROP TABLE "+tableName);
		return "drop data suceessfully";
	}
	
	@RequestMapping(value = "/create-table/{tableName}", method = RequestMethod.GET)
	public String getRestAPI(@PathVariable String tableName) {
			jdbcTemplate.execute("create table "+tableName+" (id int, name varchar)");
		return "tableCreated";
	}
	
	@RequestMapping(value = "/process/{processName}", method = RequestMethod.GET)
	public ResponseEntity<String> processName(@PathVariable String processName) {
		
			jdbcTemplate.execute("insert into employee (id, name) values (1, 'Vinayak123')");  
			List<Map<String, Object>> rows = jdbcTemplate.queryForList("select * from employee");
			Integer id = null;
			String name = null;
			 for (Map row : rows) {
				 id = (Integer) row.get("id");
				 name =  (String) row.get("name");
			 }
		return new ResponseEntity<String>(" Id : "+id+", Name: "+name, HttpStatus.OK);
	}
}