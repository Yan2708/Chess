package echiquier;

import static echiquier.Echiquier.*;
import java.util.ArrayList;


public class Regle {


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

    private static boolean piecesInTheWay(ArrayList<Coord> tile, String couleur, Coord cR, IPiece p){
        ArrayList<IPiece> pieces = getPieceFromColor(couleur);  //liste de piece allié
        String couleurOppose = (couleur.equals("BLANC")) ? "NOIR" : "BLANC";
        Coord cCheck = new Coord(p.getLigne(),p.getColonne());  //position de la piece qui met le roi en echec

        for(IPiece pc : pieces){
            Coord cP = new Coord(pc.getLigne(), pc.getColonne());

            if(isCoupValid(cCheck, pc)) //si une piece allié peut prendre la piece attaquante
                return true;

            // si une piece allié peut se positionner entre le roi et la piece attaquante
            for(Coord cF : tile)
                if(!(pc.getPieceType().equals("ROI")) &&
                        isCoupValid(cF, pc) &&
                        !isPiecePinned(pc, cR, couleurOppose))      // la piece ne doit pas etre cloué
                    return true;
        }
        return false;
    }

    private static boolean isPiecePinned(IPiece p, Coord cF, String couleur){
        Coord cS = new Coord(p.getLigne(), p.getColonne());

        if(isStraightPath(getLongueur(cS, cR))) // si le chemin entre la piece est le roi n'est pas verticale
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

    private static ArrayList<Coord> getAllCheckingTiles(Coord cF, ArrayList<IPiece> pieces){
        ArrayList<Coord> checkingTile = new ArrayList<>();

        // on recupere toutes les cases dans le cas ou plusieurs pieces attaquent le roi
        for(IPiece p : pieces){
            checkingTile.addAll(getCheckingTile(new Coord(p.getLigne(), p.getColonne()), cR));
        }
        return checkingTile;
    }

    private static ArrayList<Coord> getCheckingTile(Coord cS, Coord cF){
        ArrayList<Coord> checkingTile = new ArrayList<>();
        double longueur = getLongueur(cS, cF);

        if(isStraightPath(longueur))        //  si le chemin n'est pas verticale ou horizontale
            return checkingTile;            //  il y a pas de chemin de case attaqué.

        Coord pM = getPrimaryMove(cS, cF);
        // on applique le mouvement primaire une premiere fois
        // pour ne pas tester sur la case de la piece
        cS.Add(pM);

        for (int i = 0; i < longueur - 1; i++, cS.Add(pM)) {
            checkingTile.add(new Coord(cS.getX(), cS.getY()));
        }
        return checkingTile;
    }

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

    private static ArrayList<Coord> getAllCheckingFiles(Coord cR, ArrayList<IPiece> pieces){
        ArrayList<Coord> coords = new ArrayList<>();
        for(IPiece p : pieces){
            Coord cS = new Coord(p.getLigne(), p.getColonne());

            if(isStraightPath(getLongueur(cS, cR)))
                continue;

            // en partant de la piece attaquante on prend toutes les cases jusqu'aux limites
            // en passant pas le roi.
            Coord cP = getPrimaryMove(cS, cR);
            coords.addAll(getAllTile(cS, cP));
        }
        return coords;
    }


    public static boolean checkIfCheck(Coord cR, ArrayList<IPiece> pieces){
        for(IPiece p : pieces)
            // la coordonnées est attaqué par une piece adverse
            if(isCheck(cR, p))
                return true;

        return false;
    }

    private static ArrayList<IPiece> getAllCheckingPiece(Coord c, ArrayList<IPiece> pieces){
        ArrayList<IPiece> cPiece = new ArrayList<>();
        for(IPiece p : pieces)
            if(isCheck(c, p))
                cPiece.add(p);
        return cPiece;
    }

    private static boolean isCheck(Coord c, IPiece p){
        // on ne test pas si l'arrivé est valide car on test avec les coordonnées du roi.
        return p.estPossible(c.getX(), c.getY()) && voieLibre(p, c);
    }
}
