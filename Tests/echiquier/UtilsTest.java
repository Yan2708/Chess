package echiquier;

import Joueur.FabChessJoueur;
import coordonnee.Coord;
import org.junit.jupiter.api.Test;
import pieces.FabPiece;

import java.util.List;

import static echiquier.Couleur.BLANC;
import static echiquier.Couleur.NOIR;
import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void getPath() {

        Coord c1 = new Coord("a8");
        Coord c2 = new Coord("a3");
        Coord c3 = new Coord("f3");
        Coord c4 = new Coord("e6");
        String[] ligne = {"a8","a7","a6","a5","a4","a3"};
        String[] diag = {"a8","b7","c6","d5","e4","f3"};

        List<Coord> l = Utils.getPath(c1,c2);
        assertEquals(6,l.size());
        for(String pos : ligne)
            assertTrue(l.contains(new Coord(pos)));

        l = Utils.getPath(c1,c3);
        assertEquals(6,l.size());
        for(String pos : diag)
            assertTrue(l.contains(new Coord(pos)));

        l = Utils.getPath(c1,c4);
        assertEquals(1,l.size());
        assertTrue(l.contains(c4));
    }

    @Test
    void getAllAttackingPiece() {
        Echiquier e = new Echiquier("r6T/1p6/1PF4p/3T4/8/3R4/8/T7",new FabPiece(), new FabChessJoueur());

        String[] coordAttaquant = { "a1", "h8"};
        List<IPiece> l = Utils.getAllAttackingPiece(e.locateSensiblePiece(NOIR),e.getPieceFromColor(BLANC),e);
        assertEquals(2,l.size());
        for (String coord : coordAttaquant)
            assertTrue(l.contains(e.getPiece(new Coord(coord))));
    }

    @Test
    void allMovesDefendingCheck(){
        FabChessJoueur fj = new FabChessJoueur();
        FabPiece fp = new FabPiece();
        Echiquier e = new Echiquier("3r4/1t6/8/5c2/8/8/8/R2D4", fp, fj);
        Coord sC = e.locateSensiblePiece(NOIR);
        IPiece cavalier = e.getPiece(new Coord("f5"));
        List<IPiece> ennemies = e.getPieceFromColor(BLANC);
        String[] coupCavalier = {"d4","d6"};
        List<Coord> cavalierMoves = Utils.allMovesDefendingCheck(cavalier, sC, ennemies, e);
        assertEquals(2, cavalierMoves.size());
        for(String coup : coupCavalier)
            assertTrue(cavalierMoves.contains(new Coord(coup)));
        IPiece tour = e.getPiece(new Coord("b7"));
        List<Coord> tourMoves = Utils.allMovesDefendingCheck(tour, sC, ennemies, e);
        assertEquals(1, tourMoves.size());
        assertTrue(tourMoves.contains(new Coord("d7")));
    }

    @Test
    void allMovesFromPin() {
        Echiquier e = new Echiquier("r7/1p6/1PF4p/3T4/8/3R4/8/8",new FabPiece(), new FabChessJoueur());
        List<Coord> l = Utils.allMovesFromPin(e.getPiece(new Coord("b7")),e.locateSensiblePiece(NOIR), e);
        assertEquals(1, l.size());
        assertTrue(l.contains(new Coord("c6")));
    }

    @Test
    void getPningPiece() {
        FabChessJoueur fj = new FabChessJoueur();
        FabPiece fp = new FabPiece();
        Echiquier e = new Echiquier("3r4/2ppp3/8/F5F1/8/8/8/3D3R", fp, fj);
        Coord sC = e.locateSensiblePiece(NOIR);
        IPiece pion1 = e.getPiece(new Coord("c7"));
        assertNotNull(Utils.getPningPiece(pion1.getCoord(), sC, e));
        IPiece pion2 = e.getPiece(new Coord("d7"));
        assertNotNull(Utils.getPningPiece(pion2.getCoord(), sC, e));
        IPiece pion3 = e.getPiece(new Coord("e7"));
        assertNotNull(Utils.getPningPiece(pion3.getCoord(), sC, e));
    }

    @Test
    void getPathToBorder() {
        Coord c1 = new Coord("a4");
        Coord c2 = new Coord(1,0);
        Coord c3 = new Coord(-1,1);
        String[] ligne = { "a4","a3","a2","a1"};
        String[] diag = {"a4","b5","c6","d7","e8"};

        List<Coord> l = Utils.getPathToBorder(c1,c2);
        for (String coord : ligne )
            assertTrue(l.contains(new Coord(coord)));

        l = Utils.getPathToBorder(c1,c3);
        for (String coord : diag )
            assertTrue(l.contains(new Coord(coord)));
    }
}