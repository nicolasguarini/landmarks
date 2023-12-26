package bicocca2023.assignment3.model;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

@Embeddable
public class Coordinate {
    @Expose
    @Column(nullable = false)
    private Double longitude;

    @Expose
    @Column(nullable = false)
    private Double latitude;

    @Expose
    private Double altitude;

    public void setLongitude(Double longitude){
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }

    public void setAltitude(Double altitude){
        this.altitude = altitude;
    }

    public Double getLongitude(){
        return longitude;
    }

    public Double getLatitude(){
        return latitude;
    }

    public Double getAltitude(){
        return altitude;
    }
}
