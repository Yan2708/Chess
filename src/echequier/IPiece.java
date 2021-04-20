package echequier;

public interface IPiece {

    public abstract boolean estPossible(int colonne, int ligne);

    String getSymbole() ;
}
