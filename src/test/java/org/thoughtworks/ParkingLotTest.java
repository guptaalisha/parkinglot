package org.thoughtworks;

import org.thoughtworks.exceptions.AlreadyParkedException;
import org.thoughtworks.exceptions.NotParkedException;
import org.thoughtworks.exceptions.ParkingLotFullException;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ParkingLotTest {

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

    @Nested
    @DisplayName("Park Car")
    class ParkCar {

        @Test
        void testToParkACarInAParkingLot() throws Exception {
            parkingLotOne.park(carOne);
            Boolean actual = parkingLotOne.parkedVehicles.contains(carOne);

            assertThat(actual, equalTo(Boolean.TRUE));
        }

        @Test
        void testThrowsExceptionForAlreadyParkedCar() throws Exception {
            parkingLotTwo.park(carOne);

            AlreadyParkedException actual = assertThrows(AlreadyParkedException.class, () -> {
                parkingLotTwo.park(carOne);
            });

            assertThat(actual.getMessage(), equalTo("Cannot park an already parked car"));
        }

        @Test
        void testThrowsExceptionWhenParkingLotIsFull() throws Exception {
            parkingLotOne.park(carOne);

            ParkingLotFullException actual = assertThrows(ParkingLotFullException.class, () -> {
                parkingLotOne.park(carTwo);
            });

            assertThat(actual.getMessage(), equalTo("Parking lot size is full"));
        }
    }

    @Nested
    @DisplayName("Un-park Car")
    class UnparkCar {

        @Test
        void testToUnParkACarFromAParkingLot() throws Exception {
            parkingLotOne.park(carOne);

            parkingLotOne.unpark(carOne);
            Boolean actual = parkingLotOne.parkedVehicles.contains(carOne);

            assertThat(actual, equalTo(Boolean.FALSE));
        }

        @Test
        void testThrowsExceptionForAlreadyUnParkedCar() throws Exception {
            parkingLotOne.park(carOne);
            parkingLotOne.unpark(carOne);

            NotParkedException actual = assertThrows(NotParkedException.class, () -> {
                parkingLotOne.unpark(carOne);
            });

            assertThat(actual.getMessage(), equalTo("Cannot unpark a car which is not parked"));
        }
    }

    @Nested
    @DisplayName("Notify Observers")
    class NotifyObservers {

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
}