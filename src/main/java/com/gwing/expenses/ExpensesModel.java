package com.gwing.expenses;

import java.util.Date;

public class ExpensesModel {
	private int expId;
	private String expensesName;
	private String expensesAmt;
	private String paidTo;
	private String paidBy;
	private Date date;
	
	public ExpensesModel() {}
	
	public ExpensesModel(int expId, String expensesName, String expensesAmt, String paidTo, String paidBy, Date date) {
		this.expId = expId;
		this.expensesName = expensesName;
		this.expensesAmt = expensesAmt;
		this.paidTo = paidTo;
		this.paidBy = paidBy;
		this.date = date;
	}
	
	public int getExpId() {
		return expId;
	}
	public void setExpId(int expId) {
		this.expId = expId;
	}
	public String getExpensesName() {
		return expensesName;
	}
	public void setExpensesName(String expensesName) {
		this.expensesName = expensesName;
	}
	public String getExpensesAmt() {
		return expensesAmt;
	}
	public void setExpensesAmt(String expensesAmt) {
		this.expensesAmt = expensesAmt;
	}
	public String getPaidTo() {
		return paidTo;
	}
	public void setPaidTo(String paidTo) {
		this.paidTo = paidTo;
	}
	public String getPaidBy() {
		return paidBy;
	}
	public void setPaidBy(String paidBy) {
		this.paidBy = paidBy;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
