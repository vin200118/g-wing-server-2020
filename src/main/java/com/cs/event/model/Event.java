package com.cs.event.model;

public class Event {
private String eventName;
private String  otp;

Event() {}

Event(String eventName, String otp) {
  this.eventName = eventName;
  this.otp = otp;
}
public String getEventName() {
	return eventName;
}
public void setEventName(String eventName) {
	this.eventName = eventName;
}
public String getOtp() {
	return otp;
}
public void setOtp(String otp) {
	this.otp = otp;
}



}
