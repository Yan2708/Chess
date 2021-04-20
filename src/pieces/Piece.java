package pieces;

import echequier.IPiece;

import java.util.ArrayList;

public abstract class Piece implements IPiece
{
    private int colonne, ligne;
    private Couleur couleur;
    private PieceType type;



    public Piece(int ligne, int colonne, Couleur c) {
        this.ligne = ligne;
        this.colonne = colonne;
        couleur=c;
    }

    public Couleur getCouleur(){
        return this.couleur;
    }

    public abstract boolean estPossible(int ligne, int colonne);

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
