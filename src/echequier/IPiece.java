package echequier;

public interface IPiece {

    boolean estPossible(int colonne, int ligne);

    String dessiner();

}
