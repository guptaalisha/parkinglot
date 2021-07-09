package org.thoughtworks;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ParkingLotObserverTest {
    private ParkingLot parkingLotOne;
    private ParkingLot parkingLotTwo;
    static private Parkable carOne;
    static private Parkable carTwo;
    static private ParkingLotObserver parkingLotOwner;
    static private ParkingLotObserver trafficCop;

    @BeforeAll
    static void beforeAll() {
        carOne = mock(Parkable.class);
        carTwo = mock(Parkable.class);
    }

    @BeforeEach
    void beforeEach() {
        parkingLotOne = new ParkingLot(1);
        parkingLotTwo = new ParkingLot(2);
        parkingLotOwner = mock(ParkingLotObserver.class);
        trafficCop = mock(ParkingLotObserver.class);
    }

    @Test
    public void testNotifyParkingLotOwnerWhenParkingLotIsFull() throws Exception {
        parkingLotOne.setObserver(parkingLotOwner);

        parkingLotOne.park(carOne);

        verify(parkingLotOwner, times(1)).beingNotifiedParkingLotIsFull(parkingLotOne);
    }

    @Test
    public void testNotToNotifyParkingLotOwnerWhenParkingLotIsNotFull() throws Exception {
        parkingLotTwo.setObserver(parkingLotOwner);

        parkingLotTwo.park(carOne);

        verify(parkingLotOwner, never()).beingNotifiedParkingLotIsFull(parkingLotTwo);
    }

    @Test
    public void testNotifyTrafficCopWhenParkingLotIsFull() throws Exception {
        parkingLotOne.setObserver(trafficCop);

        parkingLotOne.park(carOne);

        verify(trafficCop, times(1)).beingNotifiedParkingLotIsFull(parkingLotOne);
    }

    @Test
    public void testNotToNotifyTrafficCopWhenParkingLotIsNotFull() throws Exception {
        parkingLotTwo.setObserver(trafficCop);

        parkingLotTwo.park(carOne);

        verify(trafficCop, never()).beingNotifiedParkingLotIsFull(parkingLotTwo);
    }

    @Test
    public void testNotifyObserversWhenParkingLotHasSpace() throws Exception {
        parkingLotOne.setObserver(trafficCop);
        parkingLotOne.setObserver(parkingLotOwner);
        parkingLotOne.park(carOne);

        parkingLotOne.unpark(carOne);

        verify(trafficCop, times(1)).beingNotifiedParkingLotHasSpaceAgain(parkingLotOne);
        verify(parkingLotOwner, times(1)).beingNotifiedParkingLotHasSpaceAgain(parkingLotOne);
    }

    @Test
    public void testNotifyObserversWhenParkingLotHasSpaceAgain() throws Exception {
        parkingLotTwo.setObserver(trafficCop);
        parkingLotTwo.setObserver(parkingLotOwner);
        parkingLotTwo.park(carOne);
        parkingLotTwo.park(carTwo);

        parkingLotTwo.unpark(carTwo);

        verify(trafficCop, times(1)).beingNotifiedParkingLotHasSpaceAgain(parkingLotTwo);
        verify(parkingLotOwner, times(1)).beingNotifiedParkingLotHasSpaceAgain(parkingLotTwo);
    }
}