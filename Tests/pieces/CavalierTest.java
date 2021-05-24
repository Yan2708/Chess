package pieces;

import Joueur.FabChessJoueur;
import echiquier.Coord;
import echiquier.Couleur;
import echiquier.Echiquier;
import echiquier.IPiece;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static echiquier.Couleur.BLANC;
import static echiquier.Couleur.NOIR;
import static org.junit.jupiter.api.Assertions.*;

class CavalierTest {

    @Test
    void estPossible() {
        Cavalier c = new Cavalier(new Coord("d5"), Couleur.BLANC);

        String[] coordPossible = {"b6","c7","e7","f6","f4","e3","c3","b4"};
        for(String coord : coordPossible)
            assertTrue(c.estPossible(new Coord(coord)));

        String[] coordImpossible = {"a1", "d3", "f7", "b2", "d4"};
        for(String coord : coordImpossible)
            assertFalse(c.estPossible(new Coord(coord)));
    }

    @Test
    void isCoupValid() {
        Cavalier c = new Cavalier(new Coord("d5"), Couleur.BLANC);
        Echiquier e = new Echiquier("8/1D1p4/2r5/3C4/8/8/8/7R",new FabPiece(), new FabChessJoueur());

        String[] coordPossible = {"b6","c7","e7","f6","f4","e3","c3","b4"};
        for(String coord : coordPossible)
            assertTrue(c.isCoupValid(new Coord(coord),e));

        String[] coordImpossible = {"a1", "d3", "f7", "b2", "d4"};
        for(String coord : coordImpossible)
            assertFalse(c.isCoupValid(new Coord(coord),e));
    }

    @Test
    void getAllMoves() {
        Echiquier e = new Echiquier("r7/1D1p4/2c5/3C4/8/8/8/7R",new FabPiece(), new FabChessJoueur());

        String[] coupPossible = {"b6","c7","e7","f6","f4","e3","c3","b4"};
        Coord cR = e.locateSensiblePiece(BLANC);
        List<IPiece> ennemies = e.getPieceFromColor(NOIR);
        IPiece p = e.getPiece(new Coord("d5"));

        LinkedList<Coord> coupCavalier = p.getAllMoves(cR, ennemies, e);
        for(String coup : coupPossible)
            assertTrue(coupCavalier.contains(new Coord(coup)));
    }


}