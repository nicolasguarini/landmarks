package bicocca2023.assignment3.model.user;

import bicocca2023.assignment3.exception.LandmarksLimitException;
import bicocca2023.assignment3.model.Landmark;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@DiscriminatorValue("VIP")
public class VipPlanUser extends User {
    public VipPlanUser() {}

    public VipPlanUser(UUID id) {
        setId(id);
    }

    /*
    @Override
    public void addLandmark(Landmark landmark) throws LandmarksLimitException {
        super.addLandmark(landmark);
    }*/
    @Override
    public String toString(){
        return "VIP User " + super.toString();
    }

}
