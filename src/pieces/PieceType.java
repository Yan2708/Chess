package pieces;

import com.sun.javafx.css.CalculatedValue;

public enum PieceType {
    VIDE,
    PION,
    TOUR,
    CAVALIER,
    FOU,
    DAME,
    ROI;

    /** Retourne le type de pièce selon la pièce demandée
     *
     * @param c         le caractère représentant la pièce
     * @return          le type de pièce
     */
    public static PieceType getInstance(char c)  {
        switch(c){
            case 'P': return PION;
            case 'T': return TOUR;
            case 'C': return CAVALIER;
            case 'F': return FOU;
            case 'D': return DAME;
            case 'R': return ROI;
            default : return VIDE; // retourne une pièce vide si le charactère n'est pas reconnu
        }
    }
}
