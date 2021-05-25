package pieces;

import echiquier.Coord;
import echiquier.Couleur;

import static java.lang.Math.abs;

/**
 * La dame est la pièce la plus puissante du jeu.
 * capable de se mouvoir en ligne droite, verticalement, horizontalement,
 * et diagonalement, sur un nombre quelconque de cases inoccupées.
 * @author Stefan Radovanovic, Yannick Li, Zakaria Sellam
 */
public class Dame  extends Piece{
    /** Constructeur d'une dame */
    public Dame(Coord coord, Couleur c) {
        super(c, coord);
    }

    /** {@inheritDoc} */
    @Override
    public boolean estPossible(Coord c) {
        int varX = abs(coord.getX()-c.getX());
        int varY = abs(coord.getY()-c.getY());
        return (varY >= 1 && varY==varX) || (varX > 0 && varY == 0) ||
                (varY > 0 && varX == 0);
    }

    /** {@inheritDoc} */
    @Override
    public String getSymbole() {
        return "D" ;
    }
}