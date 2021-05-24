package echiquier;

import coordonnee.Coord;

/**
 * fabrique de piece.
 */
public interface IFabPiece {

    /**
     * Crée une pièce selon le type donné
     * 'D' pour dame, 'R' pour roi etc
     * @param type le caractère représentant le type de pièce voulu
     * @param c la coordonnée associée a la piece
     * @return la pièce
     */
    IPiece getPiece(char type, Coord c);

}
