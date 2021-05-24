package pieces;


import coordonnee.Coord;
import echiquier.*;

import java.util.LinkedList;
import java.util.List;

import static echiquier.Couleur.BLANC;

/** {@inheritDoc} */
public abstract class Piece implements IPiece
{
    /** Position de la pièce */
    protected Coord coord;

    /** Couleur de la pièce */
    private final Couleur couleur;

    /** Constructeur d'une pièce */
    public Piece(Couleur c, Coord coord) {
        newPos(coord);
        couleur=c;
    }

    /** {@inheritDoc} */
    @Override
    public void newPos(Coord coord){
        this.coord = coord;
    }

    /** {@inheritDoc} */
    @Override
    public final Couleur getCouleur(){
        return this.couleur;
    }

    /** {@inheritDoc} */
    @Override
    public abstract boolean estPossible(Coord c);

    /** Renvoie le symbole de la pièce */
    public abstract String getSymbole();

    /** {@inheritDoc} */
    @Override
    public final String dessiner(){
        return (couleur == BLANC) ? getSymbole() : getSymbole().toLowerCase() ;
    }

    /** {@inheritDoc} */
    @Override
    public final Coord getCoord() {
        return coord;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isCoupValid(Coord cF, Echiquier e){
        return this.estPossible(cF) && Regle.voieLibre(this, cF, e) && Regle.isFinishValid(this, cF, e);
    }

    //refaire pour le roi,
    public boolean estSensible(){
        return false;
    }

    //refaire pour VIDE
    public boolean estVide(){
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public IPiece autoPromote(){ return this;}

    /** {@inheritDoc} */
    public boolean peutAttaquer(Coord c, Echiquier e){
        return estPossible(c) && Regle.voieLibre(this, c, e);
    }

    /** {@inheritDoc} */
    @Override
    public LinkedList<Coord> getAllMoves(Coord sC, List<IPiece> ennemies, Echiquier e){
        if(isPinned(sC, e)){
            return Utils.allMovesFromPin(this, sC, couleur, e);
        }

        if(Regle.isAttacked(sC, ennemies, e))
            return Utils.allMovesDefendingCheck(this, sC, ennemies, e);

        return e.allClassicMoves(this);
    }

    /**
     * Retourne si la pièce est clouée
     * @param sC la coordonnée sensible de l'allié
     * @param e l'echiquier
     * @return si la pièce est clouée ou non
     */
    protected boolean isPinned(Coord sC, Echiquier e){
        if(!Coord.isStraightPath(coord, sC)||
            !Regle.voieLibre(this, sC, e))
            return false;

        return !(Utils.getPningPiece(coord, sC, couleur, e) == null);
    }

    public boolean canHoldEndGame(){
        return true;
    }
}
