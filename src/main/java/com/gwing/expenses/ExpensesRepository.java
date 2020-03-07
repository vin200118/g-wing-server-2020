package com.gwing.expenses;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ExpensesRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	public void addExpense(ExpensesModel expensesModel) {
		jdbcTemplate.execute("INSERT INTO expenses "
				+ "(expenses_name,expenses_amount,paid_to,paid_by,date) "
				+ "VALUES('"+expensesModel.getExpensesName()+"',"+
				"'"+expensesModel.getExpensesAmt()+"',"+
				"'"+expensesModel.getPaidTo()+"',"+
				"'"+expensesModel.getPaidBy()+"',"+
				""+expensesModel.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()+");");
	}
	
	public List<Map<String, Object>> getAllExpense() {
		try {
			return jdbcTemplate.queryForList("SELECT exp_id AS expId,expenses_name AS expensesName, "
										+ "expenses_amount AS expensesAmount, paid_to AS paidTo, "
										+ "paid_by AS paidBy, date AS date from expense where expenses_amount is not null and expenses_amount !='' and expenses_amount !='0' order by date desc;");		
		}catch(EmptyResultDataAccessException e) {
			return new ArrayList<>();
		}
		
	}

}
