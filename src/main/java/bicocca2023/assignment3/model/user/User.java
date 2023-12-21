package bicocca2023.assignment3.model.user;

import bicocca2023.assignment3.exception.LandmarksLimitException;
import bicocca2023.assignment3.model.Landmark;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
@Table(name = "users")
abstract public class User {
    @Expose @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Expose @Column(name="user_type", insertable = false, updatable = false)
    private String plan;

    @Expose @Column(unique = true)
    private String username;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Landmark> landmarks = new ArrayList<>();

    public User(){}

    public User(String username){
        this.username = username;
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void addLandmark(Landmark landmark) throws LandmarksLimitException {
        landmarks.add(landmark);
    }

    public List<Landmark> getLandmarks(){
        return landmarks;
    }

    @Override
    public String toString(){
        return "(" + id + ") - " + username;
    }
}
