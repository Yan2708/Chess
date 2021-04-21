package pieces;

import static java.lang.Math.abs;
import static pieces.PieceType.ROI;

public class Roi extends Piece{

    public Roi(int ligne, int colonne, Couleur c) {
        super(ligne, colonne,c, ROI);
    }

    @Override
    public boolean estPossible(int ligne, int colonne) {
        int varX = abs(this.getLigne()-ligne);
        int varY = abs(this.getColonne()-colonne);
        if(varX == 0 && varY == 0 )
            return false;
        return (varX == 1 || varX == 0) && (varY == 0 || varY == 1);
    }



    @Override
    public String getSymbole() {
        return "R";
    }
}
