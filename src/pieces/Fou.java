package pieces;

import static java.lang.Math.abs;
import static pieces.PieceType.FOU;

public class Fou extends Piece{

    /**
     * Constructeur d'un fou
     * @see Piece#Piece(int, int, Couleur, PieceType)
     */
    public Fou(int ligne, int colonne, Couleur c) {
        super(ligne, colonne,c, FOU);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean estPossible(int ligne, int colonne) {
        int varX = abs(this.getLigne()-ligne);
        int varY = abs(this.getColonne()-colonne);
        return (varY >= 1 && varX==varY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbole() {
        return "F";
    }
}
