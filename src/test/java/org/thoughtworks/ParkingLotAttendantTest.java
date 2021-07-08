package org.thoughtworks;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.thoughtworks.exceptions.AlreadyParkedException;
import org.thoughtworks.exceptions.ParkingLotFullException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class ParkingLotAttendantTest {

    private ParkingLot parkingLotThree;
    private ParkingLot parkingLotTwo;
    static private Parkable carOne;
    static private Parkable carTwo;
    static private Parkable carThree;
    static private Parkable carFour;
    static private Parkable carFive;
    static private Parkable carSix;

    @BeforeAll
    static void beforeAll() {
        carOne = mock(Parkable.class);
        carTwo = mock(Parkable.class);
        carThree = mock(Parkable.class);
        carFour = mock(Parkable.class);
        carFive = mock(Parkable.class);
        carSix = mock(Parkable.class);
    }

    @BeforeEach
    void beforeEach() {
        parkingLotThree = new ParkingLot(3);
        parkingLotTwo = new ParkingLot(2);
    }

    @Test
    public void testToParkACarInTheOnlyManagedParkingLot() throws Exception {
        ParkingLotAttendant attendant = new ParkingLotAttendant();
        attendant.manages(parkingLotTwo);
        attendant.navigate(carOne);
    }

    @Test
    public void testToParkACarInTheFirstManagedParkingLot() throws Exception {
        ParkingLotAttendant attendant = new ParkingLotAttendant();
        attendant.manages(parkingLotTwo);
        attendant.manages(parkingLotThree);
        attendant.navigate(carOne);
        assertThrows(AlreadyParkedException.class, ()-> {parkingLotTwo.park(carOne);});
    }

    @Test
    public void testToParkACarInTheNextAvailableManagedParkingLot() throws Exception {
        ParkingLotAttendant attendant = new ParkingLotAttendant();
        attendant.manages(parkingLotTwo);
        attendant.manages(parkingLotThree);
        attendant.navigate(carOne);
        attendant.navigate(carTwo);
        attendant.navigate(carThree);

//        assertThat(parkingLotThree.parkedVehicles.contains(carThree),is(Boolean.TRUE));
        assertThrows(AlreadyParkedException.class, ()->
                parkingLotThree.park(carThree));

    }
}
