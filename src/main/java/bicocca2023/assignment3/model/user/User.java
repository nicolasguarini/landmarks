package bicocca2023.assignment3.model.user;

import bicocca2023.assignment3.exception.LandmarksLimitException;
import bicocca2023.assignment3.model.Landmark;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
@Table(name = "users")
abstract public class User {
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = {CascadeType.DETACH}, orphanRemoval = true)
    private final List<Landmark> landmarks = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private final List<User> followers = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "followers")
    private final List<User> followedBy = new ArrayList<>();

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Expose @Column(name="user_type", insertable = false, updatable = false)
    private String plan;

    @Expose @Column(unique = true)
    private String username;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addLandmark(Landmark landmark) throws LandmarksLimitException {
        landmarks.add(landmark);
    }

    public boolean isFollowing(User userToCheck) {
        return followers.contains(userToCheck);
    }

    public List<Landmark> getLandmarks() {
        return landmarks;
    }

    @Override
    public String toString() {
        return "(" + id + ") - " + username;
    }

    public void followUser(User user) {
        followers.add(user);
        user.followedBy.add(this);
    }

    public void unfollowUser(User user) {
        followers.remove(user);
        user.followedBy.remove(this);
    }

    public List<User> getFollowers() {
        return followers;
    }

    public List<User> getFollowedBy() {
        return followedBy;
    }

}
