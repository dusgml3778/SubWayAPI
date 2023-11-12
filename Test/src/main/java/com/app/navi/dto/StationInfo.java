package com.app.navi.dto;

public class StationInfo {

	/** 駅名 */
	private String stationName = "";
	/** 距離 */
	private String distance = "";
	
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public StationInfo(String stationName, String distance) {
		super();
		this.stationName = stationName;
		this.distance = distance;
	}
}
