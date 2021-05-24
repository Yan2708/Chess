package echiquier;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * classe regroupant les regles mis en place par les echecs.
 * @author Stefan Radovanovic, Yannick Li, Zakaria Sellam
 * */
public class Regle {

    /**
     * Verifie si pour une couleur donnée le roi est en echec et mat.
     * Le roi doit etre en echec et s'il n'y a aucun coup possible
     * alors la condition est verifié.
     * @param sC la coordonnée sensible de l'allié
     * @param allys toutes les pieces alliés
     * @param ennemies toutes les pieces de l'ennemi
     * @param e l'echiquier
     * @return si le roi est en échec et mat
     */
    public static boolean checkForMate(Coord sC, List<IPiece> allys, List<IPiece> ennemies, Echiquier e){
        if(!isAttacked(sC, ennemies, e))
            return false;

        return getAllPossibleMoves(sC, allys, ennemies, e).isEmpty();
    }

    /**
     * Verifie si il y a une egalité par pat dans l'etat actuel de l'echiquier.
     * le pat est une regle qui reconnait comme etant une egalité le cas ou le joueur
     * actuel n'a pas de coup possible parmis toutes ces pieces
     * sans que le roi soit en echec.
     * @param sC la coordonnée sensible de l'allié
     * @param allys les pieces alliées
     * @param ennemies les pieces ennemies
     * @param e l'echiquier
     * @return l'egalité par pat est detecté
     */
    public static boolean isStaleMate(Coord sC, List<IPiece> allys, List<IPiece> ennemies, Echiquier e){
        if(isAttacked(sC, ennemies, e))
            return false;

        return getAllPossibleMoves(sC, allys, ennemies, e).isEmpty();
    }

    /**
     * Renvoie tout les movemements possibles d'un groupe de piece.
     * on ne cherche pas a acceder aux mouvement mais a les denombrer
     * @param sC la coordonnée sensible de l'allié
     * @param allys les pieces alliées
     * @param ennemies les pieces ennemies
     * @param e l'echiquier
     * @return tout les movemements possibles d'un groupe de piece
     */
    private static ArrayList<Coord> getAllPossibleMoves(Coord sC, List<IPiece> allys, List<IPiece> ennemies,
                                                        Echiquier e){
        ArrayList<Coord> allPossibleMoves = new ArrayList<>();

        for(IPiece p: allys)
            allPossibleMoves.addAll(p.getAllMoves(sC, ennemies, e));

        return allPossibleMoves;
    }

    /**
     * verifie avec si la coordonnée sensible est attaquée par les pieces adverses.
     * @param sC les coordonnées du roi
     * @param ennemies les pièces présentes sur l'échiquier
     * @param e l'echiquier
     * @return si une pièce(s) menace(s) la coordonnée sensible
     * @see Utils#getAllAttackingPiece(Coord, java.util.List, Echiquier)
     */
    public static boolean isAttacked(Coord sC, List<IPiece> ennemies, Echiquier e){
        return !Utils.getAllAttackingPiece(sC, ennemies, e).isEmpty();
    }

    /**
     * verifie si le materiel present sur l'echiquier est suffisant pour continuer une partie.
     * https://www.chess.com/terms/draw-chess#:~:text=A%20draw%20occurs%20in%20chess,player%20wins%20half%20a%20point.
     * @param allys         les pieces alliées
     * @param ennemies      les pieces ennemies
     * @return              si le matériel des joueurs est insuffisant pour mettre l'un l'autre en echec et mat
     */
    public static boolean impossibleMat(List<IPiece> allys, List<IPiece> ennemies){
        LinkedList<IPiece> allPieces = new LinkedList<>(ennemies);
        allPieces.addAll(allys);
        allPieces.removeIf(p -> !p.canHoldEndGame());

        return allPieces.isEmpty();
    }

    /**
     * Vérifie si le chemin entre 2 points n'a pas d'obstacle (pas l'arrivé)
     * @param cF coordonnées de la case d'arrivé
     * @param p la pièce à deplacer
     * @param e l'echiquier
     * @return la voie est libre
     */
    public static boolean voieLibre(IPiece p, Coord cF, Echiquier e){
        Coord cS = p.getCoord();
        LinkedList<Coord> path = Utils.getPath(cS, cF);
        path.removeIf(c -> c.equals(cF) || c.equals(cS) || e.estVide(c));
        return path.isEmpty();
    }

    /**
     * Vérifie si la l'arrivé d'une piece sur une case est valide.
     * @param p la pièce à deplacer
     * @param c coordonnées de la case
     * @param e l'echiquier
     * @return l'arrivée est valide
     */
    public static boolean isFinishValid(IPiece p, Coord c, Echiquier e){
        IPiece pA = e.getPiece(c);
        return !Couleur.areSameColor(p, pA) && !pA.estSensible();
    }
}
