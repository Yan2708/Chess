package echiquier;

import coordonnee.Coord;

public interface IFabriquePiece {

    /**
     * Renvoie une pièce à une coordonnée donnée
     * @param type le caractère représentant le type de pièce voulu
     * @return la pièce
     */
    IPiece getPiece(char type, Coord c);
}
