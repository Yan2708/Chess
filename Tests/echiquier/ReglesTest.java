package echiquier;

import static echiquier.Echiquier.*;
import static echiquier.Regle.*;

import org.junit.jupiter.api.Test;
import pieces.FabriquePiece;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReglesTest {

    @Test
    void testCheck() throws RoiIntrouvableException {
        Echiquier e = new Echiquier(new FabriquePiece(), "r6T/8/8/8/8/8/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(checkIfCheck(cR, pieces));
    }

    @Test
    void testCheckMate() throws RoiIntrouvableException {
        Echiquier e = new Echiquier(new FabriquePiece(), "r6T/7T/8/8/8/8/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(checkForMate("NOIR",cR, pieces));
    }

    @Test
    void testCheckMate1() throws RoiIntrouvableException {
        Echiquier e = new Echiquier(new FabriquePiece(), "T4r2/8/5R2/8/8/8/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(checkForMate("NOIR",cR, pieces));
    }

    @Test
    void testCheckMate2() throws RoiIntrouvableException {
        Echiquier e = new Echiquier(new FabriquePiece(), "ppprpppp/ppc1pppp/pF6/8/8/3D4/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(checkForMate("NOIR",cR, pieces));
    }

    @Test
    void testCheckMate3() throws RoiIntrouvableException {
        Echiquier e = new Echiquier(new FabriquePiece(), "3r4/3D4/8/8/8/8/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertFalse(checkForMate("NOIR",cR, pieces));
    }

    @Test
    void testCheckMate4() throws RoiIntrouvableException {
        Echiquier e = new Echiquier(new FabriquePiece(), "prp5/p1p5/8/8/8/1T6/f7/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertFalse(checkForMate("NOIR",cR, pieces));
    }
}