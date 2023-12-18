package bicocca2023.assignment3.model.user;

import bicocca2023.assignment3.exception.LandmarksLimitException;
import bicocca2023.assignment3.model.Landmark;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("VIP")
public class VipPlanUser extends User {
    public VipPlanUser() {}

    @Override
    public void addLandmark(Landmark landmark) throws LandmarksLimitException {
        super.addLandmark(landmark);
    }

    @Override
    public String toString(){
        return "VIP User " + super.toString();
    }

}
