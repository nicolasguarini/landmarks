package bicocca2023.assignment3.model.user;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "user_followers")
public class UserFollower {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private User following;

    public UserFollower() {
    }

    public UserFollower(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }

    // Getter and setter methods

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }
}
