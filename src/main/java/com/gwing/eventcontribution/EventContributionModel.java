package com.gwing.eventcontribution;

public class EventContributionModel {
	private int eventContriId;
	private int eventId;
	private String[] flatNo;
	private String eventContriAmount;
	private String eventContriPaidAmount;
	private String eventContriDate;
	private String paidToFlatNo;
	
	public EventContributionModel() {}
	
	public EventContributionModel(int eventContriId, int eventId, String[] flatNo, String eventContriAmount,
			String eventContriPaidAmount, String eventContriDate, String paidToFlatNo) {
		this.eventContriId = eventContriId;
		this.eventId = eventId;
		this.flatNo = flatNo;
		this.eventContriAmount = eventContriAmount;
		this.eventContriPaidAmount = eventContriPaidAmount;
		this.eventContriDate = eventContriDate;
		this.paidToFlatNo = paidToFlatNo;
	}

	public int getEventContriId() {
		return eventContriId;
	}

	public void setEventContriId(int eventContriId) {
		this.eventContriId = eventContriId;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String[] getFlatNo() {
		return flatNo;
	}

	public void setFlatNo(String[] flatNo) {
		this.flatNo = flatNo;
	}

	public String getEventContriAmount() {
		return eventContriAmount;
	}

	public void setEventContriAmount(String eventContriAmount) {
		this.eventContriAmount = eventContriAmount;
	}

	public String getEventContriPaidAmount() {
		return eventContriPaidAmount;
	}

	public void setEventContriPaidAmount(String eventContriPaidAmount) {
		this.eventContriPaidAmount = eventContriPaidAmount;
	}

	public String getEventContriDate() {
		return eventContriDate;
	}

	public void setEventContriDate(String eventContriDate) {
		this.eventContriDate = eventContriDate;
	}

	public String getPaidToFlatNo() {
		return paidToFlatNo;
	}

	public void setPaidToFlatNo(String paidToFlatNo) {
		this.paidToFlatNo = paidToFlatNo;
	}
	
}
