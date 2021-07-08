package org.thoughtworks;

import org.thoughtworks.exceptions.AlreadyParkedException;
import org.thoughtworks.exceptions.ParkingLotFullException;

import java.util.ArrayList;
import java.util.List;

public class ParkingLotAttendant implements ParkingLotObserver{

    List<ParkingLot> managedParkingLots = new ArrayList<>();
    List<ParkingLot> availableParkingLots = new ArrayList<>();

    public void manages(ParkingLot parkingLot) {
        managedParkingLots.add(parkingLot);
        parkingLot.setObserver(this);
        if(!parkingLot.isFull()){
            availableParkingLots.add(parkingLot);
        }
    }

    public void navigate(Parkable car) throws AlreadyParkedException,ParkingLotFullException {
        availableParkingLots.get(0).park(car);
    }

    @Override
    public void beingNotifiedParkingLotIsFull(ParkingLot parkingLot) {
        availableParkingLots.remove(parkingLot);
    }

    @Override
    public void beingNotifiedParkingLotHasSpaceAgain(ParkingLot parkingLot) {
        availableParkingLots.add(parkingLot);

    }
}
