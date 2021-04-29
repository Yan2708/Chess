package echiquier;

import pieces.FabriquePiece;

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

    public static boolean priseEnDiag(IPiece pion,Coord c){
        IPiece p= Echiquier.getPiece(c);
        if(!pion.getPieceType().equals("PION"))
            return false;
        if(pion.getColonne()-p.getColonne()==0)
            return !p.getPieceType().equals("VIDE");
        else
            return p.getPieceType().equals("VIDE");
    }

    public static void promotion(Coord c){
        IPiece p= Echiquier.getPiece(c);
        char type = p.getCouleur().equals("NOIR") ? 'd': 'D';
        if(p.getPieceType().equals("PION") && p.getLigne()%7==0)
            Echiquier.promote(c,new FabriquePiece(),type);
    }

    public static boolean priseEnPassant(Coord lastCS, Coord lastCF, Coord cS,Coord cF){
        IPiece p,current;
        try{
            p = Echiquier.getPiece(lastCF);
            current = Echiquier.getPiece(cS);
        }catch (NullPointerException e){
            return false;
        }
        int mouvement =current.getCouleur().equals("NOIR")?1:-1;
        return p.getPieceType().equals(current.getPieceType()) && current.getPieceType().equals("PION") &&
                Math.abs(lastCF.getX() - lastCS.getX()) == 2 && !p.getCouleur().equals(current.getCouleur()) &&
                cF.getY() == lastCF.getY() && cF.getX() - lastCF.getX() == mouvement;
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
        ArrayList<Coord> checkingTiles = getAllCheckingTiles(cR, checkingPieces);

        if(checkingPieces.isEmpty()) return false;

        //si une piece met le roi en echec alors il est possible de defendre avec une autre piece.
        if(!(checkingPieces.size() > 1) &&
                isBlocked(checkingTiles, pieces, couleur, cR))
            return false;

        //verifie si chaque mouvement possible du roi est sans danger
        ArrayList<Coord> kingsMoves = getAllMoves(getPiece(cR), cR, couleur, pieces);
        kingsMoves.removeIf(getAllAttackedPath(cR, checkingPieces)::contains);
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
     * AFAIRE
     * @return              une piece peut etre posée dans le chemin
     */
    private static boolean isBlocked(ArrayList<Coord> tile, ArrayList<IPiece> enemies, String couleur, Coord cR){
        ArrayList<IPiece> Allys = getPieceFromColor(couleur);  //liste de piece allié
        String couleurOppose = (couleur.equals("BLANC")) ? "NOIR" : "BLANC";

        for(IPiece p : Allys){
            ArrayList<Coord> moves = getAllMoves(p, cR, couleurOppose, enemies);
            for(Coord c : tile)
                if(moves.contains(c))
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
    public static boolean isPiecePinned(IPiece p, Coord cR, String couleur){
        Coord cS = new Coord(p.getLigne(), p.getColonne());

        if(p.getPieceType().equals("ROI")   ||
                !isStraightPath(cS, cR)     ||
                !voieLibre(p, cR))
            return false;

        Coord cP = getPrimaryMove(cS, cR);              // Les cases en partant de la piece
        cP.inverse();                                   // vers la direction inverse du roi
        ArrayList<Coord> allTile = getPath(cS, cP);  //

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
    private static ArrayList<Coord> getPath(Coord cS, Coord cP) {
        ArrayList<Coord> tile = new ArrayList<>();
        Coord cClone = new Coord(cS.getX(), cS.getY());   // clone pour manipuler

        // on applique le mouvement primaire tant que la limite de l'echiquier n'est pas atteinte
        cClone.Add(cP);
        while (horsLimite(cClone)){
            Coord c = new Coord(cClone.getX(), cClone.getY());
            tile.add(c);
            cClone.Add(cP);
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
    public static ArrayList<Coord> getAllCheckingTiles(Coord cR, ArrayList<IPiece> pieces){
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
        Coord cClone = new Coord(cS.getX(), cS.getY());
        if(!isStraightPath(cS, cF))        //  si le chemin n'est pas verticale ou horizontale
            return checkingTile;            //  il y a pas de chemin de case attaqué.

        Coord pM = getPrimaryMove(cS, cF);

        while(!cClone.equals(cF)) {
            checkingTile.add(new Coord(cClone.getX(), cClone.getY()));
            cClone.Add(pM);
        }
        return checkingTile;
    }

    /** Renvoie un liste de coordonnées definissant le chemin entre un piece attaquant
     * le roi et la limite de l'echiquier en passant par le roi.
     *
     * @param c        les coordonnées du roi
     * @param pieces    les  pieces qui mettent le roi en echec
     * @return          les cases passant par les pieces attaquantes et le roi jusqu'a la limite de l'echiquier
     */
    private static ArrayList<Coord> getAllAttackedPath(Coord c, ArrayList<IPiece> pieces){
        ArrayList<Coord> coords = new ArrayList<>();
        for(IPiece p : pieces){
            Coord cS = new Coord(p.getLigne(), p.getColonne());

            if(!isStraightPath(cS, c))
                continue;

            // en partant de la piece attaquante on prend toutes les cases jusqu'aux limites
            // en passant pas le roi.
            Coord cP = getPrimaryMove(cS, c);
            coords.addAll(getPath(cS, cP));
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
    public static ArrayList<IPiece> getAllCheckingPiece(Coord c, ArrayList<IPiece> pieces){
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

    public static boolean isStaleMate(ArrayList<IPiece> pieces, Coord cR, String couleur){
        ArrayList<Coord> allPossibleMoves = new ArrayList<>();
        if(checkIfCheck(cR, pieces))
            return false;

        for(IPiece p: pieces)
            allPossibleMoves.addAll(getAllMoves(p, cR, couleur, pieces));

        return allPossibleMoves.isEmpty();
    }

    private static ArrayList<Coord> getAllMoves(IPiece p, Coord cR, String couleur, ArrayList<IPiece> pieces){
        ArrayList<Coord> allMoves = new ArrayList<>();

        if(isPiecePinned(p, cR, couleur))
            return allMoves;

        for (int x = 0; x < LIGNE; x++) {
            for (int y = 0; y < COLONNE; y++) {
                Coord c = new Coord(x, y);
                if(isCoupValid(c, p)) {

                    if(p.getPieceType().equals("ROI") && checkIfCheck(c, pieces))
                        continue;

                    allMoves.add(c);
                }
            }
        }
        return allMoves;
    }
}
