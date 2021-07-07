package org.thoughtworks;

import java.util.ArrayList;

public class Observers extends ArrayList<ParkingLotObserver> {
    public void beingNotifiedParkingLotIsFull() {
        for (ParkingLotObserver parkingLotObserver : this) {
            parkingLotObserver.beingNotifiedParkingLotIsFull();
        }
    }
}
