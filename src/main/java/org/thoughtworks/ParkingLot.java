package org.thoughtworks;

import java.util.HashSet;
import java.util.Set;

import exceptions.AlreadyParkedException;
import exceptions.NotParkedException;
import exceptions.ParkingLotFullException;

public class ParkingLot {

    final Set<Parkable> parkedVehicles = new HashSet<>();
    private final int size;
    Person parkingLotOwner;
    Person trafficCopIncharge;

    public ParkingLot(int size) {
        this.size = size;
    }

    private Boolean parkingLotIsFull() {
        return this.size == parkedVehicles.size();
    }

    public void park(Parkable car) throws AlreadyParkedException, ParkingLotFullException {
        if (parkingLotIsFull())
            throw new ParkingLotFullException("Parking lot size is full");
        if (parkedVehicles.contains(car))
            throw new AlreadyParkedException("Cannot park an already parked car");
        parkedVehicles.add(car);
        if (parkingLotIsFull() && this.parkingLotOwner != null)
            parkingLotOwner.notifyParkingLotIsFull();
        if (parkingLotIsFull() && this.trafficCopIncharge != null)
            trafficCopIncharge.notifyParkingLotIsFull();
    }

    public void unpark(Parkable car) throws NotParkedException {
        if (!parkedVehicles.contains(car))
            throw new NotParkedException("Cannot unpark a car which is not parked");
        parkedVehicles.remove(car);
    }

    public void setOwner(Person parkingLotOwner) {
        this.parkingLotOwner = parkingLotOwner;
    }

    public void setTrafficCopIncharge(Person trafficCop) {
        this.trafficCopIncharge = trafficCop;
    }
}
