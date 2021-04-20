package pieces;

import echequier.IPiece;

import java.util.ArrayList;

public abstract class Piece implements IPiece
{
    private int colonne, ligne;
    private Couleur couleur;

    public abstract boolean estPossible(int colonne, int ligne);

    public abstract String getSymbole();

    public boolean isWhite(){
        //pas besoin de methode isBlack
        return couleur == Couleur.BLANC;
    }

    public int getColonne(){
        return colonne;
    }

    public int getLigne(){
        return ligne;
    }

//    public boolean estSensible(){
//        return false;
//    }

}
