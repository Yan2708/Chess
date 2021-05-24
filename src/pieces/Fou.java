package pieces;

import coordonnee.Coord;
import echiquier.Couleur;

import static java.lang.Math.abs;

/**
 * represente le fou dans les echecs
 * il se deplace uniquement en diagonale.
 */
public class Fou extends Piece{

    /** Constructeur d'un fou */
    public Fou(Coord coord, Couleur c) {
        super(c, coord);
    }

    /** {@inheritDoc} */
    @Override
    public boolean estPossible(Coord c) {
        int varX = abs(coord.getX()-c.getX());
        int varY = abs(coord.getY()-c.getY());
        return (varY >= 1 && varX==varY);
    }

    /** {@inheritDoc} */
    @Override
    public String getSymbole() {
        return "F";
    }

    /** le fou ne peut pas mater */
    @Override
    public boolean canHoldEndGame() {
        return false;
    }
}
