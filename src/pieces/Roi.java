package pieces;

import echiquier.Coord;
import echiquier.Couleur;
import echiquier.Echiquier;
import echiquier.IPiece;
import echiquier.Utils;

import java.util.LinkedList;
import java.util.List;

import static echiquier.Regle.isAttacked;
import static java.lang.Math.abs;

/**
 * Le roi (♔, ♚) est la pièce clé du jeu d'échecs.
 * Si le roi d'un joueur est menacé de capture au prochain coup de façon imparable,
 * il est dit échec et mat et le joueur concerné perd la partie.
 * Le roi se déplace d’une case dans n’importe quelle direction.
 * @author Stefan Radovanovic, Yannick Li, Zakaria Sellam
 */
public class Roi extends Piece{

    /** Constructeur d'un roi */
    public Roi(Coord coord, Couleur c) {
        super(c, coord);
    }

    /** {@inheritDoc} */
    @Override
    public boolean estPossible(Coord c) {
        int varX = abs(coord.getX()-c.getX());
        int varY = abs(coord.getY()-c.getY());
        if(varX == 0 && varY == 0 ) // si la pièce fait du sur place
            return false;
        // le déplacement est valide seulement si le roi se déplace dans un rayon de une case
        return (varX == 1 || varX == 0) && (varY == 0 || varY == 1);
    }

    /** {@inheritDoc} */
    @Override
    public String getSymbole() {
        return "R";
    }

    /** le roi est sensible au attaque */
    @Override
    public boolean estSensible() {
        return true;
    }

    /**
     * il faut retirer au mouvement du roi chaque coordonnées susceptible
     * d'etre attaqué.
     * */
    @Override
    public LinkedList<Coord> getAllMoves(Coord sC, List<IPiece> ennemies, Echiquier e) {
        LinkedList<Coord> moves = e.allClassicMoves(this);
        LinkedList<IPiece> checkingPieces = Utils.getAllAttackingPiece(coord, ennemies, e);
        moves.removeIf(c -> isAttacked(c, ennemies, e));
        moves.removeIf(c -> isAttackedPath(c, checkingPieces));
        return moves;
    }

    /**
     * Verifie si une coordonnée est dans le chemin d'une piece qui attaque le roi
     * @param attackingPiece les pieces ennemies
     * @return liste de cases possiblement attaqué protégé par le roi (car en travers du chemin)
     */
    private boolean isAttackedPath(Coord c, List<IPiece> attackingPiece){
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

    /** le roi ne peut pas etre cloué */
    @Override
    protected boolean isPinned(Coord sC, Echiquier e) {
        return false;
    }

    /** le roi ne peut pas mater */
    @Override
    public boolean canHoldEndGame() {
        return false;
    }
}
