package com.driver.model;

import com.driver.Enum.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    private boolean paymentCompleted;
    @Enumerated(EnumType.STRING)
    private PaymentMode paymentMode;

    @OneToOne
    @JoinColumn
    Reservation reservation;
}
