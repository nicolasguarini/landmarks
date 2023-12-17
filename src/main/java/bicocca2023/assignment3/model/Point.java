package bicocca2023.assignment3.model;

import jakarta.persistence.*;

@Entity
@Table(name = "coordinate")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("2D_POINT")
public class Point {

    @Id
    @Column(name = "longitude")
    private double longitude;

    @Id
    @Column(name = "latitude")
    private double latitude;

    public Point() {
    }

    public Point(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
