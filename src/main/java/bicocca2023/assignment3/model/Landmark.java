package bicocca2023.assignment3.model;

import bicocca2023.assignment3.model.user.User;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Landmark {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    @OneToOne(mappedBy = "landmark", cascade = CascadeType.ALL, orphanRemoval = true)
    private Coordinate coordinate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        if (coordinate != null) {
           // coordinate.setLandmark(this);
        }
    }


}