package pieces;


import echequier.IPiece;

public abstract class Piece implements IPiece
{
    private int colonne, ligne;
    private Couleur couleur;
    private PieceType type;



    public Piece(int ligne, int colonne, Couleur c) {
        newPos(ligne,colonne);
        couleur=c;
    }

    public void newPos(int ligne, int colonne){
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public String getCouleur(){
        return this.couleur.toString();
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

    public String getPieceType(){return type.toString();}

    public void setType(PieceType p) {
        type=p;
    }





//    public boolean estSensible(){
//        return false;
//    }

}
