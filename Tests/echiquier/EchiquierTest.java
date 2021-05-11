package echiquier;

import coordonnee.Coord;
import org.junit.jupiter.api.Test;
import pieces.FabriquePiece;
import static echiquier.Couleur.*;


import java.util.ArrayList;

import static echiquier.Echiquier.*;
import static org.junit.jupiter.api.Assertions.*;

class EchiquierTest {

    /** test le renvoie de pieces selon la couleur donnée*/
    @Test
    void TestgetPieceFromColor(){
        Echiquier e = new Echiquier(new FabriquePiece(),"RRRRR3/R1R5/rrrrrrRr/8/8/r7/5R2/8");
        System.out.println(e);
        ArrayList<IPiece> noir =getPieceFromColor(NOIR);
        ArrayList<IPiece> blanc =getPieceFromColor(BLANC);
        assertEquals(noir.size(), 8);   //il y a 8 roi noir dans la fen
        assertEquals(blanc.size(), 9);  //9 roi blanc dans la fen
        for (IPiece p : noir) {
            assertEquals(p.getPieceType(), "ROI");
        }
        for (IPiece p : blanc) {
            assertEquals(p.getPieceType(), "ROI");
        }
    }

    /** test la verification pour deux coords si le chemin n'a pas d'obstacle*/
    @Test
    void TestVoieLibre(){
        Echiquier e = new Echiquier(new FabriquePiece(),"r7/1T6/8/8/R7/8/8/8");
        System.out.println(e);
        assertTrue(Regle.voieLibre(getPiece(new Coord(0,0) ),new Coord(4,0)));
        assertTrue(Regle.voieLibre(getPiece(new Coord(0,0) ),new Coord(1,1)));
        assertFalse(Regle.voieLibre(getPiece(new Coord(0,0) ),new Coord(7,0)));
        assertFalse(Regle.voieLibre(getPiece(new Coord(0,0) ),new Coord(7,7)));
    }

    /** test si l'arrivé d'une piece vers une coordonné est valide (couleur differente) */
    @Test
    void TestIsFinishValid(){
        Echiquier e = new Echiquier(new FabriquePiece(),"r7/RT6/8/8/8/8/8/8");
        System.out.println(e);
        assertTrue(Regle.isFinishValid(getPiece(new Coord(0,0)),new Coord(0,1)));
        assertTrue(Regle.isFinishValid(getPiece(new Coord(0,0)),new Coord(1,1)));
        assertFalse(Regle.isFinishValid(getPiece(new Coord(0,0)),new Coord(1,0)));

        assertFalse(Regle.isFinishValid(getPiece(new Coord(1,0)),new Coord(0,0)));
        assertFalse(Regle.isFinishValid(getPiece(new Coord(1,0)),new Coord(1,1)));
        assertTrue(Regle.isFinishValid(getPiece(new Coord(1,0)),new Coord(0,1)));
        assertTrue(Regle.isFinishValid(getPiece(new Coord(1,0)),new Coord(2,1)));
    }

    /** test la localisation du roi*/
    @Test
    void testLocateKing() {
        Echiquier e = new Echiquier(new FabriquePiece(),"r7/RT6/8/8/8/8/8/8");
        System.out.println(e);
        assertEquals(new Coord(0, 0), locateSensiblePiece(NOIR));
        assertEquals(new Coord(1, 0), locateSensiblePiece(BLANC));
        e.deplacer(new Coord(0, 0),new Coord(5,6));
        assertEquals(new Coord(5, 6), locateSensiblePiece(NOIR));
    }

    /** test la detection de promotion et la promotion des pions en dame*/
    @Test
    void testCheckForPromote(){
        Echiquier e = new Echiquier(new FabriquePiece(),"p2P3T/RT6/8/8/PPPPPPpp/8/8/rrrrpPpc");
        e.checkForPromote(NOIR);
        e.checkForPromote(BLANC);
       System.out.println(e.toString());
        int cpt=0;
        for(IPiece p : getPieceFromColor(NOIR))
            if(p.getPieceType().equals("DAME"))
                cpt++;
        assertEquals(3,cpt);
        cpt=0;
        for(IPiece p : getPieceFromColor(BLANC))
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
//        Echiquier e = new Echiquier(new FabriquePiece(), "8/8/8/8/8/8/8/ppppppppppppppppppppppppppp");
//        Coord cR = locateKing("BLANC");
//    }
}