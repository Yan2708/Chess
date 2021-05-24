package Joueur;

import coordonnee.Coord;
import echiquier.Couleur;
import echiquier.Echiquier;
import echiquier.IChessJoueur;
import echiquier.IPiece;

import java.util.List;

/** {@inheritDoc} */
public abstract class ChessJoueur implements IChessJoueur {
    /** la couleur du joueur */
    protected Couleur couleur;

    /** Constructeur de joueur */
    public ChessJoueur(Couleur couleur){
        this.couleur = couleur;
    }

    /** {@inheritDoc} */
    public final Couleur getCouleur(){
        return couleur;
    }

    /** {@inheritDoc} */
    public abstract String getCoup(Echiquier e, List<IPiece> allys, List<IPiece> enemies, Coord sC);

    /** {@inheritDoc} */
    public abstract String coupToString(String coup);

    /** {@inheritDoc} */
    public abstract boolean acceptDraw();
}
