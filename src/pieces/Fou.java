package pieces;

import static pieces.PieceType.FOU;

public class Fou  extends Piece{

    private static final PieceType type = FOU;

    public Fou(int ligne, int colonne, Couleur c) {
        super(ligne, colonne,c, type);
    }

    @Override
    public boolean estPossible(int ligne, int colonne) {
        return false;
    }

    @Override
    public String getSymbole() {
        return "F";
    }
}
