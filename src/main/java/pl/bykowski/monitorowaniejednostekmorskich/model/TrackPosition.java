package pl.bykowski.monitorowaniejednostekmorskich.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;

public class TrackPosition {

	@JsonProperty("msgt")
	private OffsetDateTime msgt;

	@JsonProperty("heading")
	private Object heading;

	@JsonProperty("rot")
	private Object rot;

	@JsonProperty("cog")
	private Object cog;

	@JsonProperty("lon")
	private double lon;

	@JsonProperty("sog")
	private double sog;

	@JsonProperty("lat")
	private double lat;

	public void setMsgt(OffsetDateTime msgt){
		this.msgt = msgt;
	}

	public OffsetDateTime getMsgt(){
		return msgt;
	}

	public void setHeading(Object heading){
		this.heading = heading;
	}

	public Object getHeading(){
		return heading;
	}

	public void setRot(Object rot){
		this.rot = rot;
	}

	public Object getRot(){
		return rot;
	}

	public void setCog(Object cog){
		this.cog = cog;
	}

	public Object getCog(){
		return cog;
	}

	public void setLon(double lon){
		this.lon = lon;
	}

	public double getLon(){
		return lon;
	}

	public void setSog(double sog){
		this.sog = sog;
	}

	public double getSog(){
		return sog;
	}

	public void setLat(double lat){
		this.lat = lat;
	}

	public double getLat(){
		return lat;
	}
}