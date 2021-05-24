package Joueur;

import echiquier.Couleur;
import echiquier.IChessJoueur;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FabChessJoueurTest {

    @Test
    void getJoueur() {
        ChessJoueurHumain humain =  new ChessJoueurHumain(Couleur.BLANC);
        ChessJoueurIA ia = new ChessJoueurIA(Couleur.NOIR);
        FabChessJoueur f = new FabChessJoueur();
        IChessJoueur i = f.getJoueur('P', Couleur.BLANC);
        assertEquals(i.getClass(), humain.getClass());
        i=f.getJoueur('I', Couleur.NOIR);
        assertEquals(i.getClass(), ia.getClass());  ///classe identique
    }
}