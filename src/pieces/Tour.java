package pieces;

import static java.lang.Math.abs;
import static pieces.PieceType.TOUR;


public class Tour extends Piece{

    public Tour(int ligne, int colonne, Couleur c) {
        super(ligne, colonne,c, TOUR);
    }
    @Override
    public boolean estPossible(int ligne, int colonne) {
        int varX = abs(this.getLigne()-ligne);
        int varY = abs(this.getColonne()-colonne);
        if(varX == 0 && varY == 0 )
            return false;
        return (varX > 0 && varY == 0) || (varY > 0 && varX == 0);
    }

    @Override
    public String getSymbole() {
        return "T";
    }
}
