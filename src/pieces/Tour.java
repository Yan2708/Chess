package pieces;

import echiquier.Coord;
import echiquier.Couleur;

import static java.lang.Math.abs;

/**
 * Represente les Tours dans un jeu d'echec.
 * La tour peut se déplacer horizontalement ou verticalement.
 * @author Stefan Radovanovic, Yannick Li, Zakaria Sellam
 */
public class Tour extends Piece{
    /** Constructeur d'une tour */
    public Tour(Coord coord, Couleur c) {
        super(c, coord);
    }

    /** {@inheritDoc} */
    @Override
    public boolean estPossible(Coord c) {
        int varX = abs(coord.getX()-c.getX());
        int varY = abs(coord.getY()-c.getY());
        return (varX > 0 && varY == 0) || (varY > 0 && varX == 0);
    }

    /** {@inheritDoc} */
    @Override
    public String getSymbole() {
        return "T";
    }
}