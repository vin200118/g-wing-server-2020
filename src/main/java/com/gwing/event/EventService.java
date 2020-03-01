package com.gwing.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwing.eventcontribution.EventContributionRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository repository;
	@Autowired
	private EventContributionRepository eventContributionRepository;
	
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
		 List<Map<String, Object>> flatContDetails = eventContributionRepository.getAllFlatContriDetailsByEventId(event.getEventId());
		 List<String> flatsNo = new ArrayList<String>();
		 if(!flatContDetails.isEmpty()) {
			 //if existing flat number is not present in requested list and user given amount as well then dont allow him to remove him.
			 for(Map<String, Object> eventContriDetails : flatContDetails) {
				 flatsNo.add(""+eventContriDetails.get("flatNo"));
			 }
			 
			 int contributionAmount = (Integer.parseInt(event.getEventCost())/flatsNo.size());
			 
			 for(String flatNo : flatsNo) {
				 eventContributionRepository.update(event.getEventId(), flatNo, ""+contributionAmount);
			 }
		}
		repository.update(event);
	}

	public List<Map<String, Object>> getAllEvents() {
		return repository.getAllEvents();
	}
	
	public List<Map<String, Object>> getAllEvents(String status) {
		return repository.getAllEvents(status);
	}

	public void deleteEvent(int eventId) {
		repository.deleteEvent(eventId);
		
	}

}
