package pieces;

import coordonnee.Coord;
import echiquier.Couleur;

import static pieces.PieceType.VIDE;

public class Vide extends Piece{

    /**
     * Constructeur d'une pièce vide
     * @see Piece#Piece(Couleur, PieceType, coordonnee.Coord)
     */
    public Vide(Coord coord, Couleur c) {
        super(c, VIDE, coord);
    }

    /**
     * {@inheritDoc}
     * @param c
     */
    @Override
    public boolean estPossible(Coord c) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbole() {
        return " ";
    }

    @Override
    public boolean estVide() {
        return true;
    }
}

