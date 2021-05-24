package pieces;

import coordonnee.Coord;
import echiquier.Couleur;

import static java.lang.Math.abs;

/**
 * cavalier ou le cheval dans le jeu d'echec.
 * Le déplacement du cavalier est original.
 * Il se déplace en L, c’est-à-dire de deux cases dans une direction
 * combinées avec une case perpendiculairement.
 */
public class Cavalier extends Piece{

    /** Constructeur d'un cavalier */
    public Cavalier(Coord coord, Couleur c) {
        super(c, coord);
    }

    /** {@inheritDoc} */
    @Override
    public boolean estPossible(Coord c) {
        int varX = abs(coord.x-c.x);
        int varY = abs(coord.y-c.y);
        return (varX==1 && varY==2)||(varX==2 && varY==1);
    }

    /** {@inheritDoc} */
    @Override
    public String getSymbole() {
        return "C";
    }

    /** le cavalier ne peut pas mater*/
    @Override
    public boolean canHoldEndGame() {
        return false;
    }
}
