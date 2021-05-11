package pieces;

import coordonnee.Coord;
import echiquier.Couleur;
import echiquier.IPiece;
import echiquier.Regle;

import static java.lang.Math.abs;
import static pieces.PieceType.PION;
import static echiquier.Couleur.*;
import static echiquier.Echiquier.*;

public class Pion extends Piece{

    private static final int START1 = 1, START2 = 6;

    private int forward;

    /**
     * Constructeur d'un pion
     * @see Piece#Piece(Couleur, PieceType, coordonnee.Coord)
     */
    public Pion(Coord coord, Couleur c) {
        super(c, PION, coord);
        this.forward = c == BLANC ? -1 : 1;
    }

    /**
     * {@inheritDoc}
     * @param c
     */
    @Override
    public boolean estPossible(Coord c) {
        int varX = c.x - coord.x;
        int varY = abs(coord.y-c.y);
        return (varY == 1 || varY == 0) && varX == forward ||
                (isFirstMove() && varX == (2*forward) && varY == 0);
    }

    private boolean isFirstMove(){
        int x = this.coord.x;
        return x == START1 || x == START2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbole() {
        return "P";
    }

    @Override
    public boolean isCoupValid(Coord cF) {
        IPiece p = getPiece(cF);
        return super.isCoupValid(cF) &&
                (isPriseEnDiag(cF) ? Regle.areOpposite(this, p) && !p.estVide() : p.estVide());
    }

    /**
     * si le mouvement d'un pion resulte en un coup diagonale
     * @param c la coordonnées d'arrivée
     * @return le coup est une prise diagonale
     */
    private boolean isPriseEnDiag(Coord c){
        int diffX = abs(c.x - coord.x);
        int diffY = abs(c.y - coord.y);
        return diffX == 1 && diffY == 1;
    }

    @Override
    public boolean isPromotable() {
        return true;
    }

    @Override
    public boolean peutAttaquer(Coord c) {
        return super.peutAttaquer(c) && this.isPriseEnDiag(c);
    }
}
