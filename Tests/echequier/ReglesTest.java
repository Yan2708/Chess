package echequier;

import coordonnees.Coord;
import org.junit.jupiter.api.Test;
import pieces.FabriquePiece;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReglesTest {

    @Test
    void testCheck(){
        Echiquier e = new Echiquier(new FabriquePiece(), "r6T/8/8/8/8/8/8/8");
        ArrayList<IPiece> pieces = Regle.getPieceFromColor(e, "NOIR");
        for(IPiece p : pieces){
            assertTrue(Regle.check(e,new Coord(0,0),p));
            assertFalse( Regle.checkMate(e, "NOIR", new Coord(0,0)));
        }
        Echiquier ec = new Echiquier(new FabriquePiece(), "r6T/T7/8/8/8/8/8/8");
        for(IPiece p : pieces){
            assertTrue(Regle.check(e,new Coord(0,0),p));
            assertTrue( Regle.checkMate(e, "NOIR", new Coord(0,0)));
        }
    }
}