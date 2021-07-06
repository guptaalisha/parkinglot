package org.thoughtworks;

import exceptions.AlreadyParkedException;
import exceptions.ParkingLotFullException;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class ParkingLotTest {

    private ParkingLot parkingLotOne;
    private ParkingLot parkingLotTwo;
    static private Parkable carOne;
    static private Parkable carTwo;

    @BeforeAll
    static void beforeAll() {
        carOne = mock(Parkable.class);
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
        void testToUnParkACarFromAParkingLot() throws AlreadyParkedException, ParkingLotFullException {
            parkingLotOne.park(carOne);

            parkingLotOne.unpark(carOne);
            Boolean actual = parkingLotOne.parkedVehicles.contains(carOne);

            assertThat(actual, equalTo(Boolean.FALSE));
        }
    }


}