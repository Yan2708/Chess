package echequier;


public interface IPiece {

    boolean estPossible(int ligne, int colonne);

    int getColonne();
    int getLigne();
    String dessiner();
    String getCouleur();
    String getPieceType();
    void newPos(int ligne, int colonne);
}
