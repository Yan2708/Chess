package pieces;

public class Fou  extends Piece{
    public Fou(int ligne, int colonne, Couleur c) {
        super(ligne, colonne,c);
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
