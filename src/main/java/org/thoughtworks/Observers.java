package org.thoughtworks;

import java.util.ArrayList;

public class Observers extends ArrayList<ParkingLotObserver> {
    public void beingNotifiedParkingLotIsFull(ParkingLot parkingLot) {
        for (ParkingLotObserver parkingLotObserver : this) {
            parkingLotObserver.beingNotifiedParkingLotIsFull(parkingLot);
        }
    }

    public void beingNotifiedParkingLotHasSpaceAgain(ParkingLot parkingLot) {
        for (ParkingLotObserver parkingLotObserver : this) {
            parkingLotObserver.beingNotifiedParkingLotHasSpaceAgain(parkingLot);
        }
    }
}
