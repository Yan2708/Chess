package Joueur;


import coordonnee.Coord;
import echiquier.Couleur;
import echiquier.Echiquier;
import echiquier.IPiece;

import java.util.List;

/**
 * Cette classe represente un joueur d'echec Humain.
 * celui-ci s'appuie sur une IHM pour communiquer avec le programme.
 * @author Stefan Radovanovic, Yannick Li, Zakaria Sellam
 */
public class ChessJoueurHumain extends ChessJoueur {

    /** Constructeur d'un joueur humain */
    public ChessJoueurHumain(Couleur couleur) {
        super(couleur);
    }

    /**
     * Un joueur Humain doit faire appel a une ihm pour donner son coup
     * @see chessIHM
     * */
    @Override
    public String getCoup(Echiquier e, List<IPiece> allys, List<IPiece> ennemies, Coord sC) {
        return chessIHM.getCoup(this, sC, ennemies, e);
    }

    /** le coup est deja affiché en invite de commande*/
    @Override
    public String coupToString(String coup) {
        return "";
    }

    /**
     * fais appel à une ihm pour accepter la proposition de nulle
     * @see chessIHM
     */
    @Override
    public boolean acceptDraw() {
        return chessIHM.acceptDraw(this);
    }
}
