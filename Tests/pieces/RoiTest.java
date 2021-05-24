package pieces;

import Joueur.FabChessJoueur;
import coordonnee.Coord;
import echiquier.Echiquier;
import org.junit.jupiter.api.Test;
import echiquier.*;

import java.util.LinkedList;
import java.util.List;

import static echiquier.Couleur.*;

import static org.junit.jupiter.api.Assertions.*;


class RoiTest {

    @Test
    void estPossible() {
        Roi r = new Roi(new Coord("d5"), BLANC);
        String[] coordPossible = {"d4","c4","c5","c6","d6","e6","e5","e4"};
        for(String coord : coordPossible)
            assertTrue(r.estPossible(new Coord(coord)));

        String[] coordImpossible = {"a1", "d3", "f7", "b2", "e3"};
        for(String coord : coordImpossible)
            assertFalse(r.estPossible(new Coord(coord)));
    }

    @Test
    void isCoupValid() {
        Roi r = new Roi(new Coord("c6"), NOIR);
        Echiquier e = new Echiquier("8/1D1p4/2r5/1P1p4/8/8/8/7R",new FPiece(), new FabChessJoueur());

        String[] coordPossible = {"c5","b5","b6","b7","c7","d6"};
        for(String coord : coordPossible)
            assertTrue(r.isCoupValid(new Coord(coord), e));

        String[] coordImpossible = {"d7", "d5"};
        for(String coord : coordImpossible)
            assertFalse(r.isCoupValid(new Coord(coord), e));
    }

    @Test
    void getAllMoves() {
        Echiquier e = new Echiquier("8/1D1p4/2r5/1P1p4/8/8/8/7R",new FPiece(), new FabChessJoueur());

        String[] coupPossible = {"b7", "d6", "c5"};
        Coord cR = e.locateSensiblePiece(NOIR);
        List<IPiece> ennemies = e.getPieceFromColor(BLANC);
        IPiece p = e.getPiece(new Coord("c6"));

        LinkedList<Coord> coupRoi = p.getAllMoves(cR, ennemies, e);
        for(String coup : coupPossible)
            assertTrue(coupRoi.contains(new Coord(coup)));
    }

}