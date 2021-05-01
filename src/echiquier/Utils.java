package echiquier;

import static echiquier.Echiquier.*;
import static echiquier.Regle.*;

import java.util.ArrayList;

import static echiquier.Echiquier.horsLimite;

public class Utils {

    /**
     * Renvoie une liste de coordonnées allant d'une position donnée à la limite de l'echiquier.
     * @param cS oord d'arrivé
     * @param cP mouvement primaire
     * @return les coordonnées
     */
    public static ArrayList<Coord> getPathToBorder(Coord cS, Coord cP) {
        ArrayList<Coord> tile = new ArrayList<>();
        Coord cClone = new Coord(cS.getX(), cS.getY());   // clone pour manipuler

        // on applique le mouvement primaire tant que la limite de l'echiquier n'est pas atteinte
        while (horsLimite(cClone)){
            Coord c = new Coord(cClone.getX(), cClone.getY());
            tile.add(c);        //add = ajout dans l'arraylist
            cClone.Add(cP);     //add = addition de coordonnées avec un mvnt primaire
        }
        return tile;
    }

    /**
     * Renvoie le chemin de coordonnées entre deux coordonnées (Une liste de coord).
     * @param cS la coordonnée de départ
     * @param cF la coordonnée d'arrivée
     * @return les coordonnées de la droite entre les deux coords.
     */
    public static ArrayList<Coord> getPath(Coord cS, Coord cF){
        ArrayList<Coord> tile = new ArrayList<>();
        Coord cClone = new Coord(cS.getX(), cS.getY());
        if(!Coord.isStraightPath(cS, cF))        //  si le chemin n'est pas verticale ou horizontale
            return tile;                        //  il y a pas de chemin de case attaqué.

        Coord pM = Coord.getPrimaryMove(cS, cF);

        while(!cClone.equals(cF)) {
            tile.add(new Coord(cClone.getX(), cClone.getY()));
            cClone.Add(pM);
        }

        tile.add(cClone);   //dernier ajout pour avoir la case d'arriver

        return tile;
    }

    /**
     * Renvoie un liste contenant tout les chemins entre les pieces attaquantes
     * et le roi jusqu'aux limites de l'echiquier.
     * Cela permet de retirer ces cases au mouvement possible du roi par exemple.
     * @param cR les coordonnées du roi
     * @param pieces les  pieces attaquant la coordonnée
     * @return les cases passant par les pieces attaquantes et le roi jusqu'a la limite de l'echiquier
     * @see Utils#getAllMoves(IPiece, Coord, String, ArrayList) exemple d'usage
     */
    public static ArrayList<Coord> getAllAttackedPath(Coord cR, ArrayList<IPiece> pieces){
        ArrayList<Coord> coords = new ArrayList<>();
        for(IPiece p : pieces){
            Coord cS = new Coord(p.getLigne(), p.getColonne());

            if(!Coord.isStraightPath(cS, cR))
                continue;

            // en partant de la piece attaquante on prend toutes les cases jusqu'aux limites
            // en passant pas le roi.
            Coord cP = Coord.getPrimaryMove(cS, cR);
            coords.addAll(getPathToBorder(cS, cP));
        }
        return coords;
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
     * le traitement peut etre different selon l'etat de la partie et de la piece:
     * si une piece est cloué celle-ci peut toujours se deplacer tant qu'elle est cloué.
     * si le roi est en echec les mouvements d'une pieces ne peut que bloqué cette attaque.
     * @param p la pièce
     * @param cR la position du roi
     * @param couleur la couleur de l'ennemi
     * @param ennemies les pièces ennemies
     * @return les coordonnées de déplacement possibles d'une pièce
     */
    public static ArrayList<Coord> getAllMoves(IPiece p, Coord cR, String couleur, ArrayList<IPiece> ennemies){
        Coord cS = new Coord(p.getLigne(), p.getColonne());
        ArrayList<Coord> allMoves = new ArrayList<>();

        if(isPiecePinned(p, cR, couleur))
            return allMovesFromPin(p, cS, cR, couleur);


        if(!p.getPieceType().equals("ROI") && isAttacked(cR, ennemies))
            return allMovesDefendingCheck(p, cR, getAllAttackingPiece(cR, ennemies));

        allMoves = allClassicMoves(p, cS, ennemies);

        if(p.getPieceType().equals("ROI"))
            allMoves.removeIf(getAllAttackedPath(cR, ennemies)::contains);    //les cases attaqués sont retirés

        return allMoves;
    }

    /**
     * renvoie la liste de toutes les coordonnées atteignable dans l'echiquier par une piece donnée.
     * @param p la piece
     * @param cS les coordonnées de la piece
     * @param ennemies les pieces ennemies
     * @return la liste des coordonnées atteignable
     */
    private static ArrayList<Coord> allClassicMoves(IPiece p, Coord cS, ArrayList<IPiece> ennemies){
        ArrayList<Coord> allClassicMoves = new ArrayList<>();

        for (int x = 0; x < LIGNE; x++) {
            for (int y = 0; y < COLONNE; y++) {

                if(x == cS.getX() && y == cS.getY()) continue;

                Coord c = new Coord(x, y);
                if(isCoupValid(c, p)) {

                    if(p.getPieceType().equals("ROI") && isAttacked(c, ennemies))   //si la piece est un roi et que
                        continue;                                                   //la coordonnées est attaqué
                    //le coup le est invalide
                    allClassicMoves.add(c);
                }
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
    public static ArrayList<Coord> allMovesDefendingCheck(IPiece p, Coord cR, ArrayList<IPiece> ennemies){
        ArrayList<Coord> moves = new ArrayList<>();

        if(ennemies.size() != 1 || p.getPieceType().equals("ROI"))     //s'il y a plusieurs piece attaquante
            return moves;                                                   //une piece ne peut pas defendre

        IPiece chkP = ennemies.get(0);
        Coord cF = new Coord(chkP.getLigne(), chkP.getColonne());
        ArrayList<Coord> path = getPath(cR, cF); //le chemin entre le roi
                                                // et la piece attaquante

        if(isCoupValid(cF, p)) {    //dans le cas ou un cavalier venait a mettre le roi en echec et mat,
            moves.add(cF);          //on regarde s'il est possible de le prendre sans passer par "path"
            path.remove(cF);        //car le chemin entre un cavalier et une piece n'est jamais
        }                           //droit.

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

        ArrayList<Coord> path = getPath(cS, new Coord(pningPiece.getLigne(), pningPiece.getColonne()));

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

}
