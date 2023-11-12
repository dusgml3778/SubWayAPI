package com.app.navi.dto;

public class TicketInfo {

	private String distance = "";
	private String stations = "";
	private String via = "";
	private String fare = "";
	private String time = "";
	private String onewayfare = "";
	
	public TicketInfo(String distance, String stations, String via, String fare, String time, String onewayfare) {
		super();
		this.distance = distance;
		this.stations = stations;
		this.via = via;
		this.fare = fare;
		this.time = time;
		this.onewayfare = onewayfare;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getStations() {
		return stations;
	}
	public void setStations(String stations) {
		this.stations = stations;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getFare() {
		return fare;
	}
	public void setFare(String fare) {
		this.fare = fare;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getOnewayfare() {
		return onewayfare;
	}
	public void setOnewayfare(String onewayfare) {
		this.onewayfare = onewayfare;
	}
}