package echiquier;

import org.junit.jupiter.api.Test;
import pieces.FabriquePiece;
import static echiquier.Utils.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

    @Test
    void TestGetPathToBorder() {
        Coord cS = new Coord(0,0), cF = new Coord(7,0), cP = Coord.getPrimaryMove(cS, cF);
        ArrayList<Coord> c = Utils.getPathToBorder(cS, cP);
        assertEquals(c.size(), 8);
        assertTrue(c.get(0).getX() == 0 && c.get(0).getY() == 0);
        assertTrue(c.get(1).getX() == 1 && c.get(1).getY() == 0);
        assertTrue(c.get(2).getX() == 2 && c.get(2).getY() == 0);
        assertTrue(c.get(3).getX() == 3 && c.get(3).getY() == 0);
        assertTrue(c.get(4).getX() == 4 && c.get(4).getY() == 0);
        assertTrue(c.get(5).getX() == 5 && c.get(5).getY() == 0);
        assertTrue(c.get(6).getX() == 6 && c.get(6).getY() == 0);
        assertTrue(c.get(7).getX() == 7 && c.get(7).getY() == 0);
    }

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
    void testGetAllAttackedPath() {
        Echiquier e = new Echiquier(new FabriquePiece(), "8/8/T3r3/8/8/7F/8/R7");
        Coord cS = new Coord(0,0), cF = new Coord(5,5);
        ArrayList<IPiece> ennemies = Echiquier.getPieceFromColor("BLANC");
        ArrayList<Coord> c = Utils.getAllAttackedPath(Echiquier.locateKing("NOIR"), ennemies);;
//        assertEquals(c.size(), 6);
//        assertTrue(c.get(0).getX() == 0 && c.get(0).getY() == 0);
//        assertTrue(c.get(1).getX() == 1 && c.get(1).getY() == 1);
//        assertTrue(c.get(2).getX() == 2 && c.get(2).getY() == 2);
//        assertTrue(c.get(3).getX() == 3 && c.get(3).getY() == 3);
//        assertTrue(c.get(4).getX() == 4 && c.get(4).getY() == 4);
//        assertTrue(c.get(5).getX() == 5 && c.get(5).getY() == 5);
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