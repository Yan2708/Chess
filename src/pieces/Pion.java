package pieces;

import static java.lang.Math.abs;

public class Pion extends Piece{

    private boolean aBouge;

    public boolean isABouge() {
        return aBouge;
    }

    public void setABouge(boolean aBouge) {
        this.aBouge = aBouge;
    }

    public Pion(int ligne, int colonne, Couleur c) {
        super(ligne, colonne,c);
        setType(PieceType.PION);
        aBouge = false;
    }

    @Override
    public boolean estPossible(int ligne, int colonne) {
        int varX = abs(this.getLigne()-ligne);
        int varY = abs(this.getColonne()-colonne);
        if ((aBouge && varX > 1) || (varX == 0 && varY == 0)){
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
