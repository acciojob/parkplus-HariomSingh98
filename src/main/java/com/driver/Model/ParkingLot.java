package com.driver.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;

    @OneToMany(mappedBy = "parkingLot",orphanRemoval = true,cascade = CascadeType.ALL)
    List<Spot> spotList = new ArrayList<>();
   //orphan removal removes the spot object in parent list when in the child the spot object is deleted
}
