package echiquier;

import coordonnee.Coord;

import java.util.LinkedList;
import java.util.List;

/**
 * Interface definissant les elements de l'echiquier, ici des pieces.
 * @author Stefan Radovanovic, Yannick Li, Zakaria Sellam
 */
public interface IPiece {

    /** Met à jour la position de la pièce selon la position donnée en paramètre */
    void newPos(Coord c);

    /** Renvoie la couleur de la pièce */
    Couleur getCouleur();

    /**
     * Pour une position donnée, calcule si le déplacement est possible pour la pièce
     * @param c@return si le deplacement est possible pour la pièce ou non
     */
    boolean estPossible(Coord c);

    /**
     * Renvoie le type de la pièce avec comme difference BLANC en MAJUSCULE, NOIR en minuscule
     * @return la représentation de la pièce
     */
    String dessiner();

    /** Renvoie la coordonnée */
    Coord getCoord();

    /**
     * Verifie si pour une coordonné d'arrivée la piece peut effectué le deplacement
     * dans l'echiquier.
     * verifie si la voie est libre, le deplacement est possible etc...
     * @param cF coordonné d'arrivée
     * @param e l'échiquier
     * @return le coup est valide
     */
    boolean isCoupValid(Coord cF, Echiquier e);

    /** Vérifie si la piece est sensible*/
    boolean estSensible();

    /** Vérifie si la case est représenté par une pièce vide */
    boolean estVide();

    /** Renvoie la piece chargé de promouvoir la piece a etre promu*/
    IPiece autoPromote();

    /**
     * Verifie si une piece peut se deplacer sur une coordonnée donnée.
     * à la difference de isCoupValid() cette methode ne verifie pas
     * si l'arrivé d'une piece vers la coordonnée est correct.
     * @param c coordonné d'arrivée
     * @param e L'échiquier
     * @return  Si la piéce est attaquable
     */
    boolean peutAttaquer(Coord c, Echiquier e);

    /**
     * Renvoie tout les mouvements possible de la piece sur l'echiquier.
     * @param sC la coordonnée sensible de l'allié
     * @param ennemies Les pièces enemies
     * @param e l'echiquier
     * @return tout les movements possible
     */
    LinkedList<Coord> getAllMoves(Coord sC, List<IPiece> ennemies, Echiquier e);

    /** Renvoie la capacité d'une piece a tenir une fin de partie (quand il ne reste qu'elle et les rois)*/
    boolean canHoldEndGame();
}
