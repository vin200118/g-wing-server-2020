package com.gwing.event;

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
public class EventController {
	
	public static final Logger logger = LoggerFactory.getLogger(EventController.class);
	@Autowired
	EventService eventService;
	
	@CrossOrigin
	@RequestMapping(value = "event", method = RequestMethod.POST)
	public ResponseEntity<?> saveEvent(@RequestBody Event event) {
		try {
			eventService.getDetails(event.getEventName());
		}catch(EmptyResultDataAccessException e) {
			eventService.save(event);
			return new ResponseEntity<String>("Event Added Successfully", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Event name is already exists,try another event name", HttpStatus.BAD_REQUEST);
	}
	
	@CrossOrigin
	@RequestMapping(value = "event/update", method = RequestMethod.POST)
	public ResponseEntity<?> updateEvent(@RequestBody Event event) {
		
		try {
			eventService.getEventDetailsToCheckUpdate(event);
		}catch(EmptyResultDataAccessException e) {
			eventService.update(event);
			return new ResponseEntity<String>("Event details updated Successfully", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Event is already exists, check with admin", HttpStatus.BAD_REQUEST);
		
	}
	
	@CrossOrigin
	@RequestMapping(value = "event", method = RequestMethod.GET)
	public ResponseEntity<?> allEvents() {
		try {
			return new ResponseEntity<List<Map<String, Object>>>(eventService.getAllEvents(), HttpStatus.OK);
		}catch(EmptyResultDataAccessException e) {
			return new ResponseEntity<String>(
					"event list is empty", 
			          HttpStatus.BAD_REQUEST);
		}
	}
	
	@CrossOrigin
	@RequestMapping(value = "event/{eventId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteEvent(@PathVariable int eventId) {
		eventService.deleteEvent(eventId);
		return new ResponseEntity<String>(
				"Event deleted successfully.", 
		          HttpStatus.OK);
	}

}
