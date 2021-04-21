package pieces;

import static pieces.PieceType.VIDE;

public class Vide extends Piece{

    private static final PieceType type = VIDE;

    public Vide(int ligne, int colonne, Couleur c) {
        super(colonne, ligne, c, type);
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

