package org.thoughtworks;

import org.thoughtworks.exceptions.AllManagedParkingLotsFullException;
import org.thoughtworks.exceptions.AlreadyParkedException;
import org.thoughtworks.exceptions.ParkingLotFullException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParkingLotAttendant implements ParkingLotObserver {

    List<ParkingLot> availableParkingLots = new ArrayList<>();
    HashMap<Parkable, ParkingLot> allocation = new HashMap<>();

    public void manages(ParkingLot parkingLot) {
        if (!parkingLot.isFull()) {
            parkingLot.setObserver(this);
            availableParkingLots.add(parkingLot);
        }
    }

    public void navigate(Parkable car) throws AlreadyParkedException,
            ParkingLotFullException, AllManagedParkingLotsFullException {
        if (availableParkingLots.isEmpty())
            throw new AllManagedParkingLotsFullException("All Parking Lots are full");
        if (allocation.containsKey(car))
            throw new AlreadyParkedException("The Car is already Parked");
        ParkingLot lotToBeParked = availableParkingLots.get(0);
        lotToBeParked.park(car);
        allocation.put(car, lotToBeParked);
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
