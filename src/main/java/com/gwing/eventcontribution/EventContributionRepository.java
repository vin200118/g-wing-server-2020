package com.gwing.eventcontribution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EventContributionRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	public EventContributionRepository(@Autowired JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	

	public void save(int eventId, String flatNo, String eventContriAmt) {
		
		jdbcTemplate.execute("INSERT INTO event_contribution "
				+ "(flatNo,eventId,eventContriAmount) "
				+ "VALUES('"+flatNo+"',"+
				""+eventId+",'"+eventContriAmt+"');");
	}
	
	public List<Map<String, Object>> getAllFlatContriDetailsByEventId(int eventId) {
		try {
			return jdbcTemplate.queryForList("SELECT eventContriId,flatNo, "
										+ "eventId, eventContriAmount, "
										+ "eventContriPaidAmount, eventContriDate,"
										+ "paidToFlatNo FROM event_contribution WHERE eventId="+eventId+";");		
		}catch(EmptyResultDataAccessException e) {
			return new ArrayList<>();
		}
		
	}
	
	public void deleteEventContriDetail(int eventId, String flatNo) {
		jdbcTemplate.execute("DELETE FROM event_contribution WHERE eventId="+eventId+" AND flatNo='"+flatNo+"'"); 
	}
	
	public void update(int eventId, String flatNo, String contriAmount) {
		jdbcTemplate.execute("UPDATE event_contribution SET "
				+ "eventContriAmount='"+contriAmount+"' WHERE eventId="+eventId+" AND flatNo='"+flatNo+"'");
		
	}


	public void saveReceivedContribution(int eventId, String flatNo, String eventContriPaidAmount,
			String paidToFlatNo, String eventContriDate) {
		
		jdbcTemplate.execute("UPDATE event_contribution SET "+
				" eventContriPaidAmount='"+eventContriPaidAmount+"', eventContriDate='"+eventContriDate+"', "
						+ "paidToFlatNo='"+paidToFlatNo+"' WHERE eventId="+eventId+" AND flatNo='"+flatNo+"'");
	}


	public Map<String, Object> getFlatContriDetailsByFlatAndEventId(int eventId, String flatNo) {
		return jdbcTemplate.queryForMap("SELECT eventContriId,flatNo, "
				+ "eventId, eventContriAmount, "
				+ "eventContriPaidAmount, eventContriDate,"
				+ "paidToFlatNo FROM event_contribution where eventId="+eventId+" AND flatNo='"+flatNo+"';");
	}


}
