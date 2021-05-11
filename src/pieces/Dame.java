package pieces;

import coordonnee.Coord;
import echiquier.Couleur;

import static java.lang.Math.abs;
import static pieces.PieceType.DAME;

public class Dame  extends Piece{
    /**
     * Constructeur d'une dame
     * @see Piece#Piece(Couleur, PieceType, coordonnee.Coord)
     */
    public Dame(Coord coord, Couleur c) {
        super(c, DAME, coord);
    }

    /**
     * {@inheritDoc}
     * @param c
     */
    @Override
    public boolean estPossible(Coord c) {
        int varX = abs(coord.x-c.x);
        int varY = abs(coord.y-c.y);
        return (varY >= 1 && varY==varX) || (varX > 0 && varY == 0) || (varY > 0 && varX == 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbole() {
        return "D" ;
    }
}
