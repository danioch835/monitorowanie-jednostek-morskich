package pl.bykowski.monitorowaniejednostekmorskich.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TracksItem{

	@JsonProperty("wellBoatWeekId")
	private int wellBoatWeekId;

	@JsonProperty("isNoSignal")
	private boolean isNoSignal;

	@JsonProperty("fromTime")
	private String fromTime;

//	@JsonProperty("coordinates")
//	@JsonUnwrapped
//	private List<Coordinate> coordinates;

	@JsonProperty("intervalPoints")
	private List<TrackPosition> intervalPoints;

	@JsonProperty("id")
	private int id;

	@JsonProperty("toTime")
	private String toTime;

	public void setWellBoatWeekId(int wellBoatWeekId){
		this.wellBoatWeekId = wellBoatWeekId;
	}

	public int getWellBoatWeekId(){
		return wellBoatWeekId;
	}

	public void setIsNoSignal(boolean isNoSignal){
		this.isNoSignal = isNoSignal;
	}

	public boolean isIsNoSignal(){
		return isNoSignal;
	}

	public void setFromTime(String fromTime){
		this.fromTime = fromTime;
	}

	public String getFromTime(){
		return fromTime;
	}

//	public void setCoordinates(List<Coordinate> coordinates){
//		this.coordinates = coordinates;
//	}
//
//	public List<Coordinate> getCoordinates(){
//		return coordinates;
//	}

	public void setIntervalPoints(List<TrackPosition> intervalPoints){
		this.intervalPoints = intervalPoints;
	}

	public List<TrackPosition> getIntervalPoints(){
		return intervalPoints;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setToTime(String toTime){
		this.toTime = toTime;
	}

	public String getToTime(){
		return toTime;
	}
}