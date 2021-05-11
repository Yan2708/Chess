package pieces;

import coordonnee.Coord;
import echiquier.Couleur;

import static java.lang.Math.abs;
import static pieces.PieceType.FOU;

public class Fou extends Piece{

    /**
     * Constructeur d'un fou
     * @see Piece#Piece(Couleur, PieceType, coordonnee.Coord)
     */
    public Fou(Coord coord, Couleur c) {
        super(c, FOU, coord);
    }

    /**
     * {@inheritDoc}
     * @param c
     */
    @Override
    public boolean estPossible(Coord c) {
        int varX = abs(coord.x-c.x);
        int varY = abs(coord.y-c.y);
        return (varY >= 1 && varX==varY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbole() {
        return "F";
    }

    @Override
    public boolean canHoldEndGame() {
        return false;
    }
}
