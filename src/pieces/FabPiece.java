package pieces;

import echiquier.Coord;
import echiquier.Couleur;
import echiquier.IFabPiece;
import echiquier.IPiece;

/**
 * {@inheritDoc}
 * @author Stefan Radovanovic, Yannick Li, Zakaria Sellam
 */
public class FabPiece implements IFabPiece {

    /**
     * {@inheritDoc}
     */
    public IPiece getPiece(char type, Coord c) {
        Couleur color = Character.isUpperCase(type) ? Couleur.BLANC : Couleur.NOIR;
        switch(Character.toUpperCase(type)){
            case 'F':
                return new Fou(c, color);
            case 'R':
                return new Roi(c, color);
            case 'D':
                return new Dame(c, color);
            case 'P':
                return new Pion(c, color);
            case 'T':
                return new Tour(c, color);
            case 'C':
                return new Cavalier(c, color);
            default: return new Vide(c, Couleur.VIDE);
        }
    }
}
