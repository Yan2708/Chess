package echiquier;


import static echiquier.Utils.*;
import static echiquier.Echiquier.*;
import static java.lang.Math.abs;

import java.util.ArrayList;


public class Regle {

    /**
     * Renvoie si le mouvement d'une piece vers une coordonnée est valide.
     * un coup est valide si : le mouvement est possible pour la piece
     * le chemin entre la piece et l'arrivé n'a pas d'obstacle
     * et que la case d'arrivé peut acceuillir la piece.
     * @param c la coordonnée d'arrivée
     * @param p la coordonnée de la piece
     * @return si le déplacement est possible
     */
    public static boolean isCoupValid(Coord c, IPiece p){
        //on divise les conditions pour la lisibilité.
        boolean possible = p.estPossible(c.getX(),c.getY()) && voieLibre(p, c) && isFinishValid(p, c);

        boolean isPawn = p.getPieceType().equals("PION");                       //le traitement d'un pion est different
        boolean isDiag = priseEnDiag(p, c);                                     //si le coup est diagonale, alors
        String color = getPiece(c).getCouleur();                                //il doit prendre un piece.
        boolean isEnemy = !color.equals(p.getCouleur()) && !color.equals("VIDE");

        return possible && (!isPawn || (!isDiag || isEnemy));
    }

    /**
     * si le mouvement d'un pion resulte en un coup diagonale
     * @param pion le pion
     * @param c la coordonnées d'arrivée
     * @return le coup est une prise diagonale
     */
    private static boolean priseEnDiag(IPiece pion,Coord c){
        int diffX = abs(c.getX() - pion.getLigne());
        int diffY = abs(c.getY() - pion.getColonne());
        return diffX == 1 && diffY == 1;
    }

    /**
     * Verifie si pour une couleur donnée le roi est en echec et mat.
     * La premiere partie verifie si un piece alliée au roi peut bloquer l'echec
     * la seconde partie verifie si le roi peut s'echapper de l'echec.
     * @param couleur la couleur du roi
     * @param cR les coordonnées du roi
     * @param pieces toutes les pieces de l'enemi
     * @return si le roi est en échec et mat
     */
    public static boolean checkForMate(String couleur, Coord cR, ArrayList<IPiece> pieces){
        ArrayList<IPiece> checkingPieces = getAllAttackingPiece(cR, pieces); //un roi peut etre mis en echec par 2 pieces

        if(checkingPieces.isEmpty()) return false;

        //si une piece met le roi en echec alors il est possible de defendre avec une autre piece.
        if(!(checkingPieces.size() > 1) &&
                isBlocked(checkingPieces, couleur, cR))
            return false;

        //verifie si chaque mouvement possible du roi est sans danger
        ArrayList<Coord> kingsMoves = getAllMoves(getPiece(cR), cR, couleur, pieces);
        for(Coord c : kingsMoves)
            if(!isAttacked(c, pieces))
                return false;

        return true;
    }

    /**
     * Verifie si une piece peut s'interposer entre la piece qui met en echec le roi et le roi.
     * Cette piece doit valider 3 conditions :
     * elle ne doit pas etre le roi, le coup doit etre valide et la piece ne doit pas etre clouée.
     * @param enemies les pièces ennemies
     * @param couleur la couleur alliée
     * @param cR les coordonnées du roi
     * @return une piece peut etre posée dans le chemin
     */
    private static boolean isBlocked(ArrayList<IPiece> enemies, String couleur, Coord cR){
        ArrayList<IPiece> Allys = getPieceFromColor(couleur);  //liste de piece allié
        for(IPiece p : Allys){
            if(!isPiecePinned(p, cR, couleur.equals("BLANC") ? "NOIR" : "BLANC") && //la piece de doit pas etre cloué
                    !allMovesDefendingCheck(p, cR, enemies).isEmpty())              //si un move existe entre le roi
                return true;                                                        //et la piece attaquante
        }
        return false;
    }

    /**
     * Verifie si une piece est cloué en verifiant toutes les cases en direction inverse du roi
     * en partant de la piece.
     * @param p la pièce à verifier
     * @param cR les coordonnées du roi
     * @param couleur la couleur opposée
     * @return si la pièce est clouée
     * @see Utils#getPningPiece(Coord, Coord, String)
     */
    public static boolean isPiecePinned(IPiece p, Coord cR, String couleur){
        Coord cS = new Coord(p.getLigne(), p.getColonne());

        if(p.getPieceType().equals("ROI")       ||      //un piece cloué ne peut pas etre le roi
                !Coord.isStraightPath(cS, cR)   ||
                !voieLibre(p, cR))
            return false;

        return !(getPningPiece(cS, cR, couleur) == null);   //s'il n'y a pas de piece clouante
    }


    /**
     * verifie avec les coordonnée du roi si celui-ci est attaquée par les pieces adverses.
     * @param cR les coordonnées du roi
     * @param pieces les pièces présentes sur l'échiquier
     * @return si une pièce(s) menace(s) le roi
     * @see Utils#getAllAttackingPiece(Coord, ArrayList)
     */
    public static boolean isAttacked(Coord cR, ArrayList<IPiece> pieces){
        return !getAllAttackingPiece(cR, pieces).isEmpty();
    }

    /**
     * Retourne si une coordonnée peut etre attaqué par une pièce ou non
     * @param c coordonnée d'arrivée
     * @param p la pièce en question
     * @return si le roi est en echec
     */
    public static boolean canBeAttacked(Coord c, IPiece p){
        // l'arrivé n'est pas testé car ce n'est pas le but de cette methode.
        return p.estPossible(c.getX(), c.getY()) && voieLibre(p, c);
    }


    /**
     * Verifie si il y a une egalité par pat dans l'etat actuel de l'echiquier.
     * le pat est une regle qui reconnait comme etant une egalité le cas ou le joueur
     * actuel n'a pas de coup possible parmis toutes ces pieces
     * sans que le roi soit en echec.
     * @param ennemies les pieces ennemies
     * @param allys les pieces alliées
     * @param couleur la couleur ennemie
     * @param cR les coordonnées du roi
     * @return l'egalité par pat est detecté
     */
    public static boolean isStaleMate(ArrayList<IPiece> ennemies, ArrayList<IPiece> allys, String couleur, Coord cR){
        ArrayList<Coord> allPossibleMoves = new ArrayList<>();
        if(isAttacked(cR, ennemies))
            return false;

        for(IPiece p: allys)
            allPossibleMoves.addAll(getAllMoves(p, cR, couleur, ennemies));

        return allPossibleMoves.isEmpty();
    }

}
