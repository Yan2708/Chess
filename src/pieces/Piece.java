package pieces;


import coordonnees.Coord;
import echequier.IPiece;

public abstract class Piece implements IPiece
{
    //Position de la pièce
    private Coord coordonnees;
    //Couleur de la pièce
    private final Couleur couleur;
    //Le type de pièce
    private final PieceType type;

    /** Constructeur d'une pièce
     *
     * @param ligne          la position en x de la pièce
     * @param colonne        la position en y de la pièce
     * @param c              la couleur de la pièce
     * @param pT             le type de pièce
     */
    public Piece(int ligne, int colonne, Couleur c, PieceType pT) {
        newPos(ligne,colonne);
        couleur=c;
        type = pT;
    }

    /**
     * {@inheritDoc}
     */
    public void newPos(int ligne, int colonne){
        setPos(ligne,colonne);
    }

    /** Met les nouvelles coordonnées de la pièce
     *
     * @param ligne         la nouvelle coordonnée en x
     * @param colonne       la nouvelle coordonnée en y
     */
    public void setPos(int ligne, int colonne){
        coordonnees=new Coord(ligne,colonne);
    }

    /**
     * {@inheritDoc}
     */
    public IPiece changeToVide(int x,int y){
        return new Vide(x, y, Couleur.VIDE);
    }

    /**
     * {@inheritDoc}
     */
    public String getCouleur(){
        return this.couleur.toString();
    }

    /**
     * {@inheritDoc}
     */
    public abstract boolean estPossible(int ligne, int colonne);

    /** Renvoie le symbole de la pièce
     *
     * @return          le symbole de la pièce
     */
    public abstract String getSymbole();

    /**
     * {@inheritDoc}
     */
    public String dessiner(){
        return (couleur == Couleur.BLANC) ? getSymbole() : getSymbole().toLowerCase() ;
    }

    /**
     * {@inheritDoc}
     */
    public int getLigne(){
        return coordonnees.getX();
    }

    /**
     * {@inheritDoc}
     */
    public int getColonne(){
        return coordonnees.getY();
    }

    /**
     * {@inheritDoc}
     */
    public String getPieceType(){return type.toString();}



}
