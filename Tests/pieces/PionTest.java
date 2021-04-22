package pieces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PionTest {

    @Test
    void TestEstPossible() {
        Pion pion = new Pion(0,0,Couleur.BLANC);
        assertTrue(pion.estPossible(2,0));
        assertTrue(pion.estPossible(1,0));
//      assertTrue(pion.estPossible(1,1)); (test = si on prends en compte la prise en diagonale)
//      assertTrue(pion.estPossible(1, -1)); (test = si on prends en compte la prise en diagonale)
        assertFalse(pion.estPossible(5, 2));

        pion.newPos(0,0);
        assertFalse(pion.estPossible(2,0));
        assertTrue(pion.estPossible(1,0));
//      assertTrue(pion.estPossible(1,1)); (test = si on prends en compte la prise en diagonale)
//      assertTrue(pion.estPossible(-1, 1)); (test = si on prends en compte la prise en diagonale)
    }
}