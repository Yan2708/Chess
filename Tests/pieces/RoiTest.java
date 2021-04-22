package pieces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoiTest {

    @Test
    void TestEstPossible() {
        Roi roi = new Roi(0,0,Couleur.NOIR);

        assertTrue(roi.estPossible(1,1));
        assertTrue(roi.estPossible(1,0));
        assertTrue(roi.estPossible(0,1));
        assertTrue(roi.estPossible(-1,1));
        assertTrue(roi.estPossible(1,-1));
        assertTrue(roi.estPossible(-1,-1));
        assertTrue(roi.estPossible(0,-1));
        assertTrue(roi.estPossible(-1,0));

        assertFalse(roi.estPossible(0,0));
        assertFalse(roi.estPossible(2,1));
        assertFalse(roi.estPossible(-1,6));
        assertFalse(roi.estPossible(2,2));
    }

}