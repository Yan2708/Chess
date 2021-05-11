package pieces;

import coordonnee.Coord;
import echiquier.Couleur;

import static java.lang.Math.abs;
import static pieces.PieceType.TOUR;

public class Tour extends Piece{
    /**
     * Constructeur d'une tour
     * @see Piece#Piece(Couleur, PieceType, coordonnee.Coord)
     */
    public Tour(Coord coord, Couleur c) {
        super(c, TOUR, coord);
    }

    /**
     * {@inheritDoc}
     * @param c
     */
    @Override
    public boolean estPossible(Coord c) {
        int varX = abs(coord.x-c.x);
        int varY = abs(coord.y-c.y);
        return (varX > 0 && varY == 0) || (varY > 0 && varX == 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbole() {
        return "T";
    }
}
