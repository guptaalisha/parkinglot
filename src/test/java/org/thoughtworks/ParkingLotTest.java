package org.thoughtworks;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

class ParkingLotTest {

    @Test
    void testToParkACarInAParkingLot() {
        ParkingLot parkingLotOne = new ParkingLot(1);
        Parkable carOne = mock(Parkable.class);

        parkingLotOne.park(carOne);
        Boolean actual = parkingLotOne.parkedVehicles.contains(carOne);

        assertThat(actual, equalTo(Boolean.TRUE));
    }
}