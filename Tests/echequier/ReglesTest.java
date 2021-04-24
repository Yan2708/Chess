package echequier;

import org.junit.jupiter.api.Test;
import pieces.FabriquePiece;

import static org.junit.jupiter.api.Assertions.*;

class ReglesTest {

    @Test
    void testCheck(){
        Echiquier e = new Echiquier(new FabriquePiece(), "r6T/8/8/8/8/8/8/8");
        assertTrue(Regle.Check(e,"NOIR"));
    }
}