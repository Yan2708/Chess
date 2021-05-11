package pieces;

import coordonnee.Coord;
import echiquier.Couleur;

import static java.lang.Math.abs;
import static pieces.PieceType.CAVALIER;

public class Cavalier extends Piece{

    /**
     * Constructeur d'un cavalier
     * @see Piece#Piece(Couleur, PieceType, coordonnee.Coord)
     */
    public Cavalier(Coord coord, Couleur c) {
        super(c, CAVALIER, coord);
    }

    /**
     * {@inheritDoc}
     * @param c
     */
    @Override
    public boolean estPossible(Coord c) {
        int varX = abs(coord.x-c.x);
        int varY = abs(coord.y-c.y);
        return (varX==1 && varY==2)||(varX==2 && varY==1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbole() {
        return "C";
    }

    @Override
    public boolean canHoldEndGame() {
        return false;
    }
}
