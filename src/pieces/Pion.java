package pieces;

import static java.lang.Math.abs;
import static pieces.PieceType.PION;


public class Pion extends Piece{
    // En cas de premier déplacement du pion (afin de vérifier la règle de déplacement de 2 cases)
    private boolean FirstMove;

    /** Constructeur d'un pion
     *  + Set FirstMove à false
     * @see Piece#Piece(int, int, Couleur, PieceType)
     */
    public Pion(int ligne, int colonne, Couleur c) {
        super(ligne, colonne,c, PION);
        FirstMove = false;
    }

    /**
     * {@inheritDoc}
     * + Set FirstMove à true
     */
    @Override
    public void newPos(int ligne, int colonne){
        setPos(ligne,colonne);
        FirstMove = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean estPossible(int ligne, int colonne) {
        int varX = this.getLigne()-ligne;
        int varY = this.getColonne()-colonne;
        int mouvement =getCouleur().equals("NOIR")?-1:1;
        return(varY >= -1 && varY <= 1 && varX == mouvement) || (!FirstMove && varX == 2*mouvement && varY == 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbole() {
        return "P";
    }
}
