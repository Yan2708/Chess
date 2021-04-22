package echequier;

import org.junit.jupiter.api.Test;
import pieces.FabriquePiece;

class EchiquierTest {

    @Test
    void TestEchiquier(){
        Echiquier e = new Echiquier(new FabriquePiece());
        e.deplacer(1,0,3,0);
        System.out.println(e.toString());
        e.deplacer(0,0,2,0);
        System.out.println(e.toString());
    }
}