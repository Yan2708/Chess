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

class FouTest {

    @Test
    void estPossible() {
        Fou f = new Fou(new Coord("d5"), Couleur.BLANC);

        String[] coordPossible = {"b7","e6","f3","a2"};
        for(String coord : coordPossible)
            assertTrue(f.estPossible(new Coord(coord)));

        String[] coordImpossible = {"f6", "d4", "b4", "e1", "g4"};
        for(String coord : coordImpossible)
            assertFalse(f.estPossible(new Coord(coord)));
    }


    @Test
    void isCoupValid() {
        Fou f = new Fou(new Coord("a8"), Couleur.NOIR);

        Echiquier e = new Echiquier("f3p3/p7/8/8/4p3/8/8/r6R",new FabPiece(), new FabChessJoueur());
        String[] coordPossible = {"b7","c6","d5"};
        for(String coord : coordPossible)
            assertTrue(f.isCoupValid(new Coord(coord),e));

        String[] coordImpossible = {"e4", "f3", "f7", "b2", "d4"};
        for(String coord : coordImpossible)
            assertFalse(f.isCoupValid(new Coord(coord),e));
    }



    @Test
    void getAllMoves() {
        Echiquier e = new Echiquier("f3p3/p7/8/8/4p3/8/8/r6R",new FabPiece(), new FabChessJoueur());

        String[] coupPossible = {"b7","c6","d5"};
        Coord cR = e.locateSensiblePiece(BLANC);
        List<IPiece> ennemies = e.getPieceFromColor(NOIR);
        IPiece p = e.getPiece(new Coord("a8"));

        LinkedList<Coord> coupDame = p.getAllMoves(cR, ennemies, e);
        for(String coup : coupPossible)
            assertTrue(coupDame.contains(new Coord(coup)));
    }

}