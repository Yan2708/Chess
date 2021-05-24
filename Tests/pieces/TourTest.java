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

class TourTest {

    @Test
    void estPossible() {
        Tour t = new Tour(new Coord("a3"),Couleur.NOIR);
        String[] coordPossible = {"a5", "a1", "b3", "a2", "c3", "d3"};
        for(String coord : coordPossible)
            assertTrue(t.estPossible(new Coord(coord)));
    }

    @Test
    void isCoupValid() {
        Echiquier e = new Echiquier("T7/8/8/8/8/r6R/8/8",new FPiece(), new FabChessJoueur());
        String[] coupPossible = {"b8, c8, d8, e8, f8, g8, h8"};
        IPiece p = e.getPiece(new Coord("A8"));
        for(String c: coupPossible) {
            assertTrue(p.isCoupValid(new Coord(c), e));
        }
    }

    @Test
    void getAllMoves(){
        Echiquier e = new Echiquier("T7/PPPPPPPP/8/8/8/r6R/8/8",new FPiece(), new FabChessJoueur());
        String[] coupPossible = {"b8, c8, d8, e8, f8, g8, h8"};
        Coord cR = e.locateSensiblePiece(BLANC);
        List<IPiece> ennemies = e.getPieceFromColor(NOIR);
        IPiece p = e.getPiece(new Coord("A8"));

        LinkedList<Coord> coupTour = p.getAllMoves(cR, ennemies, e);
        for(String c: coupPossible){
            assertTrue(coupTour.contains(new Coord(c)));
        }
    }


}