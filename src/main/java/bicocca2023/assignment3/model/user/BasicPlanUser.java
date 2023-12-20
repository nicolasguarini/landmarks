package bicocca2023.assignment3.model.user;

import bicocca2023.assignment3.exception.LandmarksLimitException;
import bicocca2023.assignment3.model.Landmark;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.*;

@Entity
@DiscriminatorValue(value = "BASIC")
public class BasicPlanUser extends User{
    public BasicPlanUser() {}
    public BasicPlanUser(UUID id) {
        setId(id);
    }
   private static final int MAX_LANDMARKS = 10;
   @Override
   public void addLandmark(Landmark landmark) throws LandmarksLimitException {
       if(super.getLandmarks().size() >= MAX_LANDMARKS){
           throw new LandmarksLimitException("Landmark limit reached!");
       }else{
           super.addLandmark(landmark);
       }
   }
   @Override
    public String toString(){
       return "Basic User " + super.toString();
   }
}
