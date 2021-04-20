package pieces;

import echequier.IFabriquePiece;
import echequier.IPiece;

public class FabriquePiece implements IFabriquePiece {
    //a voir pour les pieces

    public static IPiece getPiece() {
        return new Roi();
    }
}
