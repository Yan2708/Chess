package pieces;

import echiquier.Coord;
import echiquier.Couleur;

import static java.lang.Math.abs;

/**
 * cavalier ou le cheval dans le jeu d'echec.
 * Le déplacement du cavalier est original.
 * Il se déplace en L, c’est-à-dire de deux cases dans une direction
 * combinées avec une case perpendiculairement.
 * @author Stefan Radovanovic, Yannick Li, Zakaria Sellam
 */
public class Cavalier extends Piece{

    /** Constructeur d'un cavalier */
    public Cavalier(Coord coord, Couleur c) {
        super(c, coord);
    }

    /** {@inheritDoc} */
    @Override
    public boolean estPossible(Coord c) {
        int varX = abs(coord.getX()-c.getX());
        int varY = abs(coord.getY()-c.getY());
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