package com.gwing.event;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class EventRepository {
	public static final Logger logger = LoggerFactory.getLogger(EventRepository.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public void save(Event event) {
		jdbcTemplate.execute("INSERT INTO event "
				+ "(event_name,cost,status) "
				+ "VALUES('"+event.getEventName()+"',"+
				"'"+event.getEventCost()+"',"+
				"'"+event.getStatus()+"');");
	}

	public Map<String, Object> getDetails(String eventName) {
		return jdbcTemplate.queryForMap("SELECT event_id AS eventId, event_name AS eventName, cost AS eventCost, status FROM event WHERE event_name= '"+eventName+"'");
		
	}
	
	public Map<String, Object> getDetailsByEventId(int eventId) {
		return jdbcTemplate.queryForMap("SELECT event_id AS eventId, event_name AS eventName, cost AS eventCost, status FROM event WHERE event_id= '"+eventId+"'");
		
	}

	public Map<String, Object> getEventDetailsToCheckUpdate(Event event) {
		return jdbcTemplate.queryForMap("SELECT event_id AS eventId, event_name AS eventName, cost AS eventCost, status FROM event WHERE event_name= '"+event.getEventName()+"' and "
				+ "event_id!="+event.getEventId());
	}

	public void update(Event event) {
		jdbcTemplate.execute("UPDATE event SET "
				+ "event_name='"+event.getEventName()+"',"
						+ "status='"+event.getStatus()+"',"
				+ "cost='"+event.getEventCost()+"' WHERE event_id="+event.getEventId());
		
	}

	public List<Map<String, Object>> getAllEvents() {
		return jdbcTemplate.queryForList("SELECT event_id AS eventId, event_name AS eventName, cost AS eventCost, status FROM event order by event_name asc");
	}
	
	public List<Map<String, Object>> getAllEvents(String status) {
		return jdbcTemplate.queryForList("SELECT event_id AS eventId, event_name AS eventName, cost AS eventCost, status FROM event where status='"+status+"' order by event_name asc");
	}

	public void deleteEvent(int eventId) {
		jdbcTemplate.execute("DELETE FROM event WHERE event_id="+eventId); 
	}

}
