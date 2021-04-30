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

    @Test
    void testCheckMate5() throws RoiIntrouvableException {
        Echiquier e = new Echiquier(new FabriquePiece(), "3r4/3D4/8/8/8/8/8/3T4");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(checkForMate("NOIR",cR, pieces));
    }

    @Test
    void testCheckMate6() throws RoiIntrouvableException {
        Echiquier e = new Echiquier(new FabriquePiece(), "3r1c1T/3D4/8/8/8/8/8/3T4");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(checkForMate("NOIR",cR, pieces));
    }

    @Test
    void testCheckMate7() throws RoiIntrouvableException {
        Echiquier e = new Echiquier(new FabriquePiece(), "7r/8/5FRC/8/8/8/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(checkForMate("NOIR",cR, pieces));
    }

    @Test
    void testCheckMate8() throws RoiIntrouvableException {
        Echiquier e = new Echiquier(new FabriquePiece(), "8/4r3/8/8/1F5F/8/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertFalse(checkForMate("NOIR",cR, pieces));
    }

    @Test
    void testCheckMate9() throws RoiIntrouvableException {
        Echiquier e = new Echiquier(new FabriquePiece(), "5tr1/7F/8/6C1/8/2F5/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(checkForMate("NOIR",cR, pieces));
    }

    @Test
    void testCheckMate10() throws RoiIntrouvableException {
        Echiquier e = new Echiquier(new FabriquePiece(), "tcfdrfct/pppppDpp/8/8/2F5/8/PPPPPPPP/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(checkForMate("NOIR",cR, pieces));
    }


//    @Test
//    void  testPriseEnPassant(){
//        Echiquier e = new Echiquier(new FabriquePiece(), "8/8/8/2PPpP2/8/8/8/8");
//        System.out.println(e.toString());
//        Coord noir = new Coord(3,4);
//        Coord blanc0 = new Coord(3,2);
//        Coord blanc1 = new Coord(3,3);
//        Coord blanc2 = new Coord(3,5);
//        //piece noire
//        assertTrue(Regle.priseEnPassant(new Coord(5,3),blanc1,noir,new Coord(4,3)));
//        assertTrue(Regle.priseEnPassant(new Coord(5,5),blanc2,noir,new Coord(4,5)));
//        assertFalse(Regle.priseEnPassant(new Coord(5,5),blanc2,noir,new Coord(4,4)));
//        //piece blanche
//        assertTrue(Regle.priseEnPassant(new Coord(5,5),blanc2,noir,new Coord(4,5)));
//        //le pion blanc ne bouge que d'une case
//        assertFalse(Regle.priseEnPassant(new Coord(4,5),blanc2,noir,new Coord(4,5)));
//        //test sur la meme couleur
//        assertFalse(Regle.priseEnPassant(new Coord(5,2),blanc0,blanc1,new Coord(2,2)));
//    }

    @Test
    void testStaleMate() throws RoiIntrouvableException{
        Echiquier e = new Echiquier(new FabriquePiece(), "RF5t/8/1r6/8/8/8/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> ennemies = getPieceFromColor("NOIR");
        ArrayList<IPiece> allys = getPieceFromColor("BLANC");
        Coord cR = locateKing("BLANC");
        assertTrue(isStaleMate(ennemies, allys, "NOIR", cR));
    }

    @Test
    void testStaleMate2() throws RoiIntrouvableException{
        Echiquier e = new Echiquier(new FabriquePiece(), "8/8/8/8/8/2r5/1t6/R7");
        System.out.println(e.toString());
        ArrayList<IPiece> ennemies = getPieceFromColor("NOIR");
        ArrayList<IPiece> allys = getPieceFromColor("BLANC");
        Coord cR = locateKing("BLANC");
        assertTrue(isStaleMate(ennemies, allys, "NOIR", cR));
    }
}