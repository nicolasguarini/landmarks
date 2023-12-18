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

    public Landmark getLandmark() {
        return landmark;
    }

    public void setLandmark(Landmark landmark) {
        this.landmark = landmark;
    }

}
