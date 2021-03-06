package com.gwing.expenses;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpensesService {

	@Autowired
	ExpensesRepository expensesRepository;
	
	public void addExpense(ExpensesModel expensesModel) throws SQLException {
		expensesRepository.addExpense(expensesModel);
	}
	
	public List<Map<String, Object>> getAllExpense(){
		return expensesRepository.getAllExpense();
	}

}
