package bicocca2023.assignment3.model;

import jakarta.persistence.*;

@Entity
@Table(name = "LMUser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    public User(){}

    public User(long id, String username){
        this.id = id;
        this.username = username;
    }

    public long getId(){
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }
}
