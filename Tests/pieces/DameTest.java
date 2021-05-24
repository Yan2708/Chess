package pieces;

import Joueur.FabChessJoueur;
import coordonnee.Coord;
import echiquier.Couleur;
import echiquier.Echiquier;
import echiquier.IPiece;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static echiquier.Couleur.BLANC;
import static echiquier.Couleur.NOIR;
import static org.junit.jupiter.api.Assertions.*;

class DameTest {

    @Test
    void estPossible() {
        Dame d = new Dame(new Coord("d5"), Couleur.BLANC);

        String[] coordPossible = {"d1","d2","d6","d8","a5","g5","f7","a8","c4","f3"};
        for(String coord : coordPossible)
            assertTrue(d.estPossible(new Coord(coord)));

        String[] coordImpossible = {"f6", "g6", "b4", "e1", "g4"};
        for(String coord : coordImpossible)
            assertFalse(d.estPossible(new Coord(coord)));
    }

    @Test
    void isCoupValid() {
        Dame d = new Dame(new Coord("a8"), Couleur.NOIR);

        Echiquier e = new Echiquier("d3p3/p7/2p5/8/8/8/8/r6R",new FabPiece(), new FabChessJoueur());

        String[] coordPossible = {"b8","c8","d8","b7"};
        for(String coord : coordPossible)
            assertTrue(d.isCoupValid(new Coord(coord),e));

        String[] coordImpossible = {"a1", "d3", "f7", "b2", "d4"};
        for(String coord : coordImpossible)
            assertFalse(d.isCoupValid(new Coord(coord),e));
    }


    @Test
    void getAllMoves() {
        Echiquier e = new Echiquier("d3p3/p7/2p5/8/8/8/8/r6R",new FabPiece(), new FabChessJoueur());

        String[] coupPossible = {"b8","c8","d8","b7"};
        Coord cR = e.locateSensiblePiece(BLANC);
        List<IPiece> ennemies = e.getPieceFromColor(NOIR);
        IPiece p = e.getPiece(new Coord("a8"));

        LinkedList<Coord> coupDame = p.getAllMoves(cR, ennemies, e);
        for(String coup : coupPossible)
            assertTrue(coupDame.contains(new Coord(coup)));
    }


}