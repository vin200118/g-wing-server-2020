package com.gwing.eventcontribution;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EventContributionRepository {
	public static final Logger logger = LoggerFactory.getLogger(EventContributionRepository.class);
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
	
	public List<Map<String, Object>> isAnyFlatOwnerPaidContriForEvent(int eventId){
		return jdbcTemplate.queryForList("SELECT event_cont_id AS eventContriId,flat_no AS flatNo, "
				+ "event_id AS eventId, event_cont_amt AS eventContriAmount, "
				+ "event_cont_paid_amt AS eventContriPaidAmount, event_cont_date AS eventContriDate,"
				+ "paid_to AS paidToFlatNo FROM event_contribution WHERE event_id="+eventId+" and event_cont_paid_amt is not null and event_cont_paid_amt !='';");	
	}
	
	public void deleteFlatContributionIfNoOnPaidAnyContriForEvent(int eventId) {
		jdbcTemplate.execute("DELETE FROM event_contribution WHERE event_id="+eventId+" and event_cont_paid_amt is null or event_cont_paid_amt =='';"); 
	}
	
	public void deleteEventContriDetail(int eventId, String flatNo) {
		jdbcTemplate.execute("DELETE FROM event_contribution WHERE event_id="+eventId+" AND flat_no='"+flatNo+"'"); 
	}
	
	public void update(int eventId, String flatNo, String contriAmount) {
		jdbcTemplate.execute("UPDATE event_contribution SET "
				+ "event_cont_amt='"+contriAmount+"' WHERE event_id="+eventId+" AND flat_no='"+flatNo+"'");
		
	}


	public void saveReceivedContribution(int eventId, String flatNo, String eventContriPaidAmount,
			String paidToFlatNo, Date eventContriDate) throws SQLException {
		
		try( Connection con = jdbcTemplate.getDataSource().getConnection();
			PreparedStatement st = con.prepareStatement(
					"UPDATE event_contribution SET event_cont_paid_amt=?, event_cont_date=?,paid_to=? WHERE event_id=? AND flat_no=?")){
			st.setString(1, eventContriPaidAmount);
			st.setObject(2, eventContriDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
			st.setString(3, paidToFlatNo);
			st.setInt(4, eventId);
			st.setString(5, flatNo);
			st.executeUpdate();
		}
	}


	public Map<String, Object> getFlatContriDetailsByFlatAndEventId(int eventId, String flatNo) {
		return jdbcTemplate.queryForMap("SELECT event_cont_id AS eventContriId,flat_no AS flatNo, "
				+ "event_id AS eventId, event_cont_amt AS eventContriAmount, "
				+ "event_cont_paid_amt AS eventContriPaidAmount, event_cont_date AS eventContriDate,"
				+ "paid_to AS paidToFlatNo FROM event_contribution where event_id="+eventId+" AND flat_no='"+flatNo+"';");
	}
	
	public List<Map<String, Object>> getFlatContriDetailsByFlatAndEventIds(List<Integer> eventIds, String flatNo) throws SQLException {
		List<Map<String, Object>> result = new ArrayList<>();
		try( Connection con = jdbcTemplate.getDataSource().getConnection();
				PreparedStatement st = con.prepareStatement(
						"SELECT event_cont_id AS eventContriId,flat_no AS flatNo, "
								+ "event_id AS eventId, event_cont_amt AS eventContriAmount, "
								+ "event_cont_paid_amt AS eventContriPaidAmount, event_cont_date AS eventContriDate,"
								+ "paid_to AS paidToFlatNo FROM event_contribution where event_id in (?) AND flat_no=?")){
			 	Array tagIdsInArray = con.createArrayOf("integer", eventIds.toArray());
			 	st.setArray(1, tagIdsInArray);
				st.setString(2, flatNo);
				 try (ResultSet rs = st.executeQuery()) {
		                while (rs.next()) {
		                	Map<String, Object> map = new HashMap<String, Object>();
		                	map.put("eventContriId".toLowerCase(), rs.getInt("eventContriId".toLowerCase()));
		                	map.put("flatNo".toLowerCase(), rs.getString("flatNo".toLowerCase()));
		                	map.put("eventId".toLowerCase(), rs.getInt("eventId".toLowerCase()));
		                	map.put("eventContriAmount".toLowerCase(), rs.getString("eventContriAmount".toLowerCase()));
		                	map.put("eventContriPaidAmount".toLowerCase(), rs.getString("eventContriPaidAmount".toLowerCase()));
		                	map.put("eventContriDate".toLowerCase(), rs.getString("eventContriDate".toLowerCase()));
		                	map.put("paidToFlatNo".toLowerCase(), rs.getString("paidToFlatNo".toLowerCase()));
		                	result.add(map);
		                }
		          }
			}
		return result;
	}


}
