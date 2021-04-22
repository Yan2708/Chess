package pieces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TourTest {

    @Test
    void TestEstPossible() {
        Tour tour = new Tour(0,0,Couleur.NOIR);
        assertFalse(tour.estPossible(0,0));

        assertTrue(tour.estPossible(-8,0));
        assertTrue(tour.estPossible(0,-8));
        assertTrue(tour.estPossible(5,0));
        assertTrue(tour.estPossible(0,7));

        assertFalse(tour.estPossible(1,5));
        assertFalse(tour.estPossible(-6,9));
        assertFalse(tour.estPossible(5,5));
    }
}