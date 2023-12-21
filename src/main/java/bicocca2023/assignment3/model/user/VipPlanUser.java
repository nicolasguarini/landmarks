package bicocca2023.assignment3.model.user;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@DiscriminatorValue("VIP")
public class VipPlanUser extends User {
    public VipPlanUser() {}

    public VipPlanUser(UUID id) {
        super.setId(id);
    }

    @Override
    public String toString(){
        return "(VIP) " + super.toString();
    }
}
