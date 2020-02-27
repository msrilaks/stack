package com.stack.library.model.stack;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Location implements Serializable {
    private double lat;
    private double lng;

    public double distanceInMilesTo(Location location){
        return distanceInMilesTo(location.getLat(), location.getLng());
    }
    public double distanceInMilesTo(double lat2, double lng2) {
        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat);
        double dLng = Math.toRadians(lng2-lng);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                                          * Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist; // output distance, in MILES
    }
}
