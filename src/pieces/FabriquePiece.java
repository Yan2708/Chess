package pieces;

import echequier.IFabriquePiece;
import echequier.IPiece;

public class FabriquePiece implements IFabriquePiece {

    /**
     * {@inheritDoc}
     */
    public IPiece getPiece(char type, int x, int y) {
        Couleur c = Character.isUpperCase(type) ? Couleur.BLANC : Couleur.NOIR;
        PieceType pt;
        pt = PieceType.getInstance(Character.toUpperCase(type));
        switch(pt){
            case FOU:
                return new Fou(x, y, c);
            case ROI:
                return new Roi(x, y, c);
            case DAME:
                return new Dame(x, y, c);
            case PION:
                return new Pion(x, y, c);
            case TOUR:
                return new Tour(x, y, c);
            case CAVALIER:
                return new Cavalier(x, y, c);
            default: return new Vide(x, y, Couleur.VIDE);
        }
    }
}
