package com.driver.Model;

import com.driver.Enum.SpotType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int pricePerHour;
    @Enumerated(EnumType.STRING)
    private SpotType spotType;
    private boolean occupied;

    @ManyToOne
    @JoinColumn
    ParkingLot parkingLot;

    @OneToMany(mappedBy = "spot",cascade = CascadeType.ALL)
    List<Reservation> reservationList = new ArrayList<>();


}
