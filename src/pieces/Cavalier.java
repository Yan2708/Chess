package pieces;

import static java.lang.Math.abs;
import static pieces.PieceType.CAVALIER;

public class Cavalier extends Piece{

    /** Constructeur d'un cavalier
     *
     * @see Piece#Piece(int, int, Couleur, PieceType)
     */
    public Cavalier(int ligne, int colonne, Couleur c) {
        super(ligne, colonne,c, CAVALIER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean estPossible(int ligne, int colonne) {
        int varX = abs(this.getLigne()-ligne);
        int varY = abs(this.getColonne()-colonne);
        return (varX==1 && varY==2)||(varX==2 && varY==1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbole() {
        return "C";
    }
}
