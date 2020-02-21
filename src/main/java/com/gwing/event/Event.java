package com.gwing.event;

public class Event {
	private int eventId;
	private String eventName;
	private String eventCost;
	
	public Event() {
		
	}
	public Event(int eventId, String eventName, String eventCost) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventCost = eventCost;
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
	
}
