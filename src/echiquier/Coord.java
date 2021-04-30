package echiquier;

/**permet de manipuler des coordonnées (x,y)
 * notation :
 * cS = cStart ----> coordonnées de depart
 * cF = cFinal ----> coordonnées d'arrivé */
public class Coord {


    private int x,y;

    /** Constructeur de coordonnée
     *
     * @param x la coordonnée en x
     * @param y la coordonnée en y
     */
    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Getter de x
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /** Getter de y
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /** Ajoute les valeurs d'une coordonnée à une autre
     *
     * @param c             la coordonnée a ajouter (mouvement primaire)
     */
    public void Add(Coord c){
        this.x += c.x;
        this.y += c.y;
    }

    /** Inverse les valeurs de la coordonnée
     *
     */
    public Coord inverse(){
         this.x = -(x);
         this.y = -(y);
        return this;
    }

    /** Méthode de comparaison d'une coordonnée à un objet
     *
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
}
