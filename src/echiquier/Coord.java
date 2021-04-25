package echiquier;

import java.util.Objects;

/**permet de manipuler des coordonnées (x,y)
 * notation :
 * cS = cStart ----> coordonnées de depart
 * cF = cFinal ----> coordonnées d'arrivé */
public class Coord {

    private int x,y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void Add(Coord c){
        this.x += c.x;
        this.y += c.y;
    }

    public void inverse(){
        this.x = -(x);
        this.y = -(y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return x == coord.x && y == coord.y;
    }
}
