package com.gwing.expenses;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/")
public class ExpensesController {
	public static final Logger logger = LoggerFactory.getLogger(ExpensesController.class);
	
	@Autowired
	ExpensesService expensesService;
	
	@CrossOrigin
	@RequestMapping(value = "expenses", method = RequestMethod.POST)
	public ResponseEntity<?> recevicedContribution(@RequestBody ExpensesModel expensesModel) throws SQLException {
		expensesService.addExpense(expensesModel);
		return new ResponseEntity<String>("Expenses Added Successfully", HttpStatus.OK);			
	}
	
	@CrossOrigin
	@RequestMapping(value = "expenses", method = RequestMethod.GET)
	public ResponseEntity<?> getAllExpenses() {
			return new ResponseEntity<List<Map<String, Object>>>(expensesService.getAllExpense(), HttpStatus.OK);		
	}

}
