package pl.bykowski.monitorowaniejednostekmorskich.view;

import java.time.OffsetDateTime;
import java.util.List;

public class TrackPointView {
    private Double lat;
    private Double lon;
    private OffsetDateTime date;

    public OffsetDateTime getDate() {
        return date;
    }

    public void setDate(OffsetDateTime date) {
        this.date = date;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
