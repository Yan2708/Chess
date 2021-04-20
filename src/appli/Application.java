package appli;

import echequier.Echequier;
import pieces.FabriquePiece;

public class Application {
    public static void main(String[] args) {
        Echequier e = new Echequier();
        System.out.println(e.toString());
    }

}
