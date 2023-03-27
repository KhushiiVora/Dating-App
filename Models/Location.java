package Aashiqui.Models;

public class Location {
    Long xCordinate;
    Long yCordinate;

    public Location(long xCordinate, Long yCordinate) {
        this.xCordinate = xCordinate;
        this.yCordinate = yCordinate;
    }

    public Long getXCordinate() {
        return xCordinate;
    }

    public Long getYCordinate() {
        return yCordinate;
    }
}
