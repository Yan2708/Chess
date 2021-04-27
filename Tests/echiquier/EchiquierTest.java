package echiquier;

import org.junit.jupiter.api.Test;
import pieces.FabriquePiece;
import static echiquier.Echiquier.*;

class EchiquierTest {

//    @Test
//    void TestEchiquier(){
//        Echiquier e = new Echiquier(new FabriquePiece());
//        e.deplacer(1,0,3,0);
//        System.out.println(e.toString());
//        e.deplacer(0,0,2,0);
//        System.out.println(e.toString());
//        e.deplacer(6,0,5,0);
//        System.out.println(e.toString());
//    }

    @Test
    void testRoiIntrouvable() throws RoiIntrouvableException {
        Echiquier e = new Echiquier(new FabriquePiece());
        Coord cR = locateKing("BLANC");
    }
}