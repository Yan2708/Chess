package pieces;

import static java.lang.Math.abs;
import static pieces.PieceType.DAME;

public class Dame  extends Piece{
    /** Constructeur d'une dame
     *
     * @see Piece#Piece(int, int, Couleur, PieceType)
     */
    public Dame(int ligne, int colonne, Couleur c) {
        super(ligne, colonne,c, DAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean estPossible(int ligne, int colonne) {
        int varX = abs(this.getLigne()-ligne);
        int varY = abs(this.getColonne()-colonne);
        return (varY >= 1 && varY==varX) || (varX > 0 && varY == 0) || (varY > 0 && varX == 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbole() {
        return "D" ;
    }
}
