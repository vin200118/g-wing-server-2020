package com.cs.event;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.postgresql.jdbc3.Jdbc3SimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.cs.event.model.Event;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

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
		return "drop data suceessfully.";
	}
	
	@RequestMapping(value = "create-table/{tableName}", method = RequestMethod.GET)
	public String getRestAPI(@PathVariable String tableName) {
			jdbcTemplate.execute("create table "+tableName+" (otp VARCHAR(10) PRIMARY KEY,"
															+ "registration VARCHAR(30), "
															+ "gift VARCHAR(30),"
															+ "lunch VARCHAR(30)); ");
		return "table created successfully.";
	}
	
	@RequestMapping(value = "insert-otp/{otp}", method = RequestMethod.POST)
	public String insertOTP(@PathVariable String otp) {
		
			jdbcTemplate.execute("insert into "+tableName+" (otp) values("+otp+")");
		return "data inserted for OTP "+otp;
	}
	
	@RequestMapping(value = "insert-otp/multi/{count}", method = RequestMethod.POST)
	public String insertOtpMulti(@PathVariable int count) {
			for(int i=1001;i<(1001+count);i++) {
				jdbcTemplate.execute("insert into "+tableName+" (otp) values("+count+")");
			}
		return "data inserted for OTP "+count;
	}
	
	@RequestMapping(value = "event", method = RequestMethod.PUT)
	public ResponseEntity<String> processName(@RequestBody Event event) {
		
		try {
			
			jdbcTemplate.queryForMap("select * from "+tableName+" where otp='"+event.getOtp()+"'");
			
		}catch(EmptyResultDataAccessException e) {
			return new ResponseEntity<>(
					"OTP is not present please check with Admin.", 
			          HttpStatus.BAD_REQUEST);
		}
		try {
			
			Map<String, Object> map = jdbcTemplate.queryForMap("select * from "+tableName+" where "+event.getEventName()+" IS NOT NULL and otp='"+event.getOtp()+"'");
			
			if(map.get(event.getEventName()) != null){
				return new ResponseEntity<>(
						"You have already used otp for "+event.getEventName(), 
				          HttpStatus.BAD_REQUEST);
			}
		}catch(EmptyResultDataAccessException e) {
			
		}
		  	Date date = new Date();  
		   jdbcTemplate.execute("update "+tableName+" set "+event.getEventName()+"='"+formatDateToString(date, "dd MMM yyyy hh:mm:ss a", "IST")+"' where otp='"+event.getOtp()+"'");  	
		return new ResponseEntity<>(
				"OTP "+ event.getOtp()+" is registered successfully", 
		          HttpStatus.OK);
	}
	@RequestMapping(value = "data/eventName/{eventName}", method = RequestMethod.GET)
	public List<Map<String, Object>> getData(@PathVariable String eventName) {
		List<Map<String, Object>> rows = new ArrayList<>();
			if("all".equalsIgnoreCase(eventName)) {
			  	 rows = jdbcTemplate.queryForList("select * from "+tableName+"");
			}else {
				rows = jdbcTemplate.queryForList("select * from "+tableName+" where "+eventName+" IS NOT NULL");
			}
		return rows;
	}
	
    @GetMapping("/export-data")
    public void exportCSV(HttpServletResponse response) throws Exception {
        //set file name and content type
        String filename = "users.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<Map<String, Object>> writer = new StatefulBeanToCsvBuilder<Map<String, Object>>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        //write all users to csv file
        writer.write(jdbcTemplate.queryForList("select * from "+tableName+""));
				
    }
	
	@RequestMapping(value = "data/otp/{otp}", method = RequestMethod.GET)
	public ResponseEntity<?> getOtpData(@PathVariable String otp) {
		
		try {
			
			jdbcTemplate.queryForMap("select * from "+tableName+" where otp='"+otp+"'");
			
		}catch(EmptyResultDataAccessException e) {
			return new ResponseEntity<>(
					"OTP is not present please check with Admin.", 
			          HttpStatus.BAD_REQUEST);
		}
			return new ResponseEntity<>(jdbcTemplate.queryForMap("select * from "+tableName+" where otp='"+otp+"'"), 
			          HttpStatus.OK);
	}
	
	public static String formatDateToString(Date date, String format,
			String timeZone) {
		// null check
		if (date == null) return null;
		// create SimpleDateFormat object with input format
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		// default system timezone if passed null or empty
		if (timeZone == null || "".equalsIgnoreCase(timeZone.trim())) {
			timeZone = Calendar.getInstance().getTimeZone().getID();
		}
		// set timezone to SimpleDateFormat
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		// return Date in required format with timezone as String
		return sdf.format(date);
	}
}