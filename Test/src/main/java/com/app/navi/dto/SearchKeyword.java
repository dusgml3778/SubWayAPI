package com.app.navi.dto;

public class SearchKeyword {
	
	private String departure;
	private String destination;
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public SearchKeyword(String departure, String destination) {
		super();
		this.departure = departure;
		this.destination = destination;
	}
}
