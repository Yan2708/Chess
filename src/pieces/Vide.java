package pieces;

import static pieces.PieceType.VIDE;

public class Vide extends Piece{

    /**
     * Constructeur d'une pièce vide
     * @see Piece#Piece(int, int, Couleur, PieceType)
     */
    public Vide(int ligne, int colonne, Couleur c) {
        super(ligne, colonne, c, VIDE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean estPossible(int ligne, int colonne) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbole() {
        return " ";
    }
}

