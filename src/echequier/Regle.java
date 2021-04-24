package echequier;


import coordonnees.Coord;

import java.util.ArrayList;

public class Regle {

    @SuppressWarnings("serial")
    private static class RoiIntrouvableException extends Exception{ }
    private static class pasCool extends Exception{ }
    private static class CoupNonValide extends Exception{ }
    private static class PasMat extends Exception{ }

    public void joueurChecker(Echiquier e, String couleur) throws RoiIntrouvableException, pasCool {
        Coord cR;
        try {
            cR = locateKing(e, couleur);
        } catch (RoiIntrouvableException ex) {
            throw new RoiIntrouvableException();
        }
        ArrayList<IPiece> pieces = getPieceFromColor(e, couleur);
        for(IPiece p : pieces){
            if(check(e,cR,p)) {
                IPiece checker = checker(e, cR, p);
                if(checkMate(e, couleur, cR, checker))
                    partieFinie();
            }
            break;

        }
    }

    public boolean checkMate(Echiquier e, String couleur,Coord cR, IPiece checker){
        for (IPiece piece : getPieceFromColor(e,couleur)) {
            ArrayList<Coord> coords = deplacementsPossibles(piece, Echiquier.getLIGNE(),Echiquier.getLIGNE());
            for (Coord coord: coords) {
                try{

                    e.deplacer(piece.getLigne(),piece.getColonne(),coord.getX(),coord.getY());
                    if(check(e,cR,piece) || che)
                       throw new CoupNonValide();
                    else
                        throw new PasMat();
                } catch (CoupNonValide CNV) {

                } catch (PasMat pasMat){
                    return false;
                }
            }
        }
        return true;
    }

    public ArrayList<Coord> deplacementsPossibles(IPiece p,int lignes, int colonnes){
        ArrayList<Coord> a = new ArrayList<Coord>();
        for(int x=0;x<lignes;x++)
            for(int y=0;y<colonnes;y++)
                if(p.estPossible(x,y) && !Echiquier.horsLimite(new Coord(x,y)))
                    a.add(new Coord(x,y));

        return a;
    }

    public IPiece checker(Echiquier e, Coord cR, IPiece p) throws pasCool {
            if(p.estPossible(cR.getX(), cR.getY()) && voieLibre(e, p, cR))
                return p;
        throw new pasCool();
    }

    public static boolean check(Echiquier e, Coord cR, IPiece p){
        return p.estPossible(cR.getX(), cR.getY()) && voieLibre(e, p, cR);
    }
    
    private static ArrayList<IPiece> getPieceFromColor(Echiquier e, String couleur){
        couleur = (couleur.equals("BLANC")) ? "NOIR" : "BLANC";
        ArrayList<IPiece> pieces = new ArrayList<>();
        for(IPiece[] ligne : e.getEchiquier())
            for(IPiece p: ligne){
                if(couleur.equals(p.getCouleur()))
                    pieces.add(p);
            }
        return pieces;
    }

    
    private static Coord locateKing(Echiquier e , String couleur) throws RoiIntrouvableException {
        for(IPiece[] ligne : e.getEchiquier())
            for(IPiece p : ligne)
              if(p.getPieceType().equals("ROI") && p.getCouleur().equals(couleur))
                    return new Coord(p.getLigne(),p.getColonne());
        throw new RoiIntrouvableException();
    }

    /** Vérifie si le chemin entre 2 points n'a pas d'obstacle (pas l'arrivée)
     *
     * @param c                   coordonnées de la case d'arrivé
     * @param p                   la pièce à deplacer
     * */
    private static boolean voieLibre(Echiquier e,IPiece p, Coord c){
        Coord cS = new Coord(p.getLigne(), p.getColonne());
        double longueur = getLongueur(cS, c);

        if(longueur % 0 == 0)
            //  si la valeur est decimale le mouvement n'est pas diagonale, horizontale ou verticale
            //  alors on ne verifie pas si la voie est libre entre les deux pieces.
            return true;

        Coord pM = getPrimaryMove(cS, c);
        // on applique le mouvement primaire une premiere fois
        // pour ne pas tester sur la case de la piece
        cS.Add(pM);

        for (int i = 0; i < longueur - 1; i++, cS.Add(pM)) {
            if(!e.estVide(cS))
                return false;
        }
        return true;
    }

    /** Renvoie la longueur entre 2 points.
     *  /!\ que pour les diagonales et droites dont la longueur est toujours un entier naturel.
     *
     * @param cS                    coordonnées de la case de depart
     * @param cF                    coordonnées de la case d'arrivé
     * @return                      la longueur entre les 2 points
     * */
    private static double getLongueur(Coord cS, Coord cF){
        return (int)Math.sqrt((Math.pow(cF.getX() - cS.getX(), 2) + Math.pow(cF.getY() - cS.getY(), 2)));
    }

    /** Renvoie le mouvement primaire entre deux points.
     * EST(1,0),NORD_EST(1,1),NORD(0,1),NORD_OUEST(-1,1),OUEST (-1,0),SUD_OUEST(-1,-1),SUD(0,-1),SUD_EST(1,-1)
     *
     * @param cS                    coordonnées de la case de depart
     * @param cF                    coordonnées de la case d'arrivé
     * @return                      le mouvement primaire dans un tableau
     * */
    private static Coord getPrimaryMove(Coord cS, Coord cF){
        int x = (cF.getX() - cS.getX());
        int y = (cF.getY() - cS.getY());
        if(Math.abs(x)>1)
            x=x/Math.abs(x);
        if(Math.abs(y)>1)
            y=y/Math.abs(y);
        return new Coord(x,y);
    }

    /** Vérifie si la l'arrivé d'une piece sur une case est valide.
     *
     * @param c                 coordonnées de la case
     * @param p                 la pièce à deplacer
     * */
    private static boolean arriveNonValide(Echiquier e,IPiece p, Coord c){
        IPiece pA = e.getPiece(c);
        return ((pA.getCouleur().equals(p.getCouleur())) || (pA.getPieceType().equals("ROI")));
    }

}
