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
        if ((aBouge && varY > 1) || (varY == 0 && varX == 0)){
              return false;
        }
        else if (varY <= 2 && varX == 0){
            return true;
        }
        return (varY == 1) || ((varX == 1 || varX == -1) && (varY == 1));
    }

    @Override
    public String getSymbole() {
        return "P";
    }
}
