package echiquier;

import coordonnee.Coord;

import java.util.ArrayList;

public interface IPiece {

    /**
     * Pour une position donnée, calcule si le déplacement est possible pour la pièce
     *
     * @param c@return si le deplacement est possible pour la pièce ou non
     */
    boolean estPossible(Coord c);

    /**
     * Renvoie le bon caractère selon la couleur de la pièce (BLANC en MAJUSCULE, NOIR en minuscule)
     * @return la représentation de la pièce
     */
    String dessiner();

    /**
     * Renvoie la couleur de la pièce
     * @return la couleur de la pièce
     */
    Couleur getCouleur();

    /**
     * Getter du type de la pièce
     * @return le type de la pièce
     */
    String getPieceType();

    /**
     * Met à jour la position de la pièce selon la position donnée en paramètre
     */
    void newPos(Coord c);

    Coord getCoord();

    boolean isCoupValid(Coord cF);

    boolean estSensible();

    boolean estVide();

    boolean isPromotable();

    boolean peutAttaquer(Coord c);

    ArrayList<Coord> getAllMoves(Coord cR, ArrayList<IPiece> ennemies);

    boolean canHoldEndGame();
}
