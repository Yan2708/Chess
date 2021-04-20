package pieces;

import com.sun.javafx.css.CalculatedValue;

public enum PieceType {
    VIDE('V'),
    PION('P'),
    TOUR('T'),
    CAVALIER('C'),
    FOU('F'),
    DAME('D'),
    ROI('R');

    private char type;

    PieceType(char type){
        this.type = type;
    }

    public char getType() {
        return type;
    }

    public static PieceType getInstance(char c)  {
        switch(c){
            case 'P': return PION;
            case 'T': return TOUR;
            case 'C': return CAVALIER;
            case 'F': return FOU;
            case 'D': return DAME;
            case 'R': return ROI;
            default : return VIDE;
        }
    }
}
