package bicocca2023.assignment3.model;

import bicocca2023.assignment3.model.user.User;
import jakarta.persistence.*;

@Entity
public class Landmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(mappedBy = "landmark", cascade = CascadeType.ALL, orphanRemoval = true)
    private Coordinate coordinate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    //set get name
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