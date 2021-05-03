package echiquier;


import static echiquier.Echiquier.*;
import static java.lang.Math.abs;

import java.util.ArrayList;


public class Regle {

    /**
     * Renvoie si le mouvement d'une piece vers une coordonnée est valide.
     * un coup est valide si : le mouvement est possible pour la piece
     * le chemin entre la piece et l'arrivé n'a pas d'obstacle
     * et que la case d'arrivé peut accueillir la piece.
     * @param c la coordonnée d'arrivée
     * @param p la coordonnée de la piece
     * @return si le déplacement est possible
     */
    public static boolean isCoupValid(Coord c, IPiece p){
        String color = getPiece(c).getCouleur();
        return p.estPossible(c.getX(),c.getY()) && voieLibre(p, c) && isFinishValid(p, c) &&
                (!p.getPieceType().equals("PION") ||
                        (!priseEnDiag(p, c) || !color.equals(p.getCouleur()) && !color.equals("VIDE")));       // de couleur adverse.
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
     * @param ennemies toutes les pieces de l'ennemi
     * @return si le roi est en échec et mat
     */
    public static boolean checkForMate(String couleur, Coord cR, ArrayList<IPiece> ennemies){
        if(!isAttacked(cR, ennemies)) return false;  //le roi n'est pas en echec

        ArrayList<IPiece> ckgPieces = Utils.getAllAttackingPiece(cR, ennemies);
        //si une piece met le roi en echec alors il est possible de defendre avec une autre piece.
        if(!(ckgPieces.size() > 1) &&
                isBlocked(ennemies, couleur, cR))
            return false;

        //verifie si chaque mouvement possible du roi est sans danger
        ArrayList<Coord> kingsMoves = Utils.getAllMoves(getPiece(cR), cR, couleur, ennemies);
        for(Coord c : kingsMoves)
            if(!isAttacked(c, ennemies))
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
     * @see Utils#getAllMoves(IPiece, Coord, String, ArrayList) 
     */
    private static boolean isBlocked(ArrayList<IPiece> enemies, String couleur, Coord cR){
        String oppsColor = couleur.equals("BLANC") ? "NOIR" : "BLANC";  //la couleur opposé
        ArrayList<IPiece> Allys = getPieceFromColor(couleur);  //liste de piece allié
        for(IPiece p : Allys){
            if(!isPiecePinned(p, cR, oppsColor) && //la piece de doit pas etre cloué
                    !Utils.getAllMoves(p, cR, oppsColor, enemies).isEmpty())
                return true;                                                        
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
        Coord cS = Coord.coordFromPiece(p);

        if(p.getPieceType().equals("ROI")       ||      //un piece cloué ne peut pas etre le roi
                !Coord.isStraightPath(cS, cR)   ||
                !voieLibre(p, cR))
            return false;

        return !(Utils.getPningPiece(cS, cR, couleur) == null);   //s'il n'y a pas de piece clouante
    }

    /**
     * verifie avec les coordonnée du roi si celui-ci est attaquée par les pieces adverses.
     * @param cR les coordonnées du roi
     * @param pieces les pièces présentes sur l'échiquier
     * @return si une pièce(s) menace(s) le roi
     * @see Utils#getAllAttackingPiece(Coord, ArrayList)
     */
    public static boolean isAttacked(Coord cR, ArrayList<IPiece> pieces){
        return !Utils.getAllAttackingPiece(cR, pieces).isEmpty();
    }

    /**
     * Retourne si une coordonnée peut etre attaqué par une pièce ou non
     * @param c coordonnée d'arrivée
     * @param p la pièce attaquante
     * @return la case peut etre attaquée
     */
    public static boolean canBeAttacked(Coord c, IPiece p){
        // l'arrivé n'est pas testé car ce n'est pas le but de cette methode.
        // /!\ ne pas confondre avec isCoupValid ou l'arrivé est testé
        return p.estPossible(c.getX(), c.getY()) && voieLibre(p, c) &&
                (!p.getPieceType().equals("PION") || priseEnDiag(p, c));
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
            allPossibleMoves.addAll(Utils.getAllMoves(p, cR, couleur, ennemies));

        return allPossibleMoves.isEmpty();
    }

    /**
     * verifie si le materiel present sur l'echiquier est suffisant pour continuer une partie.
     * https://www.chess.com/terms/draw-chess#:~:text=A%20draw%20occurs%20in%20chess,player%20wins%20half%20a%20point.
     * @param ennemies      les pieces ennemies
     * @param allys         les pieces alliées
     * @return              si le matériel des joueurs est insuffisant pour mettre l'un l'autre en echec et mat
     */
    public static boolean impossibleMat(ArrayList<IPiece> ennemies, ArrayList<IPiece> allys){
        ArrayList<IPiece> allPieces = new ArrayList<>(ennemies);
        allPieces.addAll(allys);
        allPieces.removeIf(p -> p.getPieceType().equals("ROI"));

        //"King and bishop vs. king and bishop of the same color as the opponent's bishop"
        if(allPieces.size()==2){
            IPiece p1 = allPieces.get(0);
            IPiece p2 = allPieces.get(1);
            if(p1.getPieceType().equals("FOU")
                    && p2.getPieceType().equals("FOU")
                    && p1.getCouleur().equals(p2.getCouleur())){
                int diffXYp1 = abs(p1.getLigne() - p1.getColonne());
                int diffXYp2 = abs(p2.getLigne() - p2.getColonne());
                return !(diffXYp1 % 2 == diffXYp2 % 2);
            }
            return false;
        }

        if(allPieces.size()==1) {
            switch (allPieces.get(0).getPieceType()){
                case "FOU" :                            //"King and bishop vs. king"
                case "CAVALIER":                        //"King and knight vs. king"
                    return true;
                default:return false;
            }
        }
        return allPieces.isEmpty(); //"King vs. king"
    }

}
