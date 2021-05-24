package pieces;

import coordonnee.Coord;
import echiquier.Couleur;
import echiquier.Echiquier;
import echiquier.IPiece;
import echiquier.Regle;

import static java.lang.Math.abs;
import static echiquier.Couleur.*;

/**
 * C'est la pièce la moins mobile du jeu et pour cette raison la moins forte.
 * Depuis sa position d'origine, le pion peut avancer d'une ou deux cases.
 * Par la suite, le pion avance d'une seule case à la fois, sans changer de colonne
 * et seulement vers une case vide.
 * Le pion ne peut ni reculer, ni prendre vers l'arrière ou le côté.
 * Le pion prend en diagonale vers l’avant.
 * Le pion peut prendre en avançant d'une case en diagonale n'importe
 * quelle pièce adverse qui s'y trouverait.
 * /!\ la prise en passant n'est pas codé
 */
public class Pion extends Piece{

    /** les 2 coordonnées en x possible de départ d'un pion
     * (pour les test, lorsque la fen est personnalisé, le pion
     * n'a pas le droit d'avancer sur 2 deux s'il n'est pas a une
     * position de depart*/
    private static final int START1 = 1, START2 = 6;

    /** si la pièce a bougée */
    private boolean firstMove;

    /** la direction que le pion doit suivre */
    private int forward;

    /**
     * Constructeur d'un pion
     * @see Piece#Piece(Couleur, Coord)
     */
    public Pion(Coord coord, Couleur c) {
        super(c, coord);
        firstMove = true;
        this.forward = c == BLANC ? -1 : 1;
    }

    /** le pion a fait son premier pas*/
    @Override
    public void newPos(Coord coord) {
        super.newPos(coord);
        firstMove = false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean estPossible(Coord c) {
        int varX = c.x - coord.x;
        int varY = abs(coord.y-c.y);
        return (varY == 1 || varY == 0) && varX == forward ||
                (isFirstMove() && varX == (2*forward) && varY == 0);
    }

    /** Calcule si c'est le premier mouvement du pion
     *
     * @return si la pièce a bougée ou non
     */
    private boolean isFirstMove(){
        int x = this.coord.getX();
        return (x == START1 || x == START2) && firstMove;
    }

    /** {@inheritDoc} */
    @Override
    public String getSymbole() {
        return "P";
    }

    /**
     * le pion gère aussi la prise en diagonale
     * {@inheritDoc}
     */
    @Override
    public boolean isCoupValid(Coord cF, Echiquier e) {
        IPiece p = e.getPiece(cF);
        return super.isCoupValid(cF, e) &&
                (isPriseEnDiag(cF) ? Regle.areOpposite(this, p) && !p.estVide() : p.estVide());
    }

    /**
     * si le mouvement d'un pion resulte en un coup diagonale
     * @param c la coordonnées d'arrivée
     * @return le coup est une prise diagonale
     */
    private boolean isPriseEnDiag(Coord c){
        int diffX = abs(c.x - coord.x);
        int diffY = abs(c.y - coord.y);
        return diffX == 1 && diffY == 1;
    }

    /** le pion est promouvable */
    @Override
    public IPiece autoPromote() {
        return new Dame(this.coord, this.getCouleur());
    }

    /**
     * gère la prise en diagonale
     * {@inheritDoc}
     */
    @Override
    public boolean peutAttaquer(Coord c, Echiquier e) {
        return super.peutAttaquer(c, e) && this.isPriseEnDiag(c);
    }
}
