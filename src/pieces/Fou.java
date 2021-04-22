package pieces;

import static java.lang.Math.abs;
import static pieces.PieceType.FOU;

public class Fou  extends Piece{

    public Fou(int ligne, int colonne, Couleur c) {
        super(ligne, colonne,c, FOU);
    }

    @Override
    public boolean estPossible(int ligne, int colonne) {
        int varX = abs(this.getLigne()-ligne);
        int varY = abs(this.getColonne()-colonne);
        return (varY >= 1 && varX==varY);
    }

    @Override
    public String getSymbole() {
        return "F";
    }
}
