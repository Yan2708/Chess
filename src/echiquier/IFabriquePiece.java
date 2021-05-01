package echiquier;

public interface IFabriquePiece {

    /**
     * Renvoie une pièce à une coordonnée donnée
     * @param type le caractère représentant le type de pièce voulu
     * @param x la position en x de la pièce
     * @param y la position en y de la pièce
     * @return la pièce
     */
    IPiece getPiece(char type, int x, int y);
}
