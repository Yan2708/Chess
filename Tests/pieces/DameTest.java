package pieces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DameTest {
    
    @Test
    void TestEstPossible(){
        Dame dame = new Dame(0,0,Couleur.NOIR);
        assertFalse(dame.estPossible(0,0));
        // Horizontales et verticales
        assertTrue(dame.estPossible(-8,0));
        assertTrue(dame.estPossible(0,-8));
        assertTrue(dame.estPossible(5,0));
        assertTrue(dame.estPossible(0,7));
        // Diagonales
        assertTrue(dame.estPossible(1,1));
        assertTrue(dame.estPossible(-1,1));
        assertTrue(dame.estPossible(1,-1));
        assertTrue(dame.estPossible(-1,-1));
        assertTrue(dame.estPossible(5,5));
        assertTrue(dame.estPossible(-4,-4));

        assertFalse(dame.estPossible(1,5));
        assertFalse(dame.estPossible(-6,9));
        assertFalse(dame.estPossible(8,5));
        assertFalse(dame.estPossible(1,2));
    }
}