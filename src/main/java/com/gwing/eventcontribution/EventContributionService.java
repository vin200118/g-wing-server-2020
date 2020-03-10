package com.gwing.eventcontribution;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.gwing.event.Event;
import com.gwing.event.EventRepository;
import com.gwing.expenses.ExpensesRepository;

@Service
public class EventContributionService {
	public static final Logger logger = LoggerFactory.getLogger(EventContributionService.class);
	private EventContributionRepository repository;
	private EventRepository eventRepository;
	private ExpensesRepository expensesRepository;
	
	public EventContributionService(@Autowired EventContributionRepository repository, @Autowired EventRepository eventRepository,
			@Autowired ExpensesRepository expensesRepository) {
		this.repository = repository;
		this.eventRepository = eventRepository;
		this.expensesRepository = expensesRepository;
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
				// logger.info("db flatno : "+eventContriDetails.get("flatNo"));
				 if(!flatNos.contains(""+eventContriDetails.get("flatNo"))) {
				//	 logger.info("eventContriPaidAmount : "+eventContriDetails.get("eventContriPaidAmount"));
					 if(null != eventContriDetails.get("eventContriPaidAmount") && 
							 !"".equals(""+eventContriDetails.get("eventContriPaidAmount")) &&
							 !"null".equals(""+eventContriDetails.get("eventContriPaidAmount")) ){
						 throw new IllegalArgumentException("This"+eventContriDetails.get("flatNo")+" is already paid contirbution for this event,"
						 		+ " you first informed to respective flat number and mark empty in contri receive page");
					 }
				 }
			 }
			 
			 //remove existing flat number is not in requested list 
			 for(Map<String, Object> eventContriDetails : listEventContri) {
				 if(!flatNos.contains(eventContriDetails.get("flatNo"))) {
					 if(null == eventContriDetails.get("eventContriPaidAmount") || 
							 "".equals(""+eventContriDetails.get("eventContriPaidAmount")) ||
							 "null".equals(""+eventContriDetails.get("eventContriPaidAmount")) ){
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

	public void receivedContribution(EventContributionModel eventContrib) throws SQLException {
		
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
	
	public Map<String, Object> getEventsAndExpensesTotals() {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> eventResultList = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> eventMap : eventRepository.getAllEvents()) {
			List<Map<String, Object>> eventContriMap = repository.getAllFlatContriDetailsByEventId(Integer.parseInt(eventMap.get("eventid").toString()));
			if(!eventContriMap.isEmpty()) {
				int totalAmountContributed=0;
				int totalFlatCount = 0;
				int eventContriAmount = 0;
				 for(Map<String, Object> map : eventContriMap) {
					 eventContriAmount=Integer.parseInt(map.get("eventContriAmount").toString());
					 totalFlatCount+=1;
					 totalAmountContributed += !StringUtils.isEmpty(map.get("eventContriPaidAmount")) ?Integer.parseInt(map.get("eventContriPaidAmount").toString()):0;
				 }
				 eventMap.put("totalFlatCount", totalFlatCount);
				 eventMap.put("eventContriAmount", eventContriAmount);
				 eventMap.put("totalAmountContributed", totalAmountContributed);
				 eventResultList.add(eventMap);
			}
		}
		result.put("eventAllDetails",eventResultList);
		List<Map<String, Object>> expList = expensesRepository.getAllExpense();
		int totalExpenses = 0;
		for(Map<String, Object> map : expList) {
			totalExpenses += !StringUtils.isEmpty(map.get("expensesAmt")) ? 0: Integer.parseInt(map.get("expensesAmt").toString());
		}
		result.put("totalExpenses",totalExpenses);
		return result;
	}
	
	public List<Map<String, Object>> isAnyFlatOwnerPaidContriForEvent(int eventId){
		return repository.isAnyFlatOwnerPaidContriForEvent(eventId);
	}
	
	public void deleteFlatContributionIfNoOnPaidAnyContriForEvent(int eventId){
		 repository.deleteFlatContributionIfNoOnPaidAnyContriForEvent(eventId);
	}

	public Map<String, Object> getFlatContriDetailsByFlatAndEventId(int eventId, String flatNo) {
		return repository.getFlatContriDetailsByFlatAndEventId(eventId, flatNo);
	}
	
	public List<Map<String, Object>> getFlatContriDetailsByFlatAndEventIds(String flatNo) throws SQLException {
		List<Integer> eventIds = new ArrayList<Integer>();
		for(Map<String, Object> map :eventRepository.getAllEvents()) {
			eventIds.add(Integer.parseInt(map.get("eventid").toString()));
		}
		return repository.getFlatContriDetailsByFlatAndEventIds(eventIds, flatNo);
	}

}
