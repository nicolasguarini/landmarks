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
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Expose @Column(unique = true, nullable = false)
    private String username;

    @Expose @Column(name="user_type", insertable = false, updatable = false)
    private String plan;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = {CascadeType.DETACH}, orphanRemoval = true)
    private final List<Landmark> landmarks = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private final Set<User> following = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "following", cascade = CascadeType.ALL)
    private final Set<User> followers = new HashSet<>();

    public User() {}

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

    public List<Landmark> getLandmarks() {
        return landmarks;
    }

    public boolean isFollowing(User userToCheck) {
        return following.contains(userToCheck);
    }

    @Override
    public String toString() {
        return "(" + id + ") - " + username;
    }

    public void followUser(User user) {
        this.addFollowing(user);
        user.addFollower(this);
    }

    public void unfollowUser(User user) {
        this.removeFollowing(user);
        user.removeFollower(this);
    }

    public Set<User> getFollowers() { return followers; }

    public Set<User> getFollowings() { return following; }

    public void addFollower(User user) { followers.add(user); }

    public void addFollowing(User user){ this.following.add(user); }

    public void removeFollower(User user) { followers.remove(user); }

    public void removeFollowing(User user) { following.remove(user); }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;

        if(!(o instanceof User)) return false;

        User user = (User) o;

        return id != null && id.equals(user.getId());
    }

    @Override
    public int hashCode() { return getClass().hashCode(); }
}
