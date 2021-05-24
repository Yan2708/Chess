package echiquier;

import Joueur.FabChessJoueur;
import org.junit.jupiter.api.Test;
import pieces.FabPiece;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegleTest {

    @Test
    void checkForMate() {
        // https://en.wikipedia.org/wiki/Checkmate_pattern
        // https://lichess.org/editor
        String[] fens = {"8/4C1pr/8/7T/8/8/8/7R",   //Anastasia's mate
                "6rT/6P1/5R2/8/8/8/8/8",            //Anderssen's mate
                "7r/7T/5C2/8/8/8/8/7R",             //Arabian mate
                "2T3r1/5ppp/8/8/8/8/8/7R",          //Back-rank mate
                "5tr1/6pD/6P1/8/8/8/8/7R",          //Damiano's mate
                "7r/6p1/8/3F3D/8/8/8/7R"};          //Greco's mate
        FabChessJoueur fj = new FabChessJoueur();
        FabPiece fp = new FabPiece();
        for (String fen : fens) {
            Echiquier e = new Echiquier(fen, fp, fj);
            //System.out.println(e.toString());
            Coord sC = e.locateSensiblePiece(Couleur.NOIR);
            List<IPiece> allys = e.getPieceFromColor(Couleur.NOIR);
            List<IPiece> ennemies = e.getPieceFromColor(Couleur.BLANC);
            assertTrue(Regle.checkForMate(sC, allys, ennemies, e));
        }
    }

    @Test
    void isStaleMate() {
        // https://chess24.com/en/read/news/7-stalemates-every-chess-player-needs-to-know
        String[] fens = {"r7/P7/1R6/8/8/8/8/8",   //King + rook pawn vs. king (variation)
                "7r/8/6RP/3F4/8/8/8/8",            //The wrong bishop
                "5r2/8/4D2P/8/8/8/8/1R6"};        //Queens StaleMate

        FabChessJoueur fj = new FabChessJoueur();
        FabPiece fp = new FabPiece();
        for (String fen : fens) {
            Echiquier e = new Echiquier(fen, fp, fj);
            //System.out.println(e.toString());
            Coord sC = e.locateSensiblePiece(Couleur.NOIR);
            List<IPiece> allys = e.getPieceFromColor(Couleur.NOIR);
            List<IPiece> ennemies = e.getPieceFromColor(Couleur.BLANC);
            assertTrue(Regle.isStaleMate(sC, allys, ennemies, e));
        }
    }

    @Test
    void isAttacked() {
        FabChessJoueur fj = new FabChessJoueur();
        FabPiece fp = new FabPiece();
        Echiquier e = new Echiquier("7r/8/8/3P4/8/8/8/R6D", fp, fj);
        List<IPiece> ennemies = e.getPieceFromColor(Couleur.BLANC);
        String[] attaquePion = {"c6", "e6"};
        for (String coup : attaquePion)
            assertTrue(Regle.isAttacked(new Coord(coup), ennemies, e));
        Coord coordRoi = e.locateSensiblePiece(Couleur.NOIR);
        assertTrue(Regle.isAttacked(coordRoi, ennemies, e));
    }

    @Test
    void impossibleMat() {
        String[] fens = {"7r/1f6/8/8/8/7F/8/4R3",   //roi + fou vs roi + fou
                "7r/8/8/8/1c6/7C/8/4R3",            //roi + cavalier vs roi + cavalier
                "8/5r2/8/8/2R5/8/8/8"};        //roi vs roi

        FabChessJoueur fj = new FabChessJoueur();
        FabPiece fp = new FabPiece();
        for (String fen : fens) {
            Echiquier e = new Echiquier(fen, fp, fj);
            //System.out.println(e.toString());
            List<IPiece> allys = e.getPieceFromColor(Couleur.NOIR);
            List<IPiece> ennemies = e.getPieceFromColor(Couleur.BLANC);
            assertTrue(Regle.impossibleMat(allys, ennemies));
        }
    }

    @Test
    void voieLibre() {
        FabChessJoueur fj = new FabChessJoueur();
        FabPiece fp = new FabPiece();
        Echiquier e = new Echiquier("2r5/8/6p1/2p5/8/8/2D2T1p/7R", fp, fj);
        IPiece dame = e.getPiece(new Coord("c2"));
        assertFalse(Regle.voieLibre(dame, new Coord("h2"), e));
        assertFalse(Regle.voieLibre(dame, new Coord("c8"), e));
        assertTrue(Regle.voieLibre(dame, new Coord("g6"), e));
    }

    @Test
    void isFinishValid() {
        FabChessJoueur fj = new FabChessJoueur();
        FabPiece fp = new FabPiece();
        Echiquier e = new Echiquier("R6r/8/8/2p5/4P3/8/P1D2P2/2t5", fp, fj);
        IPiece dame = e.getPiece(new Coord("c2"));
        assertTrue(Regle.isFinishValid(dame, new Coord("c1"), e));
        assertTrue(Regle.isFinishValid(dame, new Coord("c5"), e));
        assertTrue(Regle.isFinishValid(dame, new Coord("a4"), e));
        assertFalse(Regle.isFinishValid(dame, new Coord("a2"), e));
        assertFalse(Regle.isFinishValid(dame, new Coord("f2"), e));
        assertFalse(Regle.isFinishValid(dame, new Coord("e42"), e));
    }

}