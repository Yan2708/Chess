package echiquier;


import coordonnee.Coord;
import static echiquier.Couleur.*;

import java.util.ArrayList;


public class Regle {

    public static boolean areSameColor(IPiece p1, IPiece p2){
        return p1.getCouleur() == p2.getCouleur();
    }

    public static boolean isRightColor(IPiece p, Couleur couleur){
        return p.getCouleur() == couleur;
    }

    public static boolean areOpposite(IPiece p1, IPiece p2) {
        return !areSameColor(p1, p2) && !p2.estVide();
    }

    /**
     * Verifie si pour une couleur donnée le roi est en echec et mat.
     * La premiere partie verifie si un piece alliée au roi peut bloquer l'echec
     * la seconde partie verifie si le roi peut s'echapper de l'echec.
     * @param sC les coordonnées du roi
     * @param allys toutes les pieces de l'ennemi
     * @return si le roi est en échec et mat
     */
    public static boolean checkForMate(Coord sC, ArrayList<IPiece> allys, ArrayList<IPiece> ennemies){
        if(!isAttacked(sC, ennemies))
            return false;

        return getAllPossibleMoves(sC, allys, ennemies).isEmpty();
    }

    /**
     * Verifie si il y a une egalité par pat dans l'etat actuel de l'echiquier.
     * le pat est une regle qui reconnait comme etant une egalité le cas ou le joueur
     * actuel n'a pas de coup possible parmis toutes ces pieces
     * sans que le roi soit en echec.
     * @param sC les coordonnées du roi
     * @param allys les pieces alliées
     * @param ennemies les pieces ennemies
     * @return l'egalité par pat est detecté
     */
    public static boolean isStaleMate(Coord sC, ArrayList<IPiece> allys, ArrayList<IPiece> ennemies){
        if(isAttacked(sC, ennemies))
            return false;

        return getAllPossibleMoves(sC, allys, ennemies).isEmpty();
    }

    /**
     *
     * @param sC
     * @param allys
     * @param ennemies
     * @return
     */
    private static ArrayList<Coord> getAllPossibleMoves(Coord sC, ArrayList<IPiece> allys, ArrayList<IPiece> ennemies){
        ArrayList<Coord> allPossibleMoves = new ArrayList<>();

        for(IPiece p: allys)
            allPossibleMoves.addAll(p.getAllMoves(sC, ennemies));

        return allPossibleMoves;
    }

    /**
     * verifie avec les coordonnée du roi si celui-ci est attaquée par les pieces adverses.
     * @param sC les coordonnées du roi
     * @param ennemies les pièces présentes sur l'échiquier
     * @return si une pièce(s) menace(s) le roi
     * @see Utils#getAllAttackingPiece(Coord, ArrayList)
     */
    public static boolean isAttacked(Coord sC, ArrayList<IPiece> ennemies){
        return !Utils.getAllAttackingPiece(sC, ennemies).isEmpty();
    }

    /**
     * verifie si le materiel present sur l'echiquier est suffisant pour continuer une partie.
     * https://www.chess.com/terms/draw-chess#:~:text=A%20draw%20occurs%20in%20chess,player%20wins%20half%20a%20point.
     * @param allys         les pieces alliées
     * @param ennemies      les pieces ennemies
     * @return              si le matériel des joueurs est insuffisant pour mettre l'un l'autre en echec et mat
     */
    public static boolean impossibleMat(ArrayList<IPiece> allys, ArrayList<IPiece> ennemies){
        ArrayList<IPiece> allPieces = new ArrayList<>(ennemies);
        allPieces.addAll(allys);
        allPieces.removeIf(p -> !p.canHoldEndGame());

        return allPieces.isEmpty(); //"King vs. king"
    }

    /**
     * Vérifie si le chemin entre 2 points n'a pas d'obstacle (pas l'arrivé* @param cF coordonnées de la case d'arrivé
     * @param p la pièce à deplacer
     */
    public static boolean voieLibre(IPiece p, Coord cF){
        Coord cS = Coord.coordFromPiece(p);
        ArrayList<Coord> path = Utils.getPath(cS, cF);
        path.removeIf(c -> c.equals(cF) || c.equals(cS) || Echiquier.estVide(c));
        return path.isEmpty();
    }

    /**
     * Vérifie si la l'arrivé d'une piece sur une case est valide.
     * @param c coordonnées de la case
     * @param p la pièce à deplacer
     */
    public static boolean isFinishValid(IPiece p, Coord c){
        IPiece pA = Echiquier.getPiece(c);
        return !areSameColor(p, pA) && !pA.estSensible();
    }
}
