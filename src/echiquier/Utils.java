package echiquier;

import coordonnee.Coord;
import java.util.ArrayList;
import java.util.Collections;
import static echiquier.Couleur.*;


import static echiquier.Echiquier.inBound;


public class Utils {

    /**
     * Renvoie le chemin de coordonnées entre deux coordonnées (Une liste de coord).
     * @param cS la coordonnée de départ
     * @param cF la coordonnée d'arrivée
     * @return les coordonnées de la droite entre les deux coords.
     */
    public static ArrayList<Coord> getPath(Coord cS, Coord cF){
        ArrayList<Coord> tile = new ArrayList<>(Collections.singletonList(cF));
        Coord cClone = cS.clone();
        if(!Coord.isStraightPath(cS, cF))        //  si le chemin n'est pas verticale ou horizontale
            return tile;                        //  il y a pas de chemin de case attaqué.

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
     * @return une liste des pièces attaquantes
     */
    public static ArrayList<IPiece> getAllAttackingPiece(Coord c, ArrayList<IPiece> pieces){
        ArrayList<IPiece> cPiece = new ArrayList<>();
        for(IPiece p : pieces)
            if(p.peutAttaquer(c))
                cPiece.add(p);
        return cPiece;
    }


    /**
     * renvoie la liste de toutes les coordonnées atteignable dans l'échiquier par une piece donnée.
     * @param p la piece
     * @return la liste des coordonnées atteignable
     */
    public static ArrayList<Coord> allClassicMoves(IPiece p){
        ArrayList<Coord> allClassicMoves = new ArrayList<>();
        Coord cP = p.getCoord();

        for (int x = 0; x < Echiquier.LIGNE; x++) {
            for (int y = 0; y < Echiquier.COLONNE; y++) {

                if(x == cP.x && y == cP.y) continue;

                Coord c = new Coord(x, y);
                if((p.isCoupValid(c)))
                    allClassicMoves.add(c);
            }
        }
        return allClassicMoves;
    }

    /**
     * Retourne les déplacements possibles d'une pièce pour défendre son roi
     * @param p la pièce
     * @param sC la position du roi
     * @param ennemies les pièces ennemies
     * @return les coordonnées possibles de la pièce pour défendre son roi
     */
    public static ArrayList<Coord> allMovesDefendingCheck(IPiece p, Coord sC, ArrayList<IPiece> ennemies){
        ArrayList<Coord> moves = new ArrayList<>();
        ArrayList<IPiece> attackingPiece = getAllAttackingPiece(sC, ennemies);


        if(attackingPiece.size() != 1)     //s'il y a plusieurs piece attaquante
            return moves;                  //une piece ne peut pas defendre

        Coord cF = Coord.coordFromPiece(attackingPiece.get(0));
        ArrayList<Coord> path = getPath(sC, cF);    //le chemin entre la piece sensible
                                                    // et la piece attaquante
        return allValidMovesFromPath(p, path);
    }

    /**
     * Retourne les coordonnées de déplacement possibles pour une pièce clouée
     * @param p la pièce clouée
     * @param sC les coordonnées du roi
     * @param couleur la couleur des ennemis
     * @return la liste des coordonnées
     */
    public static ArrayList<Coord> allMovesFromPin(IPiece p, Coord sC, Couleur couleur){
        ArrayList<Coord> moves = new ArrayList<>();
        Coord cS = p.getCoord();
        IPiece pningPiece = getPningPiece(cS, sC, couleur); //la piece clouante

        if(pningPiece == null)
            return moves;

        ArrayList<Coord> path = getPath(cS, Coord.coordFromPiece(pningPiece));
        return allValidMovesFromPath(p, path);
    }

    /**
     *
     * @param p
     * @param path
     * @return
     */
    private static ArrayList<Coord> allValidMovesFromPath(IPiece p, ArrayList<Coord> path){
        ArrayList<Coord> moves = new ArrayList<>();
        for(Coord c : path)
            if(p.isCoupValid(c))
                moves.add(c);
        return moves;
    }

    /**
     * Retourne la pièce susceptible d'attaquer le roi si la pièce clouée n'est plus là
     * @param cS la position de la pièce
     * @param sC la position du roi
     * @param couleur la couleur ennemie
     * @return la pièce qui cloue ou rien
     */
    public static IPiece getPningPiece(Coord cS, Coord sC, Couleur couleur){
        Couleur oppositeColor = couleur == BLANC ? NOIR : BLANC;
        ArrayList<Coord> pathToBorder = getPathToBorder(cS, Coord.getPrimaryMove(sC, cS));
        for(Coord c : pathToBorder){
            IPiece piece = Echiquier.getPiece(c);

            if(Regle.isRightColor(piece, oppositeColor) &&            // si dans ce chemin il y a une piece
                    piece.isCoupValid(cS) &&               // si la piece peut prendre la piece clouée
                    piece.estPossible(sC))                // et le roi juste apres.
                return piece;
        }
        return null;    //aucune piece clouante
    }

    /**
     * Renvoie une liste de coordonnées allant d'une position donnée à la limite de l'echiquier.
     * @param cS oord d'arrivé
     * @param cP mouvement primaire
     * @return les coordonnées
     */
    public static ArrayList<Coord> getPathToBorder(Coord cS, Coord cP) {
        Coord cF = cS.clone();   // clone pour manipuler

        // on applique le mouvement primaire tant que la limite de l'echiquier n'est pas atteinte
        while(inBound(cF)){
            cF.add(cP);     //add = addition de coordonnées avec un mvnt primaire
        }
        cF.add(cP.inverse());   //on revient d'un en arriere car on est hors limite (boucle while)
        return getPath(cS, cF);
    }

}
