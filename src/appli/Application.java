package appli;

import echequier.Echiquier;
import pieces.FabriquePiece;


public class Application {
    public static void main(String[] args) {
        Echiquier e = new Echiquier(new FabriquePiece());
        System.out.println(e.toString());
    }
}
