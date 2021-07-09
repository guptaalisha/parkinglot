package org.thoughtworks;

import java.util.HashSet;
import java.util.Set;

import org.thoughtworks.exceptions.AlreadyParkedException;
import org.thoughtworks.exceptions.NotParkedException;
import org.thoughtworks.exceptions.ParkingLotFullException;

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
        if (isFull())
            throw new ParkingLotFullException("Parking lot size is full");
        if (parkedVehicles.contains(car))
            throw new AlreadyParkedException("Cannot park an already parked car");
        parkedVehicles.add(car);
        if (isFull() && parkinglotObservers != null)
            parkinglotObservers.beingNotifiedParkingLotIsFull(this);
    }

    public void unpark(Parkable car) throws NotParkedException {
        if (!parkedVehicles.contains(car))
            throw new NotParkedException("Cannot unpark a car which is not parked");
        parkedVehicles.remove(car);
        if (hasSpaceAgain() && this.parkinglotObservers != null)
            parkinglotObservers.beingNotifiedParkingLotHasSpaceAgain(this);

    }

    private Boolean hasSpaceAgain() {
        return parkedVehicles.size() == size - 1;
    }

    public void setObserver(ParkingLotObserver observer) {
        this.parkinglotObservers.add(observer);
    }

    Boolean isFull() {
        return this.size == parkedVehicles.size();
    }
}
