package echiquier;

import static echiquier.Echiquier.*;
import static echiquier.Regle.*;

import java.util.ArrayList;

import java.util.Collections;

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
            cClone.Add(pM);
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
            if(canBeAttacked(c, p))
                cPiece.add(p);
        return cPiece;
    }


    /**
     * Retourne toutes les coordonnées possibles pour une pièce
     * le traitement peut etre different selon l'etat de la partie et de la piece.
     * @param p la pièce
     * @param cR la position du roi
     * @param couleur la couleur de l'ennemi
     * @param ennemies les pièces ennemies
     * @return les coordonnées de déplacement possibles d'une pièce
     */
    public static ArrayList<Coord> getAllMoves(IPiece p, Coord cR, String couleur, ArrayList<IPiece> ennemies){
        Coord cS = Coord.coordFromPiece(p);

        if(p.getPieceType().equals("ROI"))  //traitement different pour le roi
            return kingsMoves(p, ennemies);

        if(isPiecePinned(p, cR, couleur))   //traitement different pour les pieces clouées
            return allMovesFromPin(p, cS, cR, couleur);

        if(isAttacked(cR, ennemies))    //lorsque le roi est en echec
            return allMovesDefendingCheck(p, cR, getAllAttackingPiece(cR, ennemies));

        return allClassicMoves(p);
    }

    /**
     * les mouvements du roi subissent un traitement special, aucune coordonnée
     * ne doit etre attaquées.
     * @param roi le roi
     * @param ennemies les pieces ennemies
     * @return la liste des mouvements possible pour le roi (sécurisés)
     */
    private static ArrayList<Coord> kingsMoves(IPiece roi, ArrayList<IPiece> ennemies){
        ArrayList<Coord> moves = allClassicMoves(roi);
        ArrayList<Coord> behindKings = behindKing(Coord.coordFromPiece(roi), ennemies);
        moves.removeIf(c -> isAttacked(c, ennemies) || behindKings.contains(c));
        return moves;
    }

    /**
     * si une piece attaque le roi, ajoute a la liste la derriere le roi en partant de cette piece.
     * @param cR les coordonnées du roi
     * @param ennemies les pieces ennemies
     * @return liste de cases possiblement attaqué protégé par le roi (car en travers du chemin)
     */
    private static ArrayList<Coord> behindKing(Coord cR, ArrayList<IPiece> ennemies){
        ArrayList<Coord> coords = new ArrayList<>();
        for(IPiece p : ennemies) {
            if(canBeAttacked(cR, p)) {
                Coord cClone = cR.clone();
                cClone.Add(Coord.getPrimaryMove(Coord.coordFromPiece(p), cR));
                if(inBound(cClone))
                    coords.add(cClone);
            }
        }
        return coords;
    }

    /**
     * renvoie la liste de toutes les coordonnées atteignable dans l'échiquier par une piece donnée.
     * @param p la piece
     * @return la liste des coordonnées atteignable
     */
    private static ArrayList<Coord> allClassicMoves(IPiece p){
        ArrayList<Coord> allClassicMoves = new ArrayList<>();

        for (int x = 0; x < LIGNE; x++) {
            for (int y = 0; y < COLONNE; y++) {

                if(x == p.getLigne() && y == p.getColonne()) continue;

                Coord c = new Coord(x, y);
                if(isCoupValid(c, p))
                    allClassicMoves.add(c);
            }
        }
        return allClassicMoves;
    }

    /**
     * Retourne les déplacements possibles d'une pièce pour défendre son roi
     * @param p la pièce
     * @param cR la position du roi
     * @param ennemies les pièces ennemies
     * @return les coordonnées possibles de la pièce pour défendre son roi
     */
    private static ArrayList<Coord> allMovesDefendingCheck(IPiece p, Coord cR, ArrayList<IPiece> ennemies){
        ArrayList<Coord> moves = new ArrayList<>();

        if(ennemies.size() != 1 || p.getPieceType().equals("ROI"))     //s'il y a plusieurs piece attaquante
            return moves;                                                   //une piece ne peut pas defendre

        Coord cF = Coord.coordFromPiece(ennemies.get(0));
        ArrayList<Coord> path = getPath(cR, cF); //le chemin entre le roi
                                                // et la piece attaquante

        for(Coord c : path)
            if(isCoupValid(c, p))
                moves.add(c);
        return moves;
    }

    /**
     * Retourne les coordonnées de déplacement possibles pour une pièce clouée
     * @param p la pièce clouée
     * @param cS les coordonnées de la pièce
     * @param cR les coordonnées du roi
     * @param couleur la couleur des ennemis
     * @return la liste des coordonnées
     */
    private static ArrayList<Coord> allMovesFromPin(IPiece p, Coord cS, Coord cR, String couleur){
        ArrayList<Coord> moves = new ArrayList<>();
        IPiece pningPiece = getPningPiece(cS, cR, couleur); //la piece clouante

        if(getPningPiece(cS, cR, couleur) == null)
            return moves;

        ArrayList<Coord> path = getPath(cS, Coord.coordFromPiece(pningPiece));

        for(Coord c : path)
            if(isCoupValid(c, p))
                moves.add(c);
        return moves;
    }

    /**
     * Retourne la pièce susceptible d'attaquer le roi si la pièce clouée n'est plus là
     * @param cS la position de la pièce
     * @param cR la position du roi
     * @param couleur la couleur ennemie
     * @return la pièce qui cloue ou rien
     */
    public static IPiece getPningPiece(Coord cS, Coord cR, String couleur){
        ArrayList<Coord> pathToBorder = getPathToBorder(cS, Coord.getPrimaryMove(cS, cR).inverse());
        for(Coord c : pathToBorder){
            IPiece piece = getPiece(c);

            if(piece.getCouleur().equals(couleur) &&            // si dans ce chemin il y a une piece
                    isCoupValid(cS, piece) &&                   // si la piece peut prendre la piece clouée
                    piece.estPossible(cR.getX(), cR.getY()))    // et le roi juste apres.

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
    private static ArrayList<Coord> getPathToBorder(Coord cS, Coord cP) {
        ArrayList<Coord> tile = new ArrayList<>();
        Coord cClone = cS.clone();   // clone pour manipuler

        // on applique le mouvement primaire tant que la limite de l'echiquier n'est pas atteinte
        while(inBound(cClone)){
            Coord c = cClone.clone();
            tile.add(c);        //add = ajout dans l'arraylist
            cClone.Add(cP);     //add = addition de coordonnées avec un mvnt primaire
        }
        return tile;
    }

}
