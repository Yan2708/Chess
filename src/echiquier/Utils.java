package echiquier;

import com.sun.org.apache.xalan.internal.xsltc.dom.ArrayNodeListIterator;

import static echiquier.Echiquier.*;
import static echiquier.Regle.*;

import java.util.ArrayList;

import static echiquier.Echiquier.horsLimite;

public class Utils {

    /** Renvoie une liste de coordonnées allant d'une position donnée à la limite de l'echiquier.
     *
     * @param cS            coord d'arrivé
     * @param cP            mouvement primaire
     * @return              les coordonnées
     */
    public static ArrayList<Coord> getPathToBorder(Coord cS, Coord cP) {
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

    /** Renvoie une liste de coordonnées de case entre la piece qui met le roi en echec et le roi.
     *
     * @param cS        la coordonnée de départ
     * @param cF        la coordonnée d'arrivée
     * @return          les coordonnées de la demi-droite entre la piece qui le met en echec et le roi
     */
    public static ArrayList<Coord> getPath(Coord cS, Coord cF){
        ArrayList<Coord> tile = new ArrayList<>();
        Coord cClone = new Coord(cS.getX(), cS.getY());
        if(!isStraightPath(cS, cF))        //  si le chemin n'est pas verticale ou horizontale
            return tile;            //  il y a pas de chemin de case attaqué.

        Coord pM = getPrimaryMove(cS, cF);

        while(!cClone.equals(cF)) {
            tile.add(new Coord(cClone.getX(), cClone.getY()));
            cClone.Add(pM);
        }

        tile.add(cClone);   //pour avoir la case d'arriver

        return tile;
    }

    /** Renvoie un liste de coordonnées definissant le chemin entre un piece attaquant
     * le roi et la limite de l'echiquier en passant par le roi.
     *
     * @param c        les coordonnées du roi
     * @param pieces    les  pieces qui mettent le roi en echec
     * @return          les cases passant par les pieces attaquantes et le roi jusqu'a la limite de l'echiquier
     */
    public static ArrayList<Coord> getAllAttackedPath(Coord c, ArrayList<IPiece> pieces){
        ArrayList<Coord> coords = new ArrayList<>();
        for(IPiece p : pieces){
            Coord cS = new Coord(p.getLigne(), p.getColonne());

            if(!isStraightPath(cS, c))
                continue;

            // en partant de la piece attaquante on prend toutes les cases jusqu'aux limites
            // en passant pas le roi.
            Coord cP = getPrimaryMove(cS, c);
            coords.addAll(getPathToBorder(cS, cP));
        }
        return coords;
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


    /** Retourne toutes les coordonnées possibles pour une pièce
     *
     * @param p         la pièce
     * @param cR        la position du roi
     * @param couleur   la couleur de l'ennemi
     * @param pieces    les pièces ennemies
     * @return          les coordonnées de déplacement possibles d'une pièce
     */
    public static ArrayList<Coord> getAllMoves(IPiece p, Coord cR, String couleur, ArrayList<IPiece> pieces){
        Coord cS = new Coord(p.getLigne(), p.getColonne());
        ArrayList<Coord> allMoves = new ArrayList<>();

        if(isPiecePinned(p, cR, couleur)) {
            return allMovesFromPin(p, cS, cR, couleur);
        }

        for (int x = 0; x < LIGNE; x++) {
            for (int y = 0; y < COLONNE; y++) {

                if(x == cS.getX() && y == cS.getY()) continue;

                Coord c = new Coord(x, y);
                if(isCoupValid(c, p)) {

                    if(p.getPieceType().equals("ROI") && checkIfCheck(c, pieces))
                        continue;

                    allMoves.add(c);
                }
            }
        }

        if(p.getPieceType().equals("ROI"))
            allMoves.removeIf(getAllAttackedPath(cR, pieces)::contains);

        return allMoves;
    }

    /** Retourne les déplacements possibles d'une pièce pour défendre son roi
     *
     * @param p                 la pièce
     * @param cR                la position du roi
     * @param checkingPiece     les pièces ennemies
     * @return                  les coordonnées possibles de la pièce pour défendre son roi
     */
    public static ArrayList<Coord> allMovesDefendingCheck(IPiece p, Coord cR, ArrayList<IPiece> checkingPiece){
        ArrayList<Coord> moves = new ArrayList<>();

        if(checkingPiece.size() != 1 || p.getPieceType().equals("ROI"))
            return moves;

        IPiece chkP = checkingPiece.get(0);
        ArrayList<Coord> path = getPath(cR, new Coord(chkP.getLigne(), chkP.getColonne()));

        for(Coord c : path)
            if(isCoupValid(c, p))
                moves.add(c);
        return moves;
    }

    /** Retourne les coordonnées de déplacement possibles pour une pièce clouée
     *
     * @param p         la pièce clouée
     * @param cS        les coordonnées de la pièce
     * @param cR        les coordonnées du roi
     * @param couleur   la couleur des ennemis
     * @return          la liste des coordonnées
     */
    private static ArrayList<Coord> allMovesFromPin(IPiece p, Coord cS, Coord cR, String couleur){
        ArrayList<Coord> moves = new ArrayList<>();
        IPiece pningPiece = getPningPiece(cS, cR, couleur);

        if(getPningPiece(cS, cR, couleur) == null)
            return moves;

        assert pningPiece != null;
        ArrayList<Coord> path = getPath(cS, new Coord(pningPiece.getLigne(), pningPiece.getColonne()));

        for(Coord c : path)
            if(isCoupValid(c, p))
                moves.add(c);
        return moves;
    }

    /** Retourne la pièce susceptible d'attaquer le roi si la pièce clouée n'est plus là
     *
     * @param cS        la position de la pièce
     * @param cR        la position du roi
     * @param couleur   la couleur ennemie
     * @return          la pièce qui cloue ou rien
     */
    public static IPiece getPningPiece(Coord cS, Coord cR, String couleur){
        ArrayList<Coord> pathToBorder = getPathToBorder(cS, getPrimaryMove(cS, cR).inverse());
        IPiece piece;
        for(Coord c : pathToBorder){
            piece = getPiece(c);

            if(piece.getCouleur().equals(couleur) &&        // si dans ce chemin il y a une piece
                    isCoupValid(cS, piece) &&                   // suceptible d'attaquer la piece qui defend
                    piece.estPossible(cR.getX(), cR.getY()))    // et le roi juste apres.

                return piece;
        }
        return null;
    }

}
