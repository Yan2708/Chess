package pieces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static pieces.Couleur.NOIR;

class FouTest {

    @Test
    void TestEstPossible() {
        Fou fou = new Fou(0 ,0, NOIR);
        assertTrue(fou.estPossible(1,1));
        assertTrue(fou.estPossible(-1,1));
        assertTrue(fou.estPossible(1,-1));
        assertTrue(fou.estPossible(-1,-1));
        assertTrue(fou.estPossible(5,5));
        assertTrue(fou.estPossible(-4,-4));

        assertFalse(fou.estPossible(5,0));
        assertFalse(fou.estPossible(1,2));

    }
}