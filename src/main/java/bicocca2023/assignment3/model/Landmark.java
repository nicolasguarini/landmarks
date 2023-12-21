package bicocca2023.assignment3.model;

import bicocca2023.assignment3.model.user.User;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Landmark {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Expose
    @Column(unique = true)
    private String name;

    @Expose
    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    /*
    @OneToOne(mappedBy = "landmark", cascade = CascadeType.ALL, orphanRemoval = true)
    private Coordinate coordinate;


    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
        if (coordinate != null) {
           // coordinate.setLandmark(this);
        }
    }
    */


}