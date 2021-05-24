package echiquier;

/**
 * fabrique de joueur
 */
public interface IFabChessJoueur {

    /**
     * Crée un joueur selon le type donnée
     * 'H' pour joueur Humain
     * 'I' pour jouer IA
     * @param type le type de joueur
     * @param couleur la couleur associé au joueur
     * @return le joueur
     */
    IChessJoueur getJoueur(char type, Couleur couleur);
}
