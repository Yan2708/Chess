package echiquier;

import org.junit.jupiter.api.Test;
import pieces.FabriquePiece;
import static echiquier.Echiquier.*;

class EchiquierTest {

    @Test
    void TestEchiquier(){
        Echiquier e = new Echiquier(new FabriquePiece(),"8/1p6/8/2P5/8/8/8/8");
        System.out.println(e.toString());
        e.deplacer(new Coord(3,1),new Coord(3,2));
//        new Coord(3,2)
        System.out.println(e.toString());
        e.deplacer(new Coord(3,1),new Coord(5,1));
//        new Coord(3,2)
        System.out.println(e.toString());
    }

    @Test
    void testRoiIntrouvable() throws RoiIntrouvableException {
        Echiquier e = new Echiquier(new FabriquePiece());
        Coord cR = locateKing("BLANC");
    }


}