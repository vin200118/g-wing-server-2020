package com.gwing.eventcontribution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
			return null;
			
	}

}
