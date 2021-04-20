package pieces;

public class Vide extends Piece{

    public Vide(int ligne, int colonne, Couleur c) {
        super(colonne, ligne, c);
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

