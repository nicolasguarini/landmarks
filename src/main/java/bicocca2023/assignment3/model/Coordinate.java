package bicocca2023.assignment3.model;

import jakarta.persistence.*;

@Entity
@Table(name = "coordinate")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DTYPE", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("3D_POINT")

public class Coordinate extends Point {

    //...

}
