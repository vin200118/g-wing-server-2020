package com.gwing.eventcontribution;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gwing.event.EventRepository;

@Service
public class EventContributionService {
	
	private EventContributionRepository repository;
	private EventRepository eventRepository;
	
	public EventContributionService(@Autowired EventContributionRepository repository, @Autowired EventRepository eventRepository) {
		this.repository = repository;
		this.eventRepository = eventRepository;
	}

	public void save(EventContributionModel eventContrib) {
		 List<Map<String, Object>> listEventContri = repository.getAllFlatContriDetailsByEventId(eventContrib.getEventId());
		 List<String> flatNos = Arrays.asList(eventContrib.getFlatNo());
		 int cost = Integer.parseInt(""+eventRepository.getDetailsByEventId(eventContrib.getEventId()).get("eventCost"));
		 int contributionAmount = (cost/flatNos.size());
		 if(!listEventContri.isEmpty()) {
			 for(Map<String, Object> eventContriDetails : listEventContri) {
				 if(!flatNos.contains(""+eventContriDetails.get("flatNo"))) {
					 if(StringUtils.isEmpty(""+eventContriDetails.get("eventContriPaidAmount"))){
						 repository.deleteEventContriDetail(eventContrib.getEventId(), ""+eventContriDetails.get("flatNo"));
					 }else {
						 if(!new String(""+contributionAmount).equals(""+eventContriDetails.get("eventContriAmount"))) {
							 repository.update(eventContrib.getEventId(), ""+eventContriDetails.get("flatNo"), ""+contributionAmount);
						 }
					 }
				 }
			 }
		 }else {
			 for(String flatNo : flatNos) {
				 repository.save(eventContrib.getEventId(), flatNo, ""+contributionAmount);
			 }
		 }
		
	}

}
