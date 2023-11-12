package com.app.navi.dto;

public class WalkInfo {

	private String walkTime = "";
	private String walkDistance = "";
	
	public String getWalkTime() {
		return walkTime;
	}
	public void setWalkTime(String walkTime) {
		this.walkTime = walkTime;
	}
	public String getWalkDistance() {
		return walkDistance;
	}
	public void setWalkDistance(String walkDistance) {
		this.walkDistance = walkDistance;
	}
	public WalkInfo(String walkTime, String walkDistance) {
		super();
		this.walkTime = walkTime;
		this.walkDistance = walkDistance;
	}
}
