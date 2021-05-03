package echiquier;

import static java.lang.Math.abs;

/**
 * permet de manipuler des coordonnées (x,y)
 * notation :
 * cS = cStart ----> coordonnées de depart
 * cF = cFinal ----> coordonnées d'arrivé
 * cR = CRoi ----> coordonnées du roi
 * etc...
 */
public class Coord {

    /* X represente les lignes et y les colonnes d'un tableau 2d
     */
    private int x,y;

    /**
     * Constructeur de coordonnée
     * @param x la coordonnée en x
     * @param y la coordonnée en y
     */
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter de x
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Getter de y
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Ajoute les valeurs d'une coordonnée à une autre
     * @param c la coordonnée a ajouter (mouvement primaire)
     */
    public void Add(Coord c){
        this.x += c.x;
        this.y += c.y;
    }

    /**
     * Inverse les valeurs de la coordonnée
     * @return la coordonnée inversée
     */
    public Coord inverse(){
         this.x = -(x);
         this.y = -(y);
        return this;
    }

    /**
     * Renvoie si le chemin est droit ou non
     * @param cS coordonnées de depart
     * @param cF coordonnées d'arrivé
     * @return le chemin est horizontale, verticale ou diagonale
     */
    public static boolean isStraightPath(Coord cS, Coord cF){
        int difX = abs(cS.getX() - cF.getX());
        int difY = abs(cS.getY() - cF.getY());
        return (difX == 0 ||
                difY == 0 ||
                difX == difY);
    }


    /**
     * Renvoie le mouvement primaire entre deux points.
     * EST(1,0),NORD_EST(1,1),NORD(0,1),NORD_OUEST(-1,1),OUEST (-1,0),SUD_OUEST(-1,-1),SUD(0,-1),SUD_EST(1,-1)
     * @param cS coordonnées de la case de depart
     * @param cF coordonnées de la case d'arrivé
     * @return le mouvement primaire dans un tableau
     * */
    public static Coord getPrimaryMove(Coord cS, Coord cF){
        int x = (cF.getX() - cS.getX());
        int y = (cF.getY() - cS.getY());
        if(abs(x)>1)
            x=x/ abs(x);
        if(abs(y)>1)
            y=y/ abs(y);
        return new Coord(x,y);
    }


    /**
     * Méthode de comparaison d'une coordonnée à un objet.
     * lorsque des methodes comme Contains compare deux objets elle compare les adresses
     * de ces objets en memoire. Dans notre cas deux coordonnées peuvent etre identique
     * sans avoir la meme adresse, alors on redefinie la method Equals pour que ces methodes
     * n'uttilise pas la methode Equals par defaut.
     * @param o l'objet avec laquelle on veut comparer
     * @return si les objets sont identiques
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return x == coord.x && y == coord.y;
    }

    /**
     * revoie le clone d'une coordonnée
     * @return le clone
     */
    @Override
    public Coord clone() {
        return new Coord(this.x, this.y);
    }

    /**
     * renvoie les coordonnées d'une piece
     * @param p la piece
     * @return sa coordonnées
     */
    public static Coord coordFromPiece(IPiece p){
        return new Coord(p.getLigne(), p.getColonne());
    }
}
