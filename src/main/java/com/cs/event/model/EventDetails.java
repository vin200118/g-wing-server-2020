package com.cs.event.model;

public class EventDetails {
	private String opt;
	private String registration;
	private String gift;
	private String lunch;
	
	public EventDetails(){
		
	}
	public EventDetails(String opt, String registration, String gift, String lunch){
		this.opt = opt;
		this.registration = registration;
		this.gift= gift;
		this.lunch = lunch;
	}
	public String getOpt() {
		return opt;
	}
	public void setOpt(String opt) {
		this.opt = opt;
	}
	public String getRegistration() {
		return registration;
	}
	public void setRegistration(String registration) {
		this.registration = registration;
	}
	public String getGift() {
		return gift;
	}
	public void setGift(String gift) {
		this.gift = gift;
	}
	public String getLunch() {
		return lunch;
	}
	public void setLunch(String lunch) {
		this.lunch = lunch;
	}
	
	
	

}
