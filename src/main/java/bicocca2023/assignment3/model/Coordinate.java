package bicocca2023.assignment3.model;

import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Coordinate {
    @Expose
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Expose
    private Double longitude;

    @Expose
    private Double latitude;

    @Expose
    private Double altitude;

    @OneToOne
    @JoinColumn(name = "landmark_id")
    private Landmark landmark;

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
