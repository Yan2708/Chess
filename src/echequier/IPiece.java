package echequier;


public interface IPiece {

    boolean estPossible(int ligne, int colonne);

    /** Getter de la colonne de la pièce
     *
     * @return          la colonne de la pièce
     */
    int getColonne();
    int getLigne();
    String dessiner();
    String getCouleur();
    String getPieceType();
    void newPos(int ligne, int colonne);
    IPiece changeToVide(int x,int y);
}
