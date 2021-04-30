package echiquier;


import static echiquier.Utils.*;
import static echiquier.Echiquier.*;
import static java.lang.Math.abs;

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
        boolean possible = p.estPossible(c.getX(),c.getY()) && voieLibre(p, c) && isFinishValid(p, c);
        boolean isPawn = p.getPieceType().equals("PION");
        boolean isDiag = priseEnDiag(p, c);
        String color = getPiece(c).getCouleur();
        boolean isEnemy = !color.equals(p.getCouleur()) && !color.equals("VIDE");
        //return possible && (!isPawn || (!isDiag || !isEnemy));
//        return p.estPossible(c.getX(),c.getY()) && voieLibre(p, c) && isFinishValid(p, c) &&
//                (!p.getPieceType().equals("PION") || (!priseEnDiag(p, c) || !getPiece(c).getPieceType().equals(p.getCouleur())));

        return possible && (isPawn ? (isDiag ? isEnemy : true) : true);
        //return !possible || !isPawn |c4| (!isDiag || isEnemy);
    }

    private static boolean priseEnDiag(IPiece pion,Coord c){
        int diffX = abs(c.getX() - pion.getLigne());
        int diffY = abs(c.getY() - pion.getColonne());
        return diffX == 1 && diffY == 1;
    }

//    public static boolean priseEnPassant(Coord lastCS, Coord lastCF, Coord cS,Coord cF){
//        IPiece p,current;
//        try{
//            p = Echiquier.getPiece(lastCF);
//            current = Echiquier.getPiece(cS);
//        }catch (NullPointerException e){
//            return false;
//        }
//        int mouvement =current.getCouleur().equals("NOIR")?1:-1;
//        return p.getPieceType().equals(current.getPieceType()) && current.getPieceType().equals("PION") &&
//                Math.abs(lastCF.getX() - lastCS.getX()) == 2 && !p.getCouleur().equals(current.getCouleur()) &&
//                cF.getY() == lastCF.getY() && cF.getX() - lastCF.getX() == mouvement;
//    }

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

        if(checkingPieces.isEmpty()) return false;

        //si une piece met le roi en echec alors il est possible de defendre avec une autre piece.
        if(!(checkingPieces.size() > 1) &&
                isBlocked(checkingPieces, couleur, cR))
            return false;

        //verifie si chaque mouvement possible du roi est sans danger
        ArrayList<Coord> kingsMoves = getAllMoves(getPiece(cR), cR, couleur, pieces);
        for(Coord c : kingsMoves)
            if(!checkIfCheck(c, pieces))
                return false;

        return true;
    }

    /** Verifie si une piece peut s'interposer entre la piece qui met en echec le roi et le roi.
     *  Cette piece doit valider 3 conditions :
     *  elle ne doit pas etre le roi, le coup doit etre valide et la piece ne doit pas etre clouée.
     *
     * @param enemies       les pièces ennemies
     * @param couleur       la couleur alliée
     * @param cR            les coordonnées du roi
     * @return              une piece peut etre posée dans le chemin
     */
    private static boolean isBlocked(ArrayList<IPiece> enemies, String couleur, Coord cR){
        ArrayList<IPiece> Allys = getPieceFromColor(couleur);  //liste de piece allié
        for(IPiece p : Allys){
            if(!isPiecePinned(p, cR, couleur.equals("BLANC") ? "NOIR" : "BLANC") &&
                    !allMovesDefendingCheck(p, cR, enemies).isEmpty())
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
                !Coord.isStraightPath(cS, cR)     ||
                !voieLibre(p, cR))
            return false;

        return !(getPningPiece(cS, cR, couleur) == null);
    }


    /** verifie avec les coordonnée du roi si celui-ci est attaquée par les pieces adverses.
     *
     * @param cR            les coordonnées du roi
     * @param pieces        les pièces présentes sur l'échiquier
     * @return              si une pièce(s) menace(s) le roi
     */
    public static boolean checkIfCheck(Coord cR, ArrayList<IPiece> pieces){
        return !getAllCheckingPiece(cR, pieces).isEmpty();
    }

    /** Retourne si le roi est mit en echec par une pièce ou non
     *
     * @param c         les coordonnées du roi
     * @param p         la pièce en question
     * @return          si le roi est en echec
     */
    public static boolean isCheck(Coord c, IPiece p){
        // on ne test pas si l'arrivé est valide car on test avec les coordonnées du roi.
        return p.estPossible(c.getX(), c.getY()) && voieLibre(p, c);
    }

    public static boolean isStaleMate(ArrayList<IPiece> ennemies, ArrayList<IPiece> allys, String couleur, Coord cR){
        ArrayList<Coord> allPossibleMoves = new ArrayList<>();
        if(checkIfCheck(cR, ennemies))
            return false;

        for(IPiece p: allys)
            allPossibleMoves.addAll(getAllMoves(p, cR, couleur, ennemies));

        return allPossibleMoves.isEmpty();
    }

}
