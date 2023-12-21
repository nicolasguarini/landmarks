package bicocca2023.assignment3.model;

import bicocca2023.assignment3.model.user.User;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.UUID;

@Entity
public class Landmark {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private  User user;

    public UUID getId(){
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setUser(User user){ this.user = user; }

    public User getUser() { return user; }


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