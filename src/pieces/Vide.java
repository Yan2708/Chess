package pieces;

import static pieces.PieceType.VIDE;

public class Vide extends Piece{

    public Vide(int ligne, int colonne, Couleur c) {
        super(colonne, ligne, c, VIDE);
    }
    @Override
    public boolean estPossible(int ligne, int colonne) {
        return true;
    }

    @Override
    public String getSymbole() {
        return " ";
    }
}

