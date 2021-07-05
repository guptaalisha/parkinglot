package org.thoughtworks;

import exceptions.AlreadyParkedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class ParkingLotTest {
    private ParkingLot parkingLotOne;
    private ParkingLot parkingLotTwo;
    static private Parkable carOne;

    @BeforeEach
    void beforeEach() {
        parkingLotOne = new ParkingLot(1);
        parkingLotTwo = new ParkingLot(2);
        carOne = mock(Parkable.class);
    }

    @Test
    void testToParkACarInAParkingLot() throws AlreadyParkedException {

        parkingLotOne.park(carOne);
        Boolean actual = parkingLotOne.parkedVehicles.contains(carOne);

        assertThat(actual, equalTo(Boolean.TRUE));
    }

    @Test
    void testThrowsExceptionForAlreadyParkedCar() throws AlreadyParkedException {

        parkingLotTwo.park(carOne);
        AlreadyParkedException actual = assertThrows(AlreadyParkedException.class, () -> {
            parkingLotTwo.park(carOne);
        });

        assertThat(actual.getMessage(), equalTo("Cannot park an already parked car"));
    }

}