package org.thoughtworks;

import exceptions.AlreadyParkedException;
import exceptions.NotParkedException;
import exceptions.ParkingLotFullException;
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
    static private Person parkingLotOwner;
    static private Person trafficCop;

    @BeforeAll
    static void beforeAll() {
        carOne = mock(Parkable.class);
        parkingLotOwner = mock(Person.class);
        trafficCop = mock(Person.class);
    }

    @BeforeEach
    void beforeEach() {
        parkingLotOne = new ParkingLot(1);
        parkingLotTwo = new ParkingLot(2);
    }

    @Nested
    @DisplayName("Park Car")
    class ParkCar {

        @Test
        void testToParkACarInAParkingLot() throws AlreadyParkedException, ParkingLotFullException {

            parkingLotOne.park(carOne);
            Boolean actual = parkingLotOne.parkedVehicles.contains(carOne);

            assertThat(actual, equalTo(Boolean.TRUE));
        }

        @Test
        void testThrowsExceptionForAlreadyParkedCar() throws AlreadyParkedException, ParkingLotFullException {
            parkingLotTwo.park(carOne);

            AlreadyParkedException actual = assertThrows(AlreadyParkedException.class, () -> {
                parkingLotTwo.park(carOne);
            });

            assertThat(actual.getMessage(), equalTo("Cannot park an already parked car"));
        }

        @Test
        void testThrowsExceptionWhenParkingLotIsFull() throws ParkingLotFullException, AlreadyParkedException {
            carTwo = mock(Parkable.class);
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
        void testToUnParkACarFromAParkingLot() throws AlreadyParkedException,
                ParkingLotFullException, NotParkedException {
            parkingLotOne.park(carOne);

            parkingLotOne.unpark(carOne);
            Boolean actual = parkingLotOne.parkedVehicles.contains(carOne);

            assertThat(actual, equalTo(Boolean.FALSE));
        }

        @Test
        void testThrowsExceptionForAlreadyUnParkedCar() throws AlreadyParkedException,
                ParkingLotFullException, NotParkedException {
            parkingLotOne.park(carOne);
            parkingLotOne.unpark(carOne);
            NotParkedException actual = assertThrows(NotParkedException.class, () -> {
                parkingLotOne.unpark(carOne);
            });

            assertThat(actual.getMessage(), equalTo("Cannot unpark a car which is not parked"));
        }
    }

    @Nested
    @DisplayName("Notify Parking lot owner")
    class NotifyOwner {

        @Test
        public void testNotifyParkingLotOwnerWhenParkingLotIsFull()
                throws ParkingLotFullException, AlreadyParkedException {
            parkingLotOne.setOwner(parkingLotOwner);
            parkingLotOne.park(carOne);
            verify(parkingLotOwner, times(1)).notifyParkingLotIsFull();
        }

        @Test
        public void testNotToNotifyParkingLotOwnerWhenParkingLotIsNotFull()
                throws ParkingLotFullException, AlreadyParkedException {
            parkingLotTwo.setOwner(parkingLotOwner);
            parkingLotTwo.park(carOne);
            verify(parkingLotOwner, never()).notifyParkingLotIsFull();
        }
    }

    @Nested
    @DisplayName("Notify road traffic cop")
    class NotifyTrafficCop {

        @Test
        public void testNotifyTrafficCopWhenParkingLotIsFull()
                throws ParkingLotFullException, AlreadyParkedException {
            parkingLotOne.setTrafficCopIncharge(trafficCop);
            parkingLotOne.park(carOne);
            verify(trafficCop, times(1)).notifyParkingLotIsFull();
        }
    }
}