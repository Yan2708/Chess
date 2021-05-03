package echiquier;

import org.junit.jupiter.api.Test;
import pieces.FabriquePiece;
import static echiquier.Utils.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {


    @Test
    void testGetPath() {
        Coord cS = new Coord(0,0), cF = new Coord(5,5);
        ArrayList<Coord> c = Utils.getPath(cS, cF);
        assertEquals(c.size(), 6);
        assertTrue(c.get(0).getX() == 0 && c.get(0).getY() == 0);
        assertTrue(c.get(1).getX() == 1 && c.get(1).getY() == 1);
        assertTrue(c.get(2).getX() == 2 && c.get(2).getY() == 2);
        assertTrue(c.get(3).getX() == 3 && c.get(3).getY() == 3);
        assertTrue(c.get(4).getX() == 4 && c.get(4).getY() == 4);
        assertTrue(c.get(5).getX() == 5 && c.get(5).getY() == 5);
    }

    @Test
    void testGetAllCheckingPiece() {
    }

    @Test
    void testGetAllMoves() {
    }

    @Test
    void testAllMovesDefendingCheck() {
    }

    @Test
    void testGetPningPiece() {
    }
}