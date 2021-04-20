package pieces;

public class Roi extends Piece{
    public Roi(){
        super();
    }

    @Override
    public boolean estPossible(int colonne, int ligne) {
        return false;
    }

    @Override
    public String getSymbole() {
        return "R";
    }
}
