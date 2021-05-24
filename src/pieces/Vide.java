package pieces;

import echiquier.Coord;
import echiquier.Couleur;

/**
 * Classe representant les pieces vides.
 * @author Stefan Radovanovic, Yannick Li, Zakaria Sellam
 */
public class Vide extends Piece{

    /** Constructeur d'une pièce vide */
    public Vide(Coord coord, Couleur c) {
        super(c, coord);
    }

    /** {@inheritDoc} */
    @Override
    public boolean estPossible(Coord c) {
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public String getSymbole() {
        return " ";
    }

    /** La piece vide est effectivement vide*/
    @Override
    public boolean estVide() {
        return true;
    }
}

