package echiquier;

import static echiquier.Echiquier.*;
import static echiquier.Regle.*;

import org.junit.jupiter.api.Test;
import pieces.FabriquePiece;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ReglesTest {

    /*
      Certaine fens sont generés a l'aide de : https://lichess.org/editor
      Les patternes sont inspirés de : https://en.wikipedia.org/wiki/Checkmate_pattern
      */


    /** test la verification des coups pour les Pion*/
    @Test
    void testIsCoupValidForPawn(){
        Echiquier e = new Echiquier(new FabriquePiece(),"R7/8/8/1p3p2/2P1P3/8/8/r7");
        System.out.println(e.toString());
        // coup : c4b5
        assertTrue(isCoupValid(new Coord(3, 1), getPiece(new Coord(4, 2))));
        // coup : f5e4
        assertTrue(isCoupValid(new Coord(4, 4), getPiece(new Coord(3, 5))));
        // coup : c4d5
        assertFalse(isCoupValid(new Coord(3, 3), getPiece(new Coord(4, 2))));
        // coup : f5g5
        assertFalse(isCoupValid(new Coord(4, 6), getPiece(new Coord(3, 5))));
    }

    /** test la detection d'echec du roi*/
    @Test
    void testCheck() {
        Echiquier e = new Echiquier(new FabriquePiece(), "r6T/8/8/8/8/8/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(isAttacked(cR, pieces));
    }

    /** test l'echec et mat de l'escalier*/
    @Test
    void testCheckMate() {
        Echiquier e = new Echiquier(new FabriquePiece(), "r6T/7T/8/8/8/8/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(checkForMate("NOIR",cR, pieces));
    }

    /** test l'echec et mat du roi et de la tour*/
    @Test
    void testCheckMate1() {
        Echiquier e = new Echiquier(new FabriquePiece(), "T4r2/8/5R2/8/8/8/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(checkForMate("NOIR",cR, pieces));
    }

    /** test l'echec et mat avec un piece pouvant defendre mais etant cloué*/
    @Test
    void testCheckMate2() {
        Echiquier e = new Echiquier(new FabriquePiece(), "ppprpppp/ppc1pppp/pF6/8/8/3D4/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(checkForMate("NOIR",cR, pieces));
    }

    /** test l'echec et mat defendue par le roi prenant la dame*/
    @Test
    void testCheckMate3() {
        Echiquier e = new Echiquier(new FabriquePiece(), "3r4/3D4/8/8/8/8/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertFalse(checkForMate("NOIR",cR, pieces));
    }

    /** test la defense d'un echec et mat avec le fou pouvant prendre la tour*/
    @Test
    void testCheckMate4() {
        Echiquier e = new Echiquier(new FabriquePiece(), "prp5/p1p5/8/8/8/1T6/f7/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertFalse(checkForMate("NOIR",cR, pieces));
    }

    /** test l'echec et mat avec la reine etant defendu*/
    @Test
    void testCheckMate5() {
        Echiquier e = new Echiquier(new FabriquePiece(), "3r4/3D4/8/8/8/8/8/3T4");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(checkForMate("NOIR",cR, pieces));
    }

    /** test avec la position de testCheckMate5 mais avec un cavalier pouvant defendre mais cloué*/
    @Test
    void testCheckMate6() {
        Echiquier e = new Echiquier(new FabriquePiece(), "3r1c1T/3D4/8/8/8/8/8/3T4");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(checkForMate("NOIR",cR, pieces));
    }

    /** test de l'echec et mat avec un cavalier et un fou*/
    @Test
    void testCheckMate7() {
        Echiquier e = new Echiquier(new FabriquePiece(), "7r/8/5FRC/8/8/8/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(checkForMate("NOIR",cR, pieces));
    }

    /** test d'une double mise en echec*/
    @Test
    void testCheckMate8() {
        Echiquier e = new Echiquier(new FabriquePiece(), "8/4r3/8/8/1F5F/8/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertFalse(checkForMate("NOIR",cR, pieces));
    }

    /** test du "BlackBurnes'mate" https://chessfox.com/blackburnes-mate/*/
    @Test
    void testCheckMate9() {
        Echiquier e = new Echiquier(new FabriquePiece(), "5tr1/7F/8/6C1/8/2F5/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> pieces = getPieceFromColor("BLANC");
        Coord cR = locateKing("NOIR");
        assertTrue(checkForMate("NOIR",cR, pieces));
    }

    /** test de l'echec et mat avec le coup du berger*/
    @Test
    void testCheckMate10() {
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

    /** test la detection d'egalité par pat au tour des BLANCs*/
    @Test
    void testStaleMate() {
        Echiquier e = new Echiquier(new FabriquePiece(), "RF5t/8/1r6/8/8/8/8/8");
        System.out.println(e.toString());
        ArrayList<IPiece> ennemies = getPieceFromColor("NOIR");
        ArrayList<IPiece> allys = getPieceFromColor("BLANC");
        Coord cR = locateKing("BLANC");
        assertTrue(isStaleMate(ennemies, allys, "NOIR", cR));
    }

    /** test la detection d'egalité par pat*/
    @Test
    void testStaleMate2() {
        Echiquier e = new Echiquier(new FabriquePiece(), "8/8/8/8/8/2r5/1t6/R7");
        System.out.println(e.toString());
        ArrayList<IPiece> ennemies = getPieceFromColor("NOIR");
        ArrayList<IPiece> allys = getPieceFromColor("BLANC");
        Coord cR = locateKing("BLANC");
        assertTrue(isStaleMate(ennemies, allys, "NOIR", cR));
    }
}