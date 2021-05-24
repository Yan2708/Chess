package coordonnee;

import echiquier.Echiquier;
import echiquier.IPiece;

import static java.lang.Math.abs;

/**
 * permet de manipuler des coordonnées (x,y)
 * notation :
 * cS = cStart ----> coordonnées de depart
 * cF = cFinal ----> coordonnées d'arrivé
 * sC = sensibleCoord ----> coordonnées sensible
 * cP = coordPiece ----> coordonnées de la Piece.
 * etc...
 */
public class Coord {

    /* X represente les lignes et y les colonnes d'un tableau 2d */
    private int x,y; // A DEMANDER SI C'EST CORRECT

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /** Constructeur de coordonnée*/
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Constructeur de coordonnée a partir d'une String
     * "b2" devient (6, 1), les lignes et colonnes sont inversés
     * - pour arriver d'une lettre majuscule à un chiffre il faut le soustraire par 65
     * - pour arriver d'un nombre en char a un Int il faut le soustaire par 48, puis l'inverser
     * selon les lignes de l'echiquier.
     **/
    public Coord(String c){
        this(abs((c.charAt(1) - 48) - Echiquier.LIGNE), Character.toUpperCase(c.charAt(0)) - 65);
    }

    /**
     * Ajoute les valeurs d'une coordonnée à une autre
     * @param c la coordonnée a ajouter (souvent un mouvement primaire)
     */
    public void add(Coord c){
        this.x += c.x;
        this.y += c.y;
    }

    /**
     * Renvoie si le chemin est droit ou non
     * (diagonale, horizontale, verticale)
     * @param cS coordonnées de depart
     * @param cF coordonnées d'arrivé
     * @return le chemin est horizontale, verticale ou diagonale
     */
    public static boolean isStraightPath(Coord cS, Coord cF){
        int difX = abs(cS.x - cF.x);
        int difY = abs(cS.y - cF.y);
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
        int x = (cF.x - cS.x);
        int y = (cF.y - cS.y);
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
     * sans avoir la meme adresse, alors on redefinie la method Equals pour ne pas
     * uttiliser celle par defaut.
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

    /** revoie le clone d'une coordonnée */
    @Override
    public Coord clone() {
        return new Coord(this.x, this.y);
    }

    /**
     * la coordonnée en format conventionelle
     * une coordonnée 0,1 devient "b8" (les lignes et les colonnes sont inversé)
     * car dans la table Ascii les lettres commenece à la decimal 97
     * Enfin le numero de ligne est inversé par rapport au ligne d'un tableau
     * (la ligne 1 du tableau devient la ligne 7 sur l'echiquier, 3 -> 5, 2 -> 6 etc...)
     * */
    @Override
    public String toString(){
        return (char)(y + 97)+""+abs(x - Echiquier.LIGNE);
    }
}
