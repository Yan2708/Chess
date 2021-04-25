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
        ArrayList<IPiece> checkingPieces = getAllCheckingPiece(cR, pieces);
        ArrayList<Coord> checkingTiles = getAllCheckingTiles(cR, pieces);
        if(!(checkingPieces.size() > 1)){
            IPiece p = checkingPieces.get(0);
            if(piecesInTheWay(checkingTiles, couleur, cR, p))
                return false;
        }

        ArrayList<Coord> kingsMoves = getKingsMoves(cR, checkingPieces);
        for(Coord c : kingsMoves)
            if(!checkIfCheck(c, pieces))
                return false;

        return true;
    }

    private static boolean piecesInTheWay(ArrayList<Coord> tile, String couleur, Coord cR, IPiece p){
        ArrayList<IPiece> pieces = getPieceFromColor(couleur);
        String couleurOppose = (couleur.equals("BLANC")) ? "NOIR" : "BLANC";
        Coord cCheck = new Coord(p.getLigne(),p.getColonne());
        for(IPiece pc : pieces){
            Coord cP = new Coord(pc.getLigne(), pc.getColonne());

            if(isCoupValid(cCheck, pc))
                return true;

            for(Coord cF : tile)
                if(!(pc.getPieceType().equals("ROI")) &&
                        isCoupValid(cF, pc) &&
                        !isPiecePinned(pc, cR, couleurOppose))
                    return true;
        }
        return false;
    }

    private static boolean isPiecePinned(IPiece p, Coord cF, String couleur){
        Coord cS = new Coord(p.getLigne(), p.getColonne());
        double longueur = getLongueur(cS, cF);
        if(longueur % 1 != 0)
            return false;

        Coord cP = getPrimaryMove(cS, cF);
        cP.inverse();
        ArrayList<Coord> allTile = getAllTile(cS, cP);
        for(Coord c : allTile){
            IPiece piece = getPiece(c);

            if(piece.getCouleur().equals(couleur) &&
                isCoupValid(cS, piece) &&
                piece.estPossible(cF.getX(), cF.getY()))

                return true;
        }
        return false;
    }

    private static ArrayList<Coord> getAllTile(Coord cS, Coord cP) {
        ArrayList<Coord> tile = new ArrayList<>();
        Coord cNew = new Coord(cS.getX(), cS.getY());
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
        for(IPiece p : pieces){
            checkingTile.addAll(getCheckingTile(new Coord(p.getLigne(), p.getColonne()), cR));
        }
        return checkingTile;
    }

    private static ArrayList<Coord> getCheckingTile(Coord cS, Coord cF){
        ArrayList<Coord> checkingTile = new ArrayList<>();
        double longueur = getLongueur(cS, cF);

        if(isStraightPath(longueur))
            return checkingTile;

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
        ArrayList<Coord> chekingFile = getAllCheckingFiles(cR, cPiece);
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

            Coord cP = getPrimaryMove(cS, cR);
            coords.addAll(getAllTile(cS, cP));
        }
        return coords;
    }


    public static boolean checkIfCheck(Coord cR, ArrayList<IPiece> pieces){
        for(IPiece p : pieces)
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
        return p.estPossible(c.getX(), c.getY()) && voieLibre(p, c);
    }

}
