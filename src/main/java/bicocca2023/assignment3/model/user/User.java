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

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "follower")
    private final List<UserFollower> followers = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "following")
    private final List<UserFollower> following = new ArrayList<>();


    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Expose
    @Column(name = "user_type", insertable = false, updatable = false)
    private String plan;
    @Expose
    @Column(unique = true)
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

    public List<Landmark> getLandmarks() {
        return landmarks;
    }

    @Override
    public String toString() {
        return "(" + id + ") - " + username;
    }

    public void follow(User userToFollow) {
        if (!isFollowing(userToFollow)) {
            UserFollower userFollower = new UserFollower(this, userToFollow);
            followers.add(userFollower);
            userToFollow.following.add(userFollower);
        }
    }

    public void unfollow(User userToUnfollow) {
        UserFollower userFollower = findUserFollower(userToUnfollow);
        if (userFollower != null) {
            followers.remove(userFollower);
            userToUnfollow.following.remove(userFollower);
        }
    }

    public List<UserFollower> getFollowers() {
        return followers;
    }

    public List<UserFollower> getFollowing() {
        return following;
    }

    public boolean isFollowing(User user) {
        return findUserFollower(user) != null;
    }

    private UserFollower findUserFollower(User user) {
        for (UserFollower userFollower : followers) {
            if (userFollower.getFollowing().equals(user)) {
                return userFollower;
            }
        }
        return null;
    }
}
