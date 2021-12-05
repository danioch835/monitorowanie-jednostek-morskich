package pl.bykowski.monitorowaniejednostekmorskich.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Tracks{

	@JsonProperty("fromDate")
	private String fromDate;

	@JsonProperty("mmsi")
	private int mmsi;

	@JsonProperty("toDate")
	private String toDate;

	@JsonProperty("tracks")
	private List<TracksItem> tracks;

	public void setFromDate(String fromDate){
		this.fromDate = fromDate;
	}

	public String getFromDate(){
		return fromDate;
	}

	public void setMmsi(int mmsi){
		this.mmsi = mmsi;
	}

	public int getMmsi(){
		return mmsi;
	}

	public void setToDate(String toDate){
		this.toDate = toDate;
	}

	public String getToDate(){
		return toDate;
	}

	public void setTracks(List<TracksItem> tracks){
		this.tracks = tracks;
	}

	public List<TracksItem> getTracks(){
		return tracks;
	}

	public List<TrackPosition> getAllIntervalPointsItem() {
		List<TrackPosition> trackPositions = new ArrayList<>();

		tracks.stream().forEachOrdered(t -> trackPositions.addAll(t.getIntervalPoints()));

		return trackPositions;
	}

}