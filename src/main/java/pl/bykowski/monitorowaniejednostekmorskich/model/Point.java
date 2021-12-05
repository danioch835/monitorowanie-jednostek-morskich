package pl.bykowski.monitorowaniejednostekmorskich.model;

public class Point {

    private double y;
    private double x;
    private String name;
    private double destinationY;
    private double destinationX;
    private Long shipId;

    public Point(double y, double x, String name, double destinationY, double destinationX, Long shipId) {
        this.y = y;
        this.x = x;
        this.name = name;
        this.destinationY = destinationY;
        this.destinationX = destinationX;
        this.shipId = shipId;
    }

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDestinationY() {
        return destinationY;
    }

    public void setDestinationY(double destinationY) {
        this.destinationY = destinationY;
    }

    public double getDestinationX() {
        return destinationX;
    }

    public void setDestinationX(double destinationX) {
        this.destinationX = destinationX;
    }
}
