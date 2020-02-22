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
				+ "(flat_no,event_id,event_cont_amt) "
				+ "VALUES('"+flatNo+"',"+
				""+eventId+",'"+eventContriAmt+"');");
	}
	
	public List<Map<String, Object>> getAllFlatContriDetailsByEventId(int eventId) {
		try {
			return jdbcTemplate.queryForList("SELECT event_cont_id AS eventContriId,flat_no AS flatNo, "
										+ "event_id AS eventId, event_cont_amt AS eventContriAmount, "
										+ "event_cont_paid_amt AS eventContriPaidAmount, event_cont_date AS eventContriDate,"
										+ "paid_to AS paidToFlatNo FROM event_contribution WHERE event_id="+eventId+";");		
		}catch(EmptyResultDataAccessException e) {
			return new ArrayList<>();
		}
		
	}
	
	public void deleteEventContriDetail(int eventId, String flatNo) {
		jdbcTemplate.execute("DELETE FROM event_contribution WHERE event_id="+eventId+" AND flat_no='"+flatNo+"'"); 
	}
	
	public void update(int eventId, String flatNo, String contriAmount) {
		jdbcTemplate.execute("UPDATE event_contribution SET "
				+ "event_cont_amt='"+contriAmount+"' WHERE event_id="+eventId+" AND flat_no='"+flatNo+"'");
		
	}


	public void saveReceivedContribution(int eventId, String flatNo, String eventContriPaidAmount,
			String paidToFlatNo, String eventContriDate) {
		
		jdbcTemplate.execute("UPDATE event_contribution SET "+
				" event_cont_paid_amt='"+eventContriPaidAmount+"', event_cont_date='"+eventContriDate+"', "
						+ "paid_to='"+paidToFlatNo+"' WHERE event_id="+eventId+" AND flat_no='"+flatNo+"'");
	}


	public Map<String, Object> getFlatContriDetailsByFlatAndEventId(int eventId, String flatNo) {
		return jdbcTemplate.queryForMap("SELECT event_cont_id AS eventContriId,flat_no AS flatNo, "
				+ "event_id AS eventId, event_cont_amt AS eventContriAmount, "
				+ "event_cont_paid_amt AS eventContriPaidAmount, event_cont_date AS eventContriDate,"
				+ "paid_to AS paidToFlatNo FROM event_contribution where event_id="+eventId+" AND flat_no='"+flatNo+"';");
	}


}
