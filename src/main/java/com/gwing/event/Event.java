package com.gwing.event;

public class Event {
	public static String IN_PROGRESS="in_progress";
	public static String COMPLETED="completed";
	private int eventId;
	private String eventName;
	private String eventCost;
	private String status;
	
	public Event() {
		
	}
	public Event(int eventId, String eventName, String eventCost, String status) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventCost = eventCost;
		this.status = status;
	}
	
	public int getEventId() {
		return eventId;
	}
	public void setEventId(int eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventCost() {
		return eventCost;
	}
	public void setEventCost(String eventCost) {
		this.eventCost = eventCost;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
