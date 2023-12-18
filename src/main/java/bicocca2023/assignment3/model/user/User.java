package bicocca2023.assignment3.model.user;

import bicocca2023.assignment3.model.Landmark;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Landmark> landmarks = new ArrayList<>();

    public User(){}
    public User(Long id, String username){
        this.id = id;
        this.username = username;
    }

    public Long getId(){
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }


/*
    public void addLandmark(Landmark landmark) {
        if (landmarks.size() >= 10) {
            throw new IllegalStateException("BasicPlanUser can have at most 10 landmarks.");
        }
        landmarks.add(landmark);
        landmark.setUser(this);
    }

*/

}
