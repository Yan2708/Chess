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
        int varX = abs(this.getLigne()-ligne);
        int varY = abs(this.getColonne()-colonne);
        // si le pion c'est deja déplacé et avance de plus d'une case verticalement ou si le pion ne bouge pas
        if ((FirstMove && varX > 1) || (varX == 0 && varY == 0)){
              return false;
        }
        // si le pion se déplace de moins de 2 cases verticalement et aucunement horizontalement
        return varX <= 2 && varY == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getSymbole() {
        return "P";
    }
}
