package echiquier;

import echiquier.Coord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordTest {

    @Test
    void testAdd(){
        Coord c1 = new Coord(1,1);
        Coord c2 = new Coord(5,-9);
        Coord c3 = new Coord(6,-8);
        c1.add(c2);
        assertEquals(c3,c1);
    }

    @Test
    void testPrimarymove(){
        Coord c1 = new Coord(1,1);
        Coord c2 = new Coord(0,1);
        Coord c3 = new Coord(-1,0);
        assertEquals(c3, Coord.getPrimaryMove(c1,c2));
    }

    @Test
    void testIsStraightPath(){
        Coord c1 = new Coord(1,1);
        Coord c2 = new Coord(0,0);
        Coord c3 = new Coord(2,3);
        Coord c4 = new Coord(5,0);
        assertTrue(Coord.isStraightPath(c2,c1));
        assertTrue(Coord.isStraightPath(c2,c4));
        assertFalse(Coord.isStraightPath(c2,c3));
        assertFalse(Coord.isStraightPath(c3,c1));
        assertTrue(Coord.isStraightPath(c3,c4));
    }

    @Test
    void cloneTest(){
        Coord c1 = new Coord(1,1);
        Coord c2 = c1.clone();
        Coord c3 = new Coord(1,1);
        assertEquals(c1,c2);
        assertEquals(c1,c3);
    }

    @Test
    void testToString(){
        Coord c =  new Coord("b7");
        Coord c2 = new Coord(2,0);
        assertEquals(c.toString(),"b7");
        assertEquals(c2.toString(),"a6");
        c.add(c2);
        assertEquals(c.toString(),"b5");
    }

}