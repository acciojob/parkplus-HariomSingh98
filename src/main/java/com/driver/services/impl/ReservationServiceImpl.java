package com.driver.services.impl;

import com.driver.model.SpotType;
import com.driver.model.ParkingLot;
import com.driver.model.Reservation;
import com.driver.model.Spot;
import com.driver.model.User;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        //check if user exist
        User user;
        try{
            user = userRepository3.findById(userId).get();
        }
        catch(Exception e){
            throw new Exception("Cannot make reservation");
        }
        //check if parkinglot exist
        ParkingLot parkingLot;
        try{
            parkingLot = parkingLotRepository3.findById(parkingLotId).get();
        }
        catch (Exception e){
            throw new Exception("Cannot make reservation");
        }

        Reservation reservation = new Reservation();

        Spot bookedSpot =null;
        int minPricePerHour = Integer.MAX_VALUE;
        int allowedWheels=0;
        //iterate on the spotList and find the available spot having min price

        for(Spot spot:parkingLot.getSpotList()){

            if(spot.getSpotType()== SpotType.TWO_WHEELER){
                allowedWheels = 2;
            } else if (spot.getSpotType()==SpotType.FOUR_WHEELER) {
                allowedWheels = 4;
            }
            else
                allowedWheels = Integer.MAX_VALUE;

            //check if the spot is idle for booking
            if(allowedWheels>=numberOfWheels && !spot.getOccupied() && spot.getPricePerHour()<minPricePerHour){
                minPricePerHour= spot.getPricePerHour();
                bookedSpot=spot;
            }
        }
        if(bookedSpot==null){
            throw new Exception("Cannot make reservation");
        }
        //occupy the spot
        bookedSpot.setOccupied(true);

        reservation.setNumberOfHours(timeInHours);
        reservation.setSpot(bookedSpot);
        reservation.setUser(user);

        bookedSpot.getReservationList().add(reservation);
        user.getReservationList().add(reservation);

        userRepository3.save(user);

        spotRepository3.save(bookedSpot);

        return reservation;
    }
}
