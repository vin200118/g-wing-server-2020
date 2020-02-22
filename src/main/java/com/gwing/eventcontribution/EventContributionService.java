package com.gwing.eventcontribution;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gwing.event.Event;
import com.gwing.event.EventRepository;

@Service
public class EventContributionService {
	public static final Logger logger = LoggerFactory.getLogger(EventContributionService.class);
	private EventContributionRepository repository;
	private EventRepository eventRepository;
	
	public EventContributionService(@Autowired EventContributionRepository repository, @Autowired EventRepository eventRepository) {
		this.repository = repository;
		this.eventRepository = eventRepository;
	}

	public void save(EventContributionModel eventContrib) {
		
		Map<String, Object> eventMap = eventRepository.getDetailsByEventId(eventContrib.getEventId());
		if(!StringUtils.isEmpty(""+eventMap.get("status")) && Event.COMPLETED.equals(""+eventMap.get("status"))) {
			throw new IllegalArgumentException("This "+eventMap.get("eventName")+" is already completed, you can't modified it.");
		}
		
		 List<Map<String, Object>> listEventContri = repository.getAllFlatContriDetailsByEventId(eventContrib.getEventId());
		 List<String> flatNos = Arrays.asList(eventContrib.getFlatNo());
		 int cost = Integer.parseInt(""+eventRepository.getDetailsByEventId(eventContrib.getEventId()).get("eventCost"));
		 int contributionAmount = (cost/flatNos.size());
		 if(!listEventContri.isEmpty()) {
			 //if existing flat number is not present in requested list and user given amount as well then dont allow him to remove him.
			 for(Map<String, Object> eventContriDetails : listEventContri) {
				 logger.info("db flatno : "+eventContriDetails.get("flatNo"));
				 if(!flatNos.contains(""+eventContriDetails.get("flatNo"))) {
					 logger.info("eventContriPaidAmount : "+eventContriDetails.get("eventContriPaidAmount"));
					 if(!StringUtils.isEmpty(eventContriDetails.get("eventContriPaidAmount"))){
						 throw new IllegalArgumentException("This"+eventContriDetails.get("flatNo")+" is already paid contirbution for this event,"
						 		+ " you first informed to respective flat number and mark empty in contri receive page");
					 }
				 }
			 }
			 
			 //remove existing flat number is not in requested list 
			 for(Map<String, Object> eventContriDetails : listEventContri) {
				 if(!flatNos.contains(eventContriDetails.get("flatNo"))) {
					 if(StringUtils.isEmpty(eventContriDetails.get("eventContriPaidAmount"))){
						 repository.deleteEventContriDetail(eventContrib.getEventId(), ""+eventContriDetails.get("flatNo"));
					 }
				 }
			 }
			 
			 	//update existing flat number and save new flat number into event_contribution table
			 			for(String flatNo:flatNos) {
			 				boolean isNewFlatNumber=true;
			 				for(Map<String, Object> eventContriDetails : listEventContri) {
			 					
			 					if(flatNo.equals(""+eventContriDetails.get("flatNo"))) {
			 						 isNewFlatNumber=false;
			 						 repository.update(eventContrib.getEventId(), ""+eventContriDetails.get("flatNo"), ""+contributionAmount);
			 					}
			 				}
			 				if(isNewFlatNumber) {
			 					repository.save(eventContrib.getEventId(), flatNo, ""+contributionAmount); 
			 				}
		 			}
			 	}
		 else {
			 //Fresh entry for all flat numbers
			 for(String flatNo : flatNos) {
				  repository.save(eventContrib.getEventId(), flatNo, ""+contributionAmount); 
		     }
		 }
	}

	public void receivedContribution(EventContributionModel eventContrib) {
		
		Map<String, Object> eventMap = eventRepository.getDetailsByEventId(eventContrib.getEventId());
		if(!StringUtils.isEmpty(""+eventMap.get("status")) && Event.COMPLETED.equals(""+eventMap.get("status"))) {
			throw new IllegalArgumentException("This "+eventMap.get("eventName")+" is already completed, you can't modified it.");
		}
		for(String flatNo:eventContrib.getFlatNo()) {
				repository.saveReceivedContribution(eventContrib.getEventId(), flatNo, eventContrib.getEventContriPaidAmount(),
					eventContrib.getPaidToFlatNo(), eventContrib.getEventContriDate());
		}
		
	}

	public List<Map<String, Object>> getAllFlatContributionDetails(int eventId) {
		return repository.getAllFlatContriDetailsByEventId(eventId);
	}

	public Map<String, Object> getFlatContriDetailsByFlatAndEventId(int eventId, int flatNo) {
		return repository.getFlatContriDetailsByFlatAndEventId(eventId, flatNo);
	}

}
