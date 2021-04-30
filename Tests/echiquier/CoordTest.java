package echiquier;

import org.junit.jupiter.api.Test;
import pieces.FabriquePiece;

import static echiquier.Coord.getPrimaryMove;
import static echiquier.Coord.isStraightPath;
import static echiquier.Echiquier.*;
import static org.junit.jupiter.api.Assertions.*;

class CoordTest {

    @Test
    void TestGetter() {
        Coord c = new Coord(0,1);
        assertEquals(c.getX(), 0);
        assertEquals(c.getY(), 1);
    }


    @Test
    void testAdd() {
        Coord c = new Coord(0,1);
        Coord d = new Coord(1,0);
        c.Add(d);
        assertEquals(c.getY(), 1);
        assertEquals(c.getX(), 1);
    }

    @Test
    void testInverse() {
        Coord c = new Coord(-5,1);
        Coord inv = c.inverse();
        assertEquals(inv.getX(), 5);
        assertEquals(inv.getY(), -1);
    }


    @Test
    void testIsStraightPath(){
        assertTrue(isStraightPath(new Coord(0,0),new Coord(7,0)));
        assertTrue(isStraightPath(new Coord(0,0),new Coord(0,7)));
        assertTrue(isStraightPath(new Coord(0,0),new Coord(7,7)));
        assertFalse(isStraightPath(new Coord(0,0),new Coord(4,2)));
        assertFalse(isStraightPath(new Coord(0,0),new Coord(-5,8)));
    }

    @Test
    void testGetPrimaryMove(){
        assertEquals(new Coord(0, -1), getPrimaryMove(new Coord(0, 0), new Coord(0, -5)));
        assertEquals(new Coord(-1, -1), getPrimaryMove(new Coord(0, 0), new Coord(-46, -46)));
        assertEquals(new Coord(-1, 0), getPrimaryMove(new Coord(0, 0), new Coord(-6, 0)));
        assertEquals(new Coord(-1, 1), getPrimaryMove(new Coord(0, 0), new Coord(-8, 8)));
        assertEquals(new Coord(0, 1), getPrimaryMove(new Coord(0, 0), new Coord(0, 5)));
        assertEquals(new Coord(1, 1), getPrimaryMove(new Coord(0, 0), new Coord(13, 13)));
        assertEquals(new Coord(1, 0), getPrimaryMove(new Coord(0, 0), new Coord(15, 0)));
        assertEquals(new Coord(1, -1), getPrimaryMove(new Coord(0, 0), new Coord(15, -15)));
    }

    @Test
    void testEquals() {
        Coord c = new Coord(0,1);
        Coord d = new Coord(0,1);
        assertEquals(d, c);
    }
}