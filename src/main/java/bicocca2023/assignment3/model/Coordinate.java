package bicocca2023.assignment3.model;

import jakarta.persistence.*;

@Entity

public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double longitude;
    private Double latitude;
    private Double altitude;

    //set get fjgndf

    @OneToOne
    @JoinColumn(name = "landmark_id")
    private Landmark landmark;

    //=== Set methods ===

    public void setLandmark(Landmark landmark) {
        this.landmark = landmark;
    }
    public void setLongitude(Double longitude){
        this.longitude = longitude;
    }
    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }
    public void setAltitude(Double altitude){
        this.altitude = altitude;
    }

    //=== Get methods ===

    public Landmark getLandmark() {
        return landmark;
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
