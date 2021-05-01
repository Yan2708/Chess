package pieces;

import static java.lang.Math.abs;
import static pieces.PieceType.TOUR;

public class Tour extends Piece{
    /**
     * Constructeur d'une tour
     * @see Piece#Piece(int, int, Couleur, PieceType)
     */
    public Tour(int ligne, int colonne, Couleur c) {
        super(ligne, colonne,c, TOUR);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean estPossible(int ligne, int colonne) {
        int varX = abs(this.getLigne()-ligne);
        int varY = abs(this.getColonne()-colonne);
        return (varX > 0 && varY == 0) || (varY > 0 && varX == 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbole() {
        return "T";
    }
}
