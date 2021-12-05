package pl.bykowski.monitorowaniejednostekmorskich.persistence.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.OffsetDateTime;

@Entity
public class TrackPositionEntity {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	private Integer mmsi;

//	private String msgt;
//	private Object heading;
//	private double sog;
//	private Object rot;
//	private Object cog;

	private double lon;
	private double lat;
	private OffsetDateTime date;
	private Double distance;

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Integer getMmsi() {
		return mmsi;
	}

	public void setMmsi(Integer mmsi) {
		this.mmsi = mmsi;
	}

	public void setLon(double lon){
		this.lon = lon;
	}

	public double getLon(){
		return lon;
	}


	public void setLat(double lat){
		this.lat = lat;
	}

	public double getLat(){
		return lat;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OffsetDateTime getDate() {
		return date;
	}

	public void setDate(OffsetDateTime date) {
		this.date = date;
	}

	//	public void setMsgt(String msgt){
//		this.msgt = msgt;
//	}
//
//	public String getMsgt(){
//		return msgt;
//	}
//
//	public void setHeading(Object heading){
//		this.heading = heading;
//	}
//
//	public Object getHeading(){
//		return heading;
//	}
//
//	public void setRot(Object rot){
//		this.rot = rot;
//	}
//
//	public Object getRot(){
//		return rot;
//	}
//
//	public void setCog(Object cog){
//		this.cog = cog;
//	}
//
//	public Object getCog(){
//		return cog;
//	}
//	
//	public void setSog(double sog){
//		this.sog = sog;
//	}
//
//	public double getSog(){
//		return sog;
//	}

}