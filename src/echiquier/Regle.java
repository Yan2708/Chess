package echiquier;

import static echiquier.Echiquier.*;
import java.util.ArrayList;


public class Regle {

    /** Renvoie si le mouvement d'une piece vers une coordonnée est valide.
     * un coup est valide si : le mouvement est possible pour la piece
     * le chemin entre la piece et l'arrivé n'a pas d'obstacle
     * et que la case d'arrivé peut acceuillir la piece.
     *
     * @param c     la coordonnée d'arrivée
     * @param p     la coordonnée de la piece
     * @return      si le déplacement est possible
     */
    public static boolean isCoupValid(Coord c, IPiece p){
        return p.estPossible(c.getX(),c.getY()) && voieLibre(p, c) && isFinishValid(p, c);
    }

    /** Verifie si pour une couleur donnée le roi est en echec et mat.
     * La premiere partie verifie si un piece alliée au roi peut bloquer l'echec
     * la seconde partie verifie si le roi peut s'echapper de l'echec.
     *
     * @param couleur       la couleur du roi
     * @param cR            les coordonnées du roi
     * @param pieces        toutes les pieces de l'enemi
     * @return              si le roi est en échec et mat
     */
    public static boolean checkForMate(String couleur, Coord cR, ArrayList<IPiece> pieces){
        ArrayList<IPiece> checkingPieces = getAllCheckingPiece(cR, pieces); //un roi peut etre mis en echec par 2 pieces
        ArrayList<Coord> checkingTiles = getAllCheckingTiles(cR, pieces);

        //si une piece met le roi en echec alors il est possible de defendre avec une autre piece.
        if(!(checkingPieces.size() > 1)){
            IPiece p = checkingPieces.get(0);
            if(piecesInTheWay(checkingTiles, couleur, cR, p))
                return false;
        }

        //verifie si chaque mouvement possible du roi est sans danger
        ArrayList<Coord> kingsMoves = getKingsMoves(cR, checkingPieces);
        for(Coord c : kingsMoves)
            if(!checkIfCheck(c, pieces))
                return false;

        return true;
    }

    /** Verifie si une piece peut s'interposer entre la piece qui met en echec le roi et le roi.
     *  Cette piece doit valider 3 conditions :
     *  elle ne doit pas etre le roi, le coup doit etre valide et la piece ne doit pas etre cloué.
     *
     * @param tile          les cases attaquées à defendre
     * @param couleur       la couleur allié
     * @param cR            les coordonnées du roi
     * @param p             la piece mettant le roi en echec
     * @return              une piece peut etre posée dans le chemin
     */
    private static boolean piecesInTheWay(ArrayList<Coord> tile, String couleur, Coord cR, IPiece p){
        ArrayList<IPiece> pieces = getPieceFromColor(couleur);  //liste de piece allié
        String couleurOppose = (couleur.equals("BLANC")) ? "NOIR" : "BLANC";
        Coord cCheck = new Coord(p.getLigne(),p.getColonne());  //position de la piece qui met le roi en echec

        for(IPiece pc : pieces){
            if(pc.getPieceType().equals("ROI")) continue;           // ne doit pas etre le roi
            if(isPiecePinned(pc, cR, couleurOppose)) continue;      // ne doit pas etre cloué
            Coord cP = new Coord(pc.getLigne(), pc.getColonne());

            if(isCoupValid(cCheck, pc)) //s'il est possible de prendre la piece attaquante
                return true;

            // si une piece allié peut se positionner entre le roi et la piece attaquante
            for(Coord cF : tile)
                if(isCoupValid(cF, pc))
                    return true;
        }
        return false;
    }

    /** Verifie si une piece est cloué en verifiant toutes les cases en direction inverse du roi
     * en partant de la piece.
     *
     * @param p         la pièce à verifier
     * @param cR        les coordonnées du roi
     * @param couleur   la couleur opposée
     * @return          si la pièce est clouée
     */
    private static boolean isPiecePinned(IPiece p, Coord cR, String couleur){
        Coord cS = new Coord(p.getLigne(), p.getColonne());

        if(!isStraightPath(cS, cR))             // si le chemin entre la piece est le roi n'est pas verticale
            return false;                       //  ou horizontale, la piece ne peut pas etre cloué.

        if(!voieLibre(p, cR))       // si le chemin entre la piece et le roi
            return false;           // possede un obstacle alors la piece ne peut pas etre cloué.

        Coord cP = getPrimaryMove(cS, cR);              // Les cases en partant de la piece
        cP.inverse();                                   // vers la direction inverse du roi
        ArrayList<Coord> allTile = getAllTile(cS, cP);  //

        for(Coord c : allTile){
            IPiece piece = getPiece(c);

            if(piece.getCouleur().equals(couleur) &&        // si dans ce chemin il y a une piece
                isCoupValid(cS, piece) &&                   // suceptible d'attaquer la piece qui defend
                piece.estPossible(cR.getX(), cR.getY()))    // et le roi juste apres.

                return true;
        }
        return false;
    }

    /** Renvoie une liste de coordonnées allant d'une position donnée à la limite de l'echiquier.
     *
     * @param cS            coord d'arrivé
     * @param cP            mouvement primaire
     * @return              les coordonnées
     */
    private static ArrayList<Coord> getAllTile(Coord cS, Coord cP) {
        ArrayList<Coord> tile = new ArrayList<>();
        Coord cNew = new Coord(cS.getX(), cS.getY());   // coordonnée à qjouter

        // on applique le mouvement primaire tant que la limite de l'echiquier n'est pas atteinte
        cNew.Add(cP);
        while (horsLimite(cNew)){
            Coord c = new Coord(cNew.getX(), cNew.getY());
            tile.add(c);
            cNew.Add(cP);
        }
        return tile;
    }

    /** Renvoie une liste de coordonnées que composent les cases en travers des pieces qui mettent le roi
     * en echec et le roi.
     *
     * @param cR        coordonnée du roi
     * @param pieces    les pieces enemies
     * @return          les coordonnées entre le roi et la piece qui la met en echec
     */
    private static ArrayList<Coord> getAllCheckingTiles(Coord cR, ArrayList<IPiece> pieces){
        ArrayList<Coord> checkingTile = new ArrayList<>();

        // on recupere toutes les cases dans le cas ou plusieurs pieces attaquent le roi
        for(IPiece p : pieces){
            checkingTile.addAll(getCheckingTile(new Coord(p.getLigne(), p.getColonne()), cR));
        }
        return checkingTile;
    }

    /** Renvoie une liste de coordonnées de case entre la piece qui met le roi en echec et le roi.
     *
     * @param cS        la coordonnée de départ
     * @param cF        la coordonnée d'arrivée
     * @return          les coordonnées de la demi-droite entre la piece qui le met en echec et le roi
     */
    private static ArrayList<Coord> getCheckingTile(Coord cS, Coord cF){
        ArrayList<Coord> checkingTile = new ArrayList<>();

        if(!isStraightPath(cS, cF))        //  si le chemin n'est pas verticale ou horizontale
            return checkingTile;            //  il y a pas de chemin de case attaqué.

        Coord pM = getPrimaryMove(cS, cF);
        // on applique le mouvement primaire une premiere fois
        // pour ne pas tester sur la case de la piece
        cS.Add(pM);

        while(cS.equals(cF)) {
            checkingTile.add(new Coord(cS.getX(), cS.getY()));
            cS.Add(pM);
        }
        return checkingTile;
    }

    /** Renvoie une liste de coordonnées de tout les mouvements possibles pour le roi.
     * on retire au prealable toutes coordonnées dans les chemins que prennent les pieces qui attaquent le roi.
     *
     * @param cR        la coordonnée du roi
     * @param cPiece    les pieces enemies
     * @return          les coordonnées où le roi peut se rendre
     */
    private static ArrayList<Coord> getKingsMoves(Coord cR, ArrayList<IPiece> cPiece){
        ArrayList<Coord> kingsMoves = new ArrayList<>();
        IPiece roi = getPiece(cR);
        ArrayList<Coord> chekingFile = getAllCheckingFiles(cR, cPiece); //toutes les lignes attaquées

        //on test toutes les cases adjacentes au roi
        for(int x = -1 ; x <= 1; x++){
            for (int y = -1; y <= 1; y++) {
                if(x != 0 || y != 0){
                    Coord cF = new Coord(cR.getX() + x, cR.getY() + y);

                    //  le mouvement doit etre possible et dans l'echiquier
                    if(horsLimite(cF) && isCoupValid(cF, roi))
                        kingsMoves.add(cF);
                }
            }
        }
        kingsMoves.removeIf(chekingFile::contains); // on retire toutes les coordonnées où le roi est en echec
        return kingsMoves;
    }

    /** Renvoie un liste de coordonnées definissant le chemin entre un piece attaquant
     * le roi et la limite de l'echiquier en passant par le roi.
     *
     * @param cR        les coordonnées du roi
     * @param pieces    les  pieces de l'enemi
     * @return          les coordonnées où le roi est en echec
     */
    private static ArrayList<Coord> getAllCheckingFiles(Coord cR, ArrayList<IPiece> pieces){
        ArrayList<Coord> coords = new ArrayList<>();
        for(IPiece p : pieces){
            Coord cS = new Coord(p.getLigne(), p.getColonne());

            if(!isStraightPath(cS, cR))
                continue;

            // en partant de la piece attaquante on prend toutes les cases jusqu'aux limites
            // en passant pas le roi.
            Coord cP = getPrimaryMove(cS, cR);
            coords.addAll(getAllTile(cS, cP));
        }
        return coords;
    }

    /** verifie avec les coordonnée du roi si celui-ci est attaquée par les pieces adverses.
     *
     * @param cR            les coordonnées du roi
     * @param pieces        les pièces présente sur l'échiquier
     * @return              si une pièce(s) menace(s) le roi
     */
    public static boolean checkIfCheck(Coord cR, ArrayList<IPiece> pieces){
        for(IPiece p : pieces)
            // la coordonnées est attaqué par une piece adverse
            if(isCheck(cR, p))
                return true;

        return false;
    }

    /** Retourne toutes les pièces mettant en echec le roi
     *
     * @param c         les coordonnées du roi
     * @param pieces    les pièces enemies
     * @return          une liste des pièces mettant le roi en echec
     */
    private static ArrayList<IPiece> getAllCheckingPiece(Coord c, ArrayList<IPiece> pieces){
        ArrayList<IPiece> cPiece = new ArrayList<>();
        for(IPiece p : pieces)
            if(isCheck(c, p))
                cPiece.add(p);
        return cPiece;
    }

    /** Retourne si le roi est mit en echec par une pièce ou non
     *
     * @param c         les coordonnées du roi
     * @param p         la pièce en question
     * @return          si le roi est en echec
     */
    private static boolean isCheck(Coord c, IPiece p){
        // on ne test pas si l'arrivé est valide car on test avec les coordonnées du roi.
        return p.estPossible(c.getX(), c.getY()) && voieLibre(p, c);
    }
}
