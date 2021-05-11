package pieces;


import coordonnee.Coord;
import echiquier.Couleur;
import echiquier.IPiece;
import echiquier.Utils;
import echiquier.Regle;

import java.util.ArrayList;

import static echiquier.Couleur.BLANC;

public abstract class Piece implements IPiece
{
    //Position de la pièce
    protected Coord coord;
    //Couleur de la pièce
    private final Couleur couleur;
    //Le type de pièce
    protected final PieceType type;

    /**
     * Constructeur d'une pièce
     * @param c la couleur de la pièce
     * @param pT le type de pièce
     * @param coord
     */
    public Piece(Couleur c, PieceType pT, Coord coord) {
        newPos(coord);
        couleur=c;
        type = pT;
    }

    /** {@inheritDoc} */
    public void newPos(Coord coord){
        setPos(coord);
    }

    /**
     * Met les nouvelles coordonnées de la pièce
     * @param coord la nouvelle coordonnée
     */
    public void setPos(Coord coord){
        this.coord = coord;
    }

    /** {@inheritDoc} */
    public Couleur getCouleur(){
        return this.couleur;
    }

    /** {@inheritDoc} */
    public abstract boolean estPossible(Coord c);

    /**
     * Renvoie le symbole de la pièce
     * @return le symbole de la pièce
     */
    public abstract String getSymbole();

    /** {@inheritDoc} */
    public String dessiner(){
        return (couleur == BLANC) ? getSymbole() : getSymbole().toLowerCase() ;
    }

    /** {@inheritDoc} */
    public Coord getCoord() {
        return coord;
    }

    /** {@inheritDoc} */
    public String getPieceType(){return type.toString();}

    //refaire pour le pion, FAIT
    public boolean isCoupValid(Coord cF){
        return this.estPossible(cF) && Regle.voieLibre(this, cF) && Regle.isFinishValid(this, cF);
    }

    //refaire pour le roi,
    public boolean estSensible(){
        return false;
    }

    //refaire pour VIDE
    public boolean estVide(){
        return false;
    }

    //refaire pour Pion
    public boolean isPromotable(){
        return false;
    }

    //refaire pour Pion
    public boolean peutAttaquer(Coord c){
        return estPossible(c) && Regle.voieLibre(this, c);
    }

    //refaire pour ROI
    public ArrayList<Coord> getAllMoves(Coord cR, ArrayList<IPiece> ennemies){
        if(isPinned(cR)){
            return Utils.allMovesFromPin(this, cR, couleur);
        }

        if(Regle.isAttacked(cR, ennemies))
            return Utils.allMovesDefendingCheck(this, cR, ennemies);

        return Utils.allClassicMoves(this);
    }

    //refaire pour ROi
    public boolean isPinned(Coord cR){
        if(!Coord.isStraightPath(coord, cR)||
            !Regle.voieLibre(this, cR))
            return false;

        return !(Utils.getPningPiece(coord, cR, couleur) == null);
    }

    public boolean canHoldEndGame(){
        return true;
    }

}
