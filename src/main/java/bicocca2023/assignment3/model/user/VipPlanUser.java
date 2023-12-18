package bicocca2023.assignment3.model.user;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("VIP")
public class VipPlanUser extends User {

    private String VipType;
    public VipPlanUser() {
    }
/*
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Landmark> landmarks = new ArrayList<>();
*/

}
