package bicocca2023.assignment3.model;

import bicocca2023.assignment3.model.user.User;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.UUID;

@Entity
public class Landmark {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Expose
    private String name;
    @Expose
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Expose
    @Embedded
    @Column(nullable = false)
    private Coordinate coordinate;

    public UUID getId(){
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }
}

