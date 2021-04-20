package pieces;

public class Cavalier extends Piece{


    public Cavalier(int colonne, int ligne, Couleur c) {
        super(ligne, colonne,c);
    }

    @Override
    public boolean estPossible(int ligne, int colonne) {
        return false;
    }

    @Override
    public String getSymbole() {
        return "C";
    }
}
