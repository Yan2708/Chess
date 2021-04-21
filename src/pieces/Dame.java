package pieces;

import static pieces.PieceType.DAME;

public class Dame  extends Piece{

    public Dame(int ligne, int colonne, Couleur c) {
        super(ligne, colonne,c, DAME);
    }

    @Override
    public boolean estPossible(int ligne, int colonne) {
        return false;
    }

    @Override
    public String getSymbole() {
        return "D" ;
    }
}
