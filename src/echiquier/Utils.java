package echiquier;

import coordonnee.Coord;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static echiquier.Couleur.*;

/**
 * classe regroupant les utilitaires d'un echiquier.
 */
public class Utils {

    /**
     * Renvoie le chemin de coordonnées entre deux coordonnées (Une liste de coord).
     * @param cS la coordonnée de départ
     * @param cF la coordonnée d'arrivée
     * @return les coordonnées de la droite entre les deux coords.
     */
    public static LinkedList<Coord> getPath(Coord cS, Coord cF){
        LinkedList<Coord> tile = new LinkedList<>(Collections.singletonList(cF));
        Coord cClone = cS.clone();
        if(!Coord.isStraightPath(cS, cF))        //  si le chemin n'est pas verticale ou horizontale
            return tile;                         //  il y a pas de chemin de case attaqué.

        Coord pM = Coord.getPrimaryMove(cS, cF);

        while(!cClone.equals(cF)) {
            tile.add(cClone.clone());
            cClone.add(pM);
        }

        return tile;
    }

    /**
     * Retourne toutes les pièces attaquant une coordonnées.
     * @param c la coordonnée
     * @param pieces les pièces enemies
     * @param e l'echiquier
     * @return une liste des pièces attaquantes
     */
    public static LinkedList<IPiece> getAllAttackingPiece(Coord c, List<IPiece> pieces, Echiquier e){
        LinkedList<IPiece> cPiece = new LinkedList<>();
        for(IPiece p : pieces)
            if(p.peutAttaquer(c, e))
                cPiece.add(p);
        return cPiece;
    }

    /**
     * Retourne les déplacements possibles d'une pièce pour défendre une coordonnée sensible
     * @param p la pièce
     * @param sC la coordonnée sensible
     * @param ennemies les pièces ennemies
     * @param e l'echiquier
     * @return les coordonnées possibles de la pièce pour défendre
     */
    public static LinkedList<Coord> allMovesDefendingCheck(IPiece p, Coord sC, List<IPiece> ennemies, Echiquier e){
        LinkedList<Coord> moves = new LinkedList<>();
        LinkedList<IPiece> attackingPiece = getAllAttackingPiece(sC, ennemies, e);

        if(attackingPiece.size() != 1)     //s'il y a plusieurs piece attaquante
            return moves;                  //une piece ne peut pas defendre

        Coord cF = attackingPiece.get(0).getCoord();
        LinkedList<Coord> path = getPath(sC, cF);    //le chemin entre la piece sensible
                                                    // et la piece attaquante
        return allValidMovesFromPath(p, path, e);
    }

    /**
     * Retourne les coordonnées de déplacement possibles pour une pièce clouée
     * @param p la pièce clouée
     * @param sC la coordonné sensible qui donne lieu au clouage
     * @param couleur la couleur des ennemis
     * @param e l'echiquier
     * @return la liste des coordonnées
     */
    public static LinkedList<Coord> allMovesFromPin(IPiece p, Coord sC, Couleur couleur, Echiquier e){
        LinkedList<Coord> moves = new LinkedList<>();
        Coord cS = p.getCoord();
        IPiece pningPiece = getPningPiece(cS, sC, couleur, e); //la piece clouante

        if(pningPiece == null)
            return moves;

        List<Coord> path = getPath(cS, pningPiece.getCoord());
        return allValidMovesFromPath(p, path, e);
    }

    /**
     * Renvoie pour un chemin de coordonnée donné les coordonnées que la piece
     * peut acceder.
     * @param p la piece
     * @param path le chemin
     * @param e l'echiquier
     * @return les coordonnées accessible
     */
    private static LinkedList<Coord> allValidMovesFromPath(IPiece p, List<Coord> path, Echiquier e){
        LinkedList<Coord> moves = new LinkedList<>();
        for(Coord c : path)
            if(p.isCoupValid(c, e))
                moves.add(c);
        return moves;
    }

    /**
     * Retourne la pièce susceptible d'attaquer une coordonnée sensible,
     * si pièce testé n'est plus la.
     * Clouage en francais ou Pin en anglais.
     * @param cS la position de la pièce
     * @param sC la coordonnée sensible de l'allié
     * @param couleur la couleur ennemie
     * @param e l'echiquier
     * @return la pièce qui cloue ou rien
     */
    public static IPiece getPningPiece(Coord cS, Coord sC, Couleur couleur, Echiquier e){
        Couleur oppositeColor = couleur == BLANC ? NOIR : BLANC;
        List<Coord> pathToBorder = getPathToBorder(cS, Coord.getPrimaryMove(sC, cS));
        for(Coord c : pathToBorder){
            IPiece piece = e.getPiece(c);

            if(Regle.isRightColor(piece, oppositeColor) &&            // si dans ce chemin il y a une piece
                    piece.isCoupValid(cS, e) &&                       // si la piece peut prendre la piece clouée
                    piece.estPossible(sC))                            // et le roi juste apres.
                return piece;
        }
        return null;    //aucune piece clouante
    }

    /**
     * Renvoie une liste de coordonnées allant d'une position donnée à la limite de l'echiquier.
     * Cette methode fonction grace au mouvement primaire pour retrouver la coordonnée
     * delimitant la fin de l'echiquier.
     * @param cS coord d'arrivé
     * @param cP mouvement primaire
     * @return les coordonnées
     */
    public static LinkedList<Coord> getPathToBorder(Coord cS, Coord cP) {
        Coord cF = cS.clone();   // clone pour manipuler

        // on applique le mouvement primaire tant que la limite de l'echiquier n'est pas atteinte
        while(Echiquier.inBound(cF)){
            cF.add(cP);     //add = addition de coordonnées avec un mvnt primaire
        }
        cF.add(new Coord(-(cP.getX()), -(cP.getY()) ) );   //on revient d'un en arriere car on est hors limite (boucle while)
        return getPath(cS, cF);
    }

}
