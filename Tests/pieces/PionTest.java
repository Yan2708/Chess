package pieces;


import coordonnee.Coord;
import org.junit.jupiter.api.Test;
import echiquier.Couleur;

import static org.junit.jupiter.api.Assertions.*;

class PionTest {

    @Test
    void estPossible() {
        Pion pion = new Pion(new Coord(0,0),Couleur.BLANC);
    }

    @Test
    void isCoupValid() {
    }

    @Test
    void getAllMoves() {
    }
}