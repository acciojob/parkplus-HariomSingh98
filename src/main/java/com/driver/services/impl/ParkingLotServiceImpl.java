package com.driver.services.impl;

import com.driver.Enum.SpotType;
import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address)  {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        //create a new spot in the parkingLot with given id
        //the spot type should be the next biggest type in case the number of wheels are not 2 or 4, for 4+ wheels, it is others
       ParkingLot parkingLot ;
       parkingLot = parkingLotRepository1.findById(parkingLotId).get();
       Spot  newSpot = new Spot();
       newSpot.setOccupied(false);
       newSpot.setPricePerHour(pricePerHour);
       newSpot.setParkingLot(parkingLot);
       if(numberOfWheels<=2)newSpot.setSpotType(SpotType.TWO_WHEELER);
       else if(numberOfWheels<=4)newSpot.setSpotType(SpotType.FOUR_WHEELER);
       else newSpot.setSpotType(SpotType.OTHERS);

       parkingLot.getSpotList().add(newSpot);

       parkingLotRepository1.save(parkingLot);

       return newSpot;

    }

    @Override
    public void deleteSpot(int spotId) {


         spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
        Spot spot = new Spot();
        for(Spot s : parkingLot.getSpotList()){
            if(s.getId()==spotId){
                spot =s;
                break;
            }
        }
        spot.setPricePerHour(pricePerHour);

        spotRepository1.save(spot);

        return spot ;


    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
      parkingLotRepository1.deleteById(parkingLotId);
    }
}
