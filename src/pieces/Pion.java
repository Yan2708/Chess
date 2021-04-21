package pieces;

import static java.lang.Math.abs;
import static pieces.PieceType.PION;


public class Pion extends Piece{

    private boolean FirstMove;

    public void setFirstMove(boolean aBouge) {
        this.FirstMove = aBouge;
    }

    public Pion(int ligne, int colonne, Couleur c) {
        super(ligne, colonne,c, PION);
        FirstMove = false;
    }

    @Override
    public boolean estPossible(int ligne, int colonne) {
        int varX = abs(this.getLigne()-ligne);
        int varY = abs(this.getColonne()-colonne);
        if ((FirstMove && varX > 1) || (varX == 0 && varY == 0)){
              return false;
        }
        else if (varX <= 2 && varY == 0){
            return true;
        }
        return varX == 1;
    }

    @Override
    public String getSymbole() {
        return "P";
    }
}
