package echiquier;

import java.util.List;

/**
 * interface definissant les joueurs d'un echiquier.
 * @author Stefan Radovanovic, Yannick Li, Zakaria Sellam
 */
public interface IChessJoueur {

    /**
     * Renvoie le coup du joueur,
     * si c'est un Humain le coup se fera avec l'invite de commande
     * mais avec une IA celle-ci a besoin de l'etat de l'echiquier.
     * @param e l'echiquier
     * @param allys les pieces alliés
     * @param enemies les pieces ennemies
     * @param sC la coordonnée sensible de l'allié
     * @return le coup du joueur.
     */
    String getCoup(Echiquier e, List<IPiece> allys, List<IPiece> enemies, Coord sC);

    /** Renvoie la couleur de la pièce */
    Couleur getCouleur();

    /** Renvoie une chaine de caractères du coup au format "c3c4" */
    String coupToString(String coup);

    /** demande au joueur s'il accept la nulle (une Ia ne refuse jamais) */
    boolean acceptDraw();
}
