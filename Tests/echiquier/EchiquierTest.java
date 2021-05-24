package echiquier;

import Joueur.FabChessJoueur;
import coordonnee.Coord;
import org.junit.jupiter.api.Test;
import pieces.Dame;
import pieces.FabPiece;
import pieces.Roi;
import pieces.Vide;

import java.util.LinkedList;
import java.util.List;

import static echiquier.Couleur.*;
import static org.junit.jupiter.api.Assertions.*;

class EchiquierTest {

    @Test
    void allClassicMoves() {
        Echiquier e = new Echiquier("r7/8/8/3T3P/8/8/8/7R",new FabPiece(), new FabChessJoueur());

        String[] coupPossible = {"d8","d7","d6","a5","b5","c5","e5","f5","g5","d4","d3","d2","d1"};
        LinkedList<Coord> l = e.allClassicMoves(e.getPiece(new Coord("d5")));
        for (String coup : coupPossible)
            assertTrue(l.contains(new Coord(coup)));
        assertFalse(l.contains("h5"));

        String[] coupPossibleRoi = {"a7","b8","b7"};
        l = e.allClassicMoves(e.getPiece(new Coord("a8")));
        assertEquals(l.size(), 3);
        for (String coup : coupPossibleRoi)
            assertTrue(l.contains(new Coord(coup)));
    }

    @Test
    void deplacer() {
        Echiquier e = new Echiquier("r7/8/8/3T3P/8/8/8/7R",new FabPiece(), new FabChessJoueur());
        e.deplacer("a8h4");
        assertEquals("r", e.getPiece(new Coord("h4")).dessiner());
        assertEquals(" ", e.getPiece(new Coord("a8")).dessiner());
    }

    @Test
    void getPieceFromColor() {
        Echiquier e = new Echiquier("r7/8/8/3T3P/PPPPPPpp/8/8/7R",new FabPiece(), new FabChessJoueur());
        List<IPiece> blanc = e.getPieceFromColor(BLANC);
        List<IPiece> noir = e.getPieceFromColor(NOIR);
        assertEquals(blanc.size(),9);
        assertEquals(noir.size(),3);
        for (IPiece p : blanc) {
            assertEquals(p.getCouleur(), BLANC);
        }
        for (IPiece p : noir) {
            assertEquals(p.getCouleur(), NOIR);
        }
    }

    @Test
    void locateSensiblePiece() {
        Echiquier e = new Echiquier("r7/8/8/3T3P/PPPPPPpp/3R4/8/8",new FabPiece(), new FabChessJoueur());
        assertEquals(e.locateSensiblePiece(BLANC),new Coord("d3"));
        assertEquals(e.locateSensiblePiece(NOIR),new Coord("a8"));
    }

    @Test
    void checkForPromote() {
        Echiquier e = new Echiquier("r6P/PP6/8/3T4/8/3R4/PPPp4/4p3",new FabPiece(), new FabChessJoueur());
        e.checkForPromote(BLANC);
        assertEquals("D", e.getPiece(new Coord("h8")).dessiner());
        assertEquals("P", e.getPiece(new Coord("a7")).dessiner());
        e.checkForPromote(NOIR);
        assertEquals("d", e.getPiece(new Coord("e1")).dessiner());
        assertEquals("p", e.getPiece(new Coord("d2")).dessiner());
        assertEquals("P", e.getPiece(new Coord("c2")).dessiner());
    }

    @Test
    void testToString() {
        Echiquier e = new Echiquier(new FabPiece(), new FabChessJoueur());
        String board =
                "     a   b   c   d   e   f   g   h    \n" +
                "    --- --- --- --- --- --- --- ---   \n" +
                " 8 | t | c | f | d | r | f | c | t | 8\n" +
                "    --- --- --- --- --- --- --- ---   \n" +
                " 7 | p | p | p | p | p | p | p | p | 7\n" +
                "    --- --- --- --- --- --- --- ---   \n" +
                " 6 |   |   |   |   |   |   |   |   | 6\n" +
                "    --- --- --- --- --- --- --- ---   \n" +
                " 5 |   |   |   |   |   |   |   |   | 5\n" +
                "    --- --- --- --- --- --- --- ---   \n" +
                " 4 |   |   |   |   |   |   |   |   | 4\n" +
                "    --- --- --- --- --- --- --- ---   \n" +
                " 3 |   |   |   |   |   |   |   |   | 3\n" +
                "    --- --- --- --- --- --- --- ---   \n" +
                " 2 | P | P | P | P | P | P | P | P | 2\n" +
                "    --- --- --- --- --- --- --- ---   \n" +
                " 1 | T | C | F | D | R | F | C | T | 1\n" +
                "    --- --- --- --- --- --- --- ---   \n" +
                "     a   b   c   d   e   f   g   h    \n";
        assertEquals(e.toString(), board);
    }
}