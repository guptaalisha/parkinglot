package org.thoughtworks;

public interface ParkingLotObserver {
    void beingNotifiedParkingLotIsFull(ParkingLot parkingLot);

    void beingNotifiedParkingLotHasSpaceAgain(ParkingLot parkingLot);
}
