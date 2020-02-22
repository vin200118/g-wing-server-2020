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
				+ "(eventName,eventCost,eventStatus) "
				+ "VALUES('"+event.getEventName()+"',"+
				"'"+event.getEventCost()+"',"+
				"'"+event.getStatus()+"');");
	}

	public Map<String, Object> getDetails(String eventName) {
		return jdbcTemplate.queryForMap("SELECT eventId, eventName, eventCost, eventStatus FROM event WHERE eventName= '"+eventName+"'");
		
	}
	
	public Map<String, Object> getDetailsByEventId(int eventId) {
		return jdbcTemplate.queryForMap("SELECT eventId, eventName, eventCost, eventStatus FROM event WHERE eventId= '"+eventId+"'");
		
	}

	public Map<String, Object> getEventDetailsToCheckUpdate(Event event) {
		return jdbcTemplate.queryForMap("SELECT eventId, eventName, eventCost, eventStatus FROM event WHERE eventName= '"+event.getEventName()+"' and "
				+ "eventId!="+event.getEventId());
	}

	public void update(Event event) {
		jdbcTemplate.execute("UPDATE event SET "
				+ "eventName='"+event.getEventName()+"',"
						+ "eventStatus='"+event.getStatus()+"',"
				+ "eventCost='"+event.getEventCost()+"' WHERE eventId="+event.getEventId());
		
	}

	public List<Map<String, Object>> getAllEvents() {
		return jdbcTemplate.queryForList("SELECT 'eventId', 'eventName', 'eventCost', 'eventStatus' FROM event order by 'eventName' asc");
	}

	public void deleteEvent(int eventId) {
		jdbcTemplate.execute("DELETE FROM event WHERE eventId="+eventId); 
	}

}
