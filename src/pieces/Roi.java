package pieces;

import static java.lang.Math.abs;
import static pieces.PieceType.ROI;

public class Roi extends Piece{

    /** Constructeur d'un roi
     *
     * @see Piece#Piece(int, int, Couleur, PieceType)
     */
    public Roi(int ligne, int colonne, Couleur c) {
        super(ligne, colonne,c, ROI);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean estPossible(int ligne, int colonne) {
        int varX = abs(this.getLigne()-ligne);
        int varY = abs(this.getColonne()-colonne);
        if(varX == 0 && varY == 0 ) // si la pièce fait du sur place
            return false;
        // le déplacement est valide seulement si le roi se déplace dans un rayon de une case
        return (varX == 1 || varX == 0) && (varY == 0 || varY == 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbole() {
        return "R";
    }
}
