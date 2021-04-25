package echequier;

import coordonnees.Coord;
import org.junit.jupiter.api.Test;
import pieces.FabriquePiece;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReglesTest {

    @Test
    void testCheck(){
        Echiquier e = new Echiquier(new FabriquePiece(), "r6D/D7/8/8/8/8/8/8");
        ArrayList<IPiece> pieces = Regle.getPieceFromColor(e, "NOIR");
        assertTrue(Regle.check(e,new Coord(0,0),e.getPiece(new Coord(0,7))));
        assertTrue(Regle.check(e,new Coord(0,0),e.getPiece(new Coord(1,0))));
        assertFalse( Regle.checkMate(e, "BLANC", new Coord(0,0)));
        e.deplacer(1,0,7,0);

        System.out.println( e.getPiece(new Coord(7,0)));
        assertTrue(Regle.check(e,new Coord(0,0),e.getPiece(new Coord(7,0))));
        assertTrue(Regle.checkMate(e, "BLANC", new Coord(0,0)));
        e.deplacer(7,0,7,7);
        System.out.println( e.getPiece(new Coord(7,7)));
        assertTrue(Regle.check(e,new Coord(0,0),e.getPiece(new Coord(7,7))));
        assertFalse(Regle.checkMate(e, "BLANC", new Coord(0,0)));

    }
}