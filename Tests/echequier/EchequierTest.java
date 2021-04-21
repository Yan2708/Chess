package echequier;

import org.junit.jupiter.api.Test;
import pieces.FabriquePiece;

class EchequierTest {

    @Test
    void TestEchequier(){
        Echequier e = new Echequier(new FabriquePiece());
        e.deplacer(2,0,e.getPiece(1,0));
        System.out.println(e.toString());

    }
}