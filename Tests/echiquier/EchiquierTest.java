package echiquier;

import org.junit.jupiter.api.Test;
import pieces.FabriquePiece;

import java.util.ArrayList;

import static echiquier.Coord.isStraightPath;
import static echiquier.Echiquier.*;
import static org.junit.jupiter.api.Assertions.*;

class EchiquierTest {

    @Test
    void TestgetPieceFromColor(){
        Echiquier e = new Echiquier(new FabriquePiece(),"RRRRR3/R1R5/rrrrrrRr/8/8/r7/5R2/8");
        ArrayList<IPiece> noir =getPieceFromColor("NOIR");
        ArrayList<IPiece> blanc =getPieceFromColor("BLANC");
        assertEquals(noir.size(), 8);
        assertEquals(blanc.size(), 9);
        for (IPiece p : noir) {
            assertEquals(p.getPieceType(), "ROI");
        }
        for (IPiece p : blanc) {
            assertEquals(p.getPieceType(), "ROI");
        }
    }

    @Test
    void TestVoieLibre(){
        Echiquier e = new Echiquier(new FabriquePiece(),"r7/1T6/8/8/R7/8/8/8");
        assertTrue(voieLibre(getPiece(new Coord(0,0) ),new Coord(4,0)));
        assertTrue(voieLibre(getPiece(new Coord(0,0) ),new Coord(1,1)));
        assertFalse(voieLibre(getPiece(new Coord(0,0) ),new Coord(7,0)));
        assertFalse(voieLibre(getPiece(new Coord(0,0) ),new Coord(7,7)));
    }


    @Test
    void TestIsFinishValid(){
        Echiquier e = new Echiquier(new FabriquePiece(),"r7/RT6/8/8/8/8/8/8");
        assertTrue(isFinishValid(getPiece(new Coord(0,0)),new Coord(0,1)));
        assertTrue(isFinishValid(getPiece(new Coord(0,0)),new Coord(1,1)));
        assertFalse(isFinishValid(getPiece(new Coord(0,0)),new Coord(1,0)));

        assertFalse(isFinishValid(getPiece(new Coord(1,0)),new Coord(0,0)));
        assertFalse(isFinishValid(getPiece(new Coord(1,0)),new Coord(1,1)));
        assertTrue(isFinishValid(getPiece(new Coord(1,0)),new Coord(0,1)));
        assertTrue(isFinishValid(getPiece(new Coord(1,0)),new Coord(2,1)));
    }

    @Test
    void testLocateKing() throws RoiIntrouvableException {
        Echiquier e = new Echiquier(new FabriquePiece(),"r7/RT6/8/8/8/8/8/8");
        assertEquals(new Coord(0, 0), locateKing("NOIR"));
        assertEquals(new Coord(1, 0), locateKing("BLANC"));
        e.deplacer(new Coord(0, 0),new Coord(5,6));
        assertEquals(new Coord(5, 6), locateKing("NOIR"));
    }

    @Test
    void testCheckForPromote(){
        Echiquier e = new Echiquier(new FabriquePiece(),"p2P3T/RT6/8/8/PPPPPPpp/8/8/rrrrpPpc");
        e.checkForPromote("NOIR");
        e.checkForPromote("BLANC");
       System.out.println(e.toString());
        int cpt=0;
        for(IPiece p : getPieceFromColor("NOIR"))
            if(p.getPieceType().equals("DAME"))
                cpt++;
        assertEquals(3,cpt);
        cpt=0;
        for(IPiece p : getPieceFromColor("BLANC"))
            if(p.getPieceType().equals("DAME"))
                cpt++;
        assertEquals(2,cpt);
    }

//    @Test
//    void TestEchiquier(){
//        Echiquier e = new Echiquier(new FabriquePiece(),"8/1p6/8/2P5/8/8/8/8");
//        System.out.println(e.toString());
//        e.deplacer(new Coord(3,1),new Coord(3,2));
////        new Coord(3,2)
//        System.out.println(e.toString());
//        e.deplacer(new Coord(3,1),new Coord(5,1));
////        new Coord(3,2)
//        System.out.println(e.toString());
//    }
//
//    @Test
//    void testRoiIntrouvable() throws RoiIntrouvableException {
//        Echiquier e = new Echiquier(new FabriquePiece());
//        Coord cR = locateKing("BLANC");
//    }


}