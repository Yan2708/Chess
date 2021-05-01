package pieces;

import static java.lang.Math.abs;
import static pieces.PieceType.PION;
import static pieces.Couleur.*;


public class Pion extends Piece{

    private static final int START1 = 1, START2 = 6;

    private int forward;

    /**
     * Constructeur d'un pion
     * @see Piece#Piece(int, int, Couleur, PieceType)
     */
    public Pion(int ligne, int colonne, Couleur c) {
        super(ligne, colonne,c, PION);
        this.forward = c == BLANC ? -1 : 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void newPos(int ligne, int colonne){
        setPos(ligne,colonne);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean estPossible(int ligne, int colonne) {
        int varX = ligne - this.getLigne();
        int varY = abs(this.getColonne()-colonne);
        return (varY == 1 || varY == 0) && varX == forward ||
                (isFirstMove() && varX == (2*forward) && varY == 0);
    }

    private boolean isFirstMove(){
        int x = this.getLigne();
        return x == START1 || x == START2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbole() {
        return "P";
    }

}
