package mouvement;

import echequier.Echequier;
import echequier.IPiece;

public class Deplacement {



    public void voieLibre(Echequier e, int ligne, int colonne){

    }

    private int getLongueur(int xStart, int yStart, int xFinal, int yFinal){
        return (int)Math.sqrt((Math.pow(xFinal - xStart, 2) + Math.pow(yFinal - yStart, 2)));
    }

    private int getCoefDirecteur(int xStart, int yStart, int xFinal, int yFinal){
        return (yFinal - yStart) / (xFinal - xStart);
    }



//    public boolean voieLibre(Echequier e, IPiece p, int ligne, int colonne){
//        int xS = p.getLigne();
//        int yS = p.getColonne();
//        int longueur = getLongueur(p.getLigne(),p.getColonne(), ligne, colonne);
//        int coefD = getCoefDirecteur(p.getLigne(),p.getColonne(), ligne, colonne);
//        for(int i = 0; i < longueur - 1; i++, xS *= coefD, yS *= coefD){
//            if(!p.estPossible(xS,yS) || !e.estVide(xS,yS))
//                return false;
//        }
//        return true;
//    }
//
//
//    // /!\ on assume que la voie est libre
//    public boolean nonValide(Echequier e, IPiece p, int ligne, int colonne){
//        IPiece[][] plateau = e.getEchequier();
//        IPiece pA = plateau[ligne][colonne];
//        return ((pA.getCouleur() != p.getCouleur()) ||
//                (!pA.getPieceType().equals("ROI")));
//    }
}
