package echiquier;

public interface IPiece {

    /** Pour une position donnée, calcule si le déplacement est possible pour la pièce
     *
     * @param ligne         la coordonnée en x de la position voulue
     * @param colonne       la coordonnée en y de la position voulue
     * @return              si le deplacement est possible pour la pièce ou non
     */
    boolean estPossible(int ligne, int colonne);

    /** Getter de la colonne de la pièce
     *
     * @return          la colonne de la pièce
     */
    int getColonne();

    /** Getter de la ligne de la pièce
     *
     * @return          la ligne de la pièce
     */
    int getLigne();

    /** Renvoie le bon caractère selon la couleur de la pièce (BLANC en MAJUSCULE, NOIR en minuscule)
     *
     * @return          la représentation de la pièce
     */
    String dessiner();

    /** Renvoie la couleur de la pièce
     *
     * @return          la couleur de la pièce
     */
    String getCouleur();

    /** Getter du type de la pièce
     *
     * @return          le type de la pièce
     */
    String getPieceType();

    /** Met à jour la position de la pièce selon la position donnée en paramètre
     *
     * @param ligne         la nouvelle coordonnée en x
     * @param colonne       la nouvelle coordonnée en y
     */
    void newPos(int ligne, int colonne);

    /** Renvoie une Pièce vide à la position indiquée
     *
     * @param x         la coordonnée en x
     * @param y         la coordonnée en y
     * @return          la pièce vide
     */
    IPiece changeToVide(int x,int y);
}
