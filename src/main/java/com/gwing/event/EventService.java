package com.gwing.event;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
	
	@Autowired
	private EventRepository repository;
	
	public Map<String, Object> getDetails(String eventName) {
		return repository.getDetails(eventName);
	}

	public void save(Event event) {
		repository.save(event);	
	}

	public Map<String, Object> getEventDetailsToCheckUpdate(Event event) {
		return repository.getEventDetailsToCheckUpdate(event);
	}

	public void update(Event event) {
		repository.update(event);
	}

	public List<Map<String, Object>> getAllEvents() {
		return repository.getAllEvents();
	}

	public void deleteEvent(int eventId) {
		repository.deleteEvent(eventId);
		
	}

}
