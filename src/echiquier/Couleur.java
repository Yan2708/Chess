package echiquier;

/**
 * les couleurs que peut attribuer l'echiquier a ces elements.
 * @author Stefan Radovanovic, Yannick Li, Zakaria Sellam
 */
public enum Couleur {
    NOIR,
    BLANC,
    VIDE // Attribution de la couleur VIDE uniquement pour les cases vides de l'échiquier
    ;

    /** verifie que la couleur entre 2 pieces est identique*/
    public static boolean areSameColor(IPiece p1, IPiece p2){
        return p1.getCouleur() == p2.getCouleur();
    }

    /** verifie que la couleur d'une piece est celle souhaité*/
    public static boolean isRightColor(IPiece p, Couleur couleur){
        return p.getCouleur() == couleur;
    }

    /** verifie que 2 pieces sont opposés (NOIR vs BLANC)*/
    public static boolean areOpposite(IPiece p1, IPiece p2) {
        return p2.getCouleur().equals(getOpposite(p1));
    }

    /** renvoie la couleur opposé à la piece (a redefinir si ajout de Couleur)*/
    public static Couleur getOpposite(IPiece p1) {
        return p1.getCouleur().equals(BLANC) ? NOIR : BLANC;
    }
}
