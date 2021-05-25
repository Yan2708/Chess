package Joueur;

import echiquier.Couleur;
import echiquier.IFabChessJoueur;
import echiquier.IChessJoueur;

/**
 * {@inheritDoc}
 * @author Stefan Radovanovic, Yannick Li, Zakaria Sellam
 */
public class FabChessJoueur implements IFabChessJoueur {

    /**
     * {@inheritDoc}
     */
    @Override
    public IChessJoueur getJoueur(char type, Couleur couleur) {
        if (type == 'I') {
            return new ChessJoueurIA(couleur);
        }
        return new ChessJoueurHumain(couleur);
    }
}