package org.thoughtworks;

import java.util.HashSet;
import java.util.Set;

import exceptions.AlreadyParkedException;
import exceptions.NotParkedException;
import exceptions.ParkingLotFullException;

public class ParkingLot {

    final Set<Parkable> parkedVehicles;
    private final int size;
    private Observers parkinglotObservers;

    public ParkingLot(int size) {
        parkedVehicles = new HashSet<>();
        parkinglotObservers = new Observers();
        this.size = size;
    }

    public void park(Parkable car) throws AlreadyParkedException, ParkingLotFullException {
        if (parkingLotIsFull())
            throw new ParkingLotFullException("Parking lot size is full");
        if (parkedVehicles.contains(car))
            throw new AlreadyParkedException("Cannot park an already parked car");
        parkedVehicles.add(car);
        if (parkingLotIsFull() && this.parkinglotObservers != null)
            parkinglotObservers.beingNotifiedParkingLotIsFull();
    }

    public void unpark(Parkable car) throws NotParkedException {
        if (!parkedVehicles.contains(car))
            throw new NotParkedException("Cannot unpark a car which is not parked");
        if (parkingLotIsFull() && this.parkinglotObservers != null)
            parkinglotObservers.beingNotifiedParkingLotHasSpaceAgain();
        parkedVehicles.remove(car);
    }

    public void setObserver(ParkingLotObserver observer) {
        this.parkinglotObservers.add(observer);
    }

    private Boolean parkingLotIsFull() {
        return this.size == parkedVehicles.size();
    }
}
