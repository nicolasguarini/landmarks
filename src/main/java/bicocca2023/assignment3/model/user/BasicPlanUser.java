package bicocca2023.assignment3.model.user;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "BASIC")
public class BasicPlanUser extends User{
    
}
