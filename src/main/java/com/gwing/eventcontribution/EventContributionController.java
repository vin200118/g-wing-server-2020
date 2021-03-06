package com.gwing.eventcontribution;

import java.sql.SQLException;
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
public class EventContributionController {
	public static final Logger logger = LoggerFactory.getLogger(EventContributionController.class);
	@Autowired
	EventContributionService eventContributionService;
	
	@CrossOrigin
	@RequestMapping(value = "eventcontribution", method = RequestMethod.POST)
	public ResponseEntity<?> saveEvent(@RequestBody EventContributionModel eventContrib) {
			eventContributionService.save(eventContrib);
			return new ResponseEntity<String>("Event Contribution split and save Successfully", HttpStatus.OK);
			
	}
	
	@CrossOrigin
	@RequestMapping(value = "eventcontribution/received", method = RequestMethod.POST)
	public ResponseEntity<?> recevicedContribution(@RequestBody EventContributionModel eventContrib) throws SQLException {
			eventContributionService.receivedContribution(eventContrib);
			return new ResponseEntity<String>("Event Contribution received Successfully", HttpStatus.OK);
			
	}
	
	@CrossOrigin
	@RequestMapping(value = "eventcontribution/{eventId}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllFlatsContributionDetails(@PathVariable int eventId) {
		try {
			return new ResponseEntity<List<Map<String, Object>>>(eventContributionService.getAllFlatContributionDetails(eventId), HttpStatus.OK);
		}catch(EmptyResultDataAccessException e) {
				return new ResponseEntity<String>(
						"No data found", 
				          HttpStatus.BAD_REQUEST);
			}
			
	}
	
	@CrossOrigin
	@RequestMapping(value = "eventcontribution/{eventId}/{flatNo}", method = RequestMethod.GET)
	public ResponseEntity<?> getAllFlatsContributionDetails(@PathVariable int eventId,@PathVariable String flatNo) {
		try {	
			return new ResponseEntity<Map<String, Object>>(eventContributionService.getFlatContriDetailsByFlatAndEventId(eventId, flatNo), HttpStatus.OK);
		}catch(EmptyResultDataAccessException e) {
			return new ResponseEntity<String>(
					"No data found", 
			          HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = "eventcontribution/flatno/{flatNo}", method = RequestMethod.GET)
	public ResponseEntity<?> getFlatContributionDetailsForAllEvents(@PathVariable String flatNo) throws SQLException {
		try {	
			return new ResponseEntity<List<Map<String, Object>>>(eventContributionService.getFlatContriDetailsByFlatAndEventIds(flatNo), HttpStatus.OK);
		}catch(EmptyResultDataAccessException e) {
			return new ResponseEntity<String>(
					"No data found", 
			          HttpStatus.BAD_REQUEST);
		}
	}
	@CrossOrigin
	@RequestMapping(value = "eventcontribution/total", method = RequestMethod.GET)
	public ResponseEntity<?> getEventsAndExpensesTotals() throws SQLException {
		try {	
			return new ResponseEntity<Map<String, Object>>(eventContributionService.getEventsAndExpensesTotals(), HttpStatus.OK);
		}catch(EmptyResultDataAccessException e) {
			return new ResponseEntity<String>(
					"No data found", 
			          HttpStatus.BAD_REQUEST);
		}
	}
	
}
