package org.thoughtworks;

import java.util.HashSet;
import java.util.Set;

import exceptions.AlreadyParkedException;

public class ParkingLot {

    final Set<Parkable> parkedVehicles = new HashSet<>();
    private final int size;

    public ParkingLot(int size) {
        this.size = size;
    }

    public void park(Parkable car) throws AlreadyParkedException {
        if (parkedVehicles.contains(car))
            throw new AlreadyParkedException("Cannot park an already parked car");
        parkedVehicles.add(car);
    }
}
