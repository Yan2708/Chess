package pieces;

import coordonnee.Coord;
import echiquier.Couleur;
import echiquier.IPiece;
import echiquier.Utils;

import java.util.ArrayList;

import static echiquier.Regle.isAttacked;
import static java.lang.Math.abs;
import static pieces.PieceType.*;

public class Roi extends Piece{

    /**
     * Constructeur d'un roi
     * @see Piece#Piece(Couleur, PieceType, coordonnee.Coord)
     */
    public Roi(Coord coord, Couleur c) {
        super(c, ROI, coord);
    }

    /**
     * {@inheritDoc}
     * @param c
     */
    @Override
    public boolean estPossible(Coord c) {
        int varX = abs(coord.x-c.x);
        int varY = abs(coord.y-c.y);
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

    @Override
    public boolean estSensible() {
        return true;
    }

    @Override
    public ArrayList<Coord> getAllMoves(Coord cR, ArrayList<IPiece> ennemies) {
        ArrayList<Coord> moves = Utils.allClassicMoves(this);
        ArrayList<IPiece> checkingPieces = Utils.getAllAttackingPiece(coord, ennemies);
        moves.removeIf(c -> isAttacked(c, ennemies));
        moves.removeIf(c -> isAttackedPath(c, checkingPieces));
        return moves;
    }

    /**
     * Verifie si une coordonnée est dans le chemin d'une piece qui attaque le roi
     * @param attackingPiece les pieces ennemies
     * @return liste de cases possiblement attaqué protégé par le roi (car en travers du chemin)
     */
    private boolean isAttackedPath(Coord c, ArrayList<IPiece> attackingPiece){
        for(IPiece p : attackingPiece) {
            Coord cS = p.getCoord().clone();
            if(!Coord.isStraightPath(coord, cS))
                continue;

            Coord cP = Coord.getPrimaryMove(cS, coord);
            cS.add(cP); //on ajoute une premiere fois
            if(Utils.getPathToBorder(cS, cP).contains(c))
                return true;
        }
        return false;
    }

    @Override
    public boolean isPinned(Coord cR) {
        return false;
    }

    @Override
    public boolean canHoldEndGame() {
        return false;
    }
}
