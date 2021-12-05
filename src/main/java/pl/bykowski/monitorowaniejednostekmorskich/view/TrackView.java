package pl.bykowski.monitorowaniejednostekmorskich.view;

import java.time.OffsetDateTime;
import java.util.List;

public class TrackView {

    private Long shipId;
    private String name;
    private String destination;
    private OffsetDateTime eta;
    private OffsetDateTime firstPointDate;
    private OffsetDateTime lastPointDate;
    private List<TrackPointView> points;
    private Integer distance;
    private TrackDestinationView trackDestination;

    public TrackDestinationView getTrackDestination() {
        return trackDestination;
    }

    public void setTrackDestination(TrackDestinationView trackDestination) {
        this.trackDestination = trackDestination;
    }

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public OffsetDateTime getEta() {
        return eta;
    }

    public void setEta(OffsetDateTime eta) {
        this.eta = eta;
    }

    public OffsetDateTime getFirstPointDate() {
        return firstPointDate;
    }

    public void setFirstPointDate(OffsetDateTime firstPointDate) {
        this.firstPointDate = firstPointDate;
    }

    public OffsetDateTime getLastPointDate() {
        return lastPointDate;
    }

    public void setLastPointDate(OffsetDateTime lastPointDate) {
        this.lastPointDate = lastPointDate;
    }

    public List<TrackPointView> getPoints() {
        return points;
    }

    public void setPoints(List<TrackPointView> points) {
        this.points = points;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
