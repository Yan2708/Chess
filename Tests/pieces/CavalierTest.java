package pieces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CavalierTest {

    @Test
    void TestEstPossible() {
        Cavalier cavalier = new Cavalier(0,0,Couleur.NOIR);
        assertTrue(cavalier.estPossible(1,2));
        assertTrue(cavalier.estPossible(1,-2));
        assertTrue(cavalier.estPossible(-1,2));
        assertTrue(cavalier.estPossible(-1,-2));
        assertTrue(cavalier.estPossible(2,1));
        assertTrue(cavalier.estPossible(-2,1));
        assertTrue(cavalier.estPossible(2,-1));
        assertTrue(cavalier.estPossible(-2,-1));

        assertFalse(cavalier.estPossible(-1,-1));
        assertFalse(cavalier.estPossible(1,-1));
        assertFalse(cavalier.estPossible(-1,-1));
        assertFalse(cavalier.estPossible(0,-1));
        assertFalse(cavalier.estPossible(-1,0));
    }
}