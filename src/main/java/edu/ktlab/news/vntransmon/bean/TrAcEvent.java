package edu.ktlab.news.vntransmon.bean;

import com.google.gson.Gson;

public class TrAcEvent extends NewsRawDocument {	
	String whereEvent;
	String geolocEvent;
	String whenEvent;
	String dameEvent;
	String vehicleEvent;
	String reasonEvent;
	String imageEvent;
	String levelEvent;
	
	public String getWhereEvent() {
		return whereEvent;
	}

	public void setWhereEvent(String whereEvent) {
		this.whereEvent = whereEvent;
	}

	public String getGeoLocEvent() {
		return geolocEvent;
	}

	public void setGeoLocEvent(String geolocEvent) {
		this.geolocEvent = geolocEvent;
	}

	public String getWhenEvent() {
		return whenEvent;
	}

	public void setWhenEvent(String whenEvent) {
		this.whenEvent = whenEvent;
	}

	public String getDameEvent() {
		return dameEvent;
	}

	public void setDameEvent(String dameEvent) {
		this.dameEvent = dameEvent;
	}

	public String getVehicleEvent() {
		return vehicleEvent;
	}

	public void setVehicleEvent(String vehicleEvent) {
		this.vehicleEvent = vehicleEvent;
	}

	public String getReasonEvent() {
		return reasonEvent;
	}

	public void setReasonEvent(String reasonEvent) {
		this.reasonEvent = reasonEvent;
	}

	public String getImageEvent() {
		return imageEvent;
	}

	public void setImageEvent(String imageEvent) {
		this.imageEvent = imageEvent;
	}

	public String getLevelEvent() {
		return levelEvent;
	}

	public void setLevelEvent(String levelEvent) {
		this.levelEvent = levelEvent;
	}

	public String printJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
