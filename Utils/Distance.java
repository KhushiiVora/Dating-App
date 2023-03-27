package Aashiqui.Utils;

import Aashiqui.Models.Location;

public class Distance {

    public Distance() {
    }

    public static int distance(Location locationPoint1, Location locationPoint2) {
        /* returning distance: sqrt((x-a)² + (y-b)²) */
        return (int) Math.sqrt((locationPoint1.getXCordinate() - locationPoint2.getXCordinate())
                * (locationPoint1.getXCordinate() - locationPoint2.getXCordinate())
                + (locationPoint1.getYCordinate() - locationPoint2.getYCordinate())
                        * (locationPoint1.getYCordinate() - locationPoint2.getYCordinate()));
    }

}
