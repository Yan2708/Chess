package pieces;


import Joueur.FabChessJoueur;
import echiquier.Coord;
import echiquier.Echiquier;
import echiquier.IPiece;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static echiquier.Couleur.BLANC;
import static echiquier.Couleur.NOIR;
import static org.junit.jupiter.api.Assertions.*;

class PionTest {

    @Test
    void estPossible() {
        Pion p = new Pion(new Coord("b7"), NOIR);
        String[] coordPossible = {"b5","b6","a6","c6"};
        for(String coord : coordPossible)
            assertTrue(p.estPossible(new Coord(coord)));

        String[] coordImpossible = {"b4","a5","c5","a8","b8","c8"};
        for(String coord : coordImpossible)
            assertFalse(p.estPossible(new Coord(coord)));

        Pion p2 = new Pion(new Coord("b2"), BLANC);
        String[] coordPossible2 = {"b3","b4","a3","c3"};
        for(String coord : coordPossible2)
            assertTrue(p.estPossible(new Coord(coord)));

        String[] coordImpossible2 = {"b5","a4","c4","a1","b1","c1"};
        for(String coord : coordImpossible2)
            assertFalse(p.estPossible(new Coord(coord)));
    }

    @Test
    void isCoupValid() {
        Pion p = new Pion(new Coord("b7"), NOIR);

        Echiquier e = new Echiquier("8/1p6/P1p5/8/4p3/8/8/r6R",new FabPiece(), new FabChessJoueur());
        System.out.println(e.toString());
        String[] coordPossible = {"b5","b6","a6"};
        for(String coord : coordPossible)
            assertTrue(p.isCoupValid(new Coord(coord),e));

        String[] coordImpossible = {"c6", "b4","a5","c5","a8","b8","c8"};
        for(String coord : coordImpossible)
            assertFalse(p.isCoupValid(new Coord(coord),e));
    }

    @Test
    void autoPromote(){
        Pion pion = new Pion(new Coord("a8"),BLANC);
        IPiece p = pion.autoPromote();
        assertEquals(p.dessiner(), "D");
        Pion pion2 = new Pion(new Coord("a8"),NOIR);
        IPiece p2 = pion2.autoPromote();
        assertEquals(p2.dessiner(), "d");
    }

    @Test
    void getAllMoves() {
        Echiquier e = new Echiquier("8/1p6/P1p5/8/4p3/8/8/r6R",new FabPiece(), new FabChessJoueur());

        String[] coupPossible = {"b5","b6","a6"};
        Coord cR = e.locateSensiblePiece(NOIR);
        List<IPiece> ennemies = e.getPieceFromColor(BLANC);
        IPiece p = e.getPiece(new Coord("b7"));

        LinkedList<Coord> coupDame = p.getAllMoves(cR, ennemies, e);
        for(String coup : coupPossible)
            assertTrue(coupDame.contains(new Coord(coup)));
    }

}