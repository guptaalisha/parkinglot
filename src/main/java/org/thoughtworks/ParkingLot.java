package org.thoughtworks;

import java.util.HashSet;
import java.util.Set;

public class ParkingLot {

    final Set<Parkable> parkedVehicles = new HashSet<>();
    private final int size;

    public ParkingLot(int size) {
        this.size = size;
    }

    public void park(Parkable car) {
        parkedVehicles.add(car);
    }
}
