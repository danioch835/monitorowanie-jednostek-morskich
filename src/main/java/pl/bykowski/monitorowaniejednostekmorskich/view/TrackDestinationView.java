package pl.bykowski.monitorowaniejednostekmorskich.view;

public class TrackDestinationView {

    private String name;
    private TrackPointView point;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TrackPointView getPoint() {
        return point;
    }

    public void setPoint(TrackPointView point) {
        this.point = point;
    }
}
