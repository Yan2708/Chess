package pieces;

import echequier.IPiece;

import java.util.ArrayList;

public abstract class Piece implements IPiece
{
    private int colonne, ligne;
    private Couleur couleur;
    private PieceType type;



    public abstract boolean estPossible(int colonne, int ligne);

    public abstract String getSymbole();

    public String dessiner(){
        return (couleur == Couleur.BLANC) ? getSymbole() : getSymbole().toLowerCase() ;
    }

    public int getColonne(){
        return colonne;
    }

    public int getLigne(){
        return ligne;
    }

    public PieceType getPieceType(){return type;}

    public void setType(PieceType p) {
        type=p;
    }

//    public boolean estSensible(){
//        return false;
//    }

}
