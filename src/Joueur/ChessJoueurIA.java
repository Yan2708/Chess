package Joueur;


import coordonnee.Coord;
import echiquier.Couleur;
import echiquier.Echiquier;
import echiquier.IPiece;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Cette clase represente une Ia pour les echecs sans intelligence, ces coups sont calculés de facon
 * aléatoire.
 */
public class ChessJoueurIA extends ChessJoueur {

    /**
     * {@inheritDoc}
     */
    public ChessJoueurIA(Couleur couleur) {
        super(couleur);
    }

    /**
     * {@inheritDoc}
     * l'ordinateur cherche un coup valide et le renvoie
     */
    @Override
    public String getCoup(Echiquier e, List<IPiece> allys, List<IPiece> enemies, Coord sC) {
        pause();

        HashMap<IPiece, List<Coord>> allPossibleMoves = new HashMap<>();
        for(IPiece p : allys){
            allPossibleMoves.put(p, p.getAllMoves(sC, enemies, e));
        }

        Random rand = new Random();

        IPiece choosenP = allys.get(rand.nextInt(allys.size()));

        int cmpt = 0;   //securité contre les boucles infinis
        // choisis une piece au hasard, si celle-ci n'a pas
        // de mouvement disponible, l'ia continue.
        while(allPossibleMoves.get(choosenP).isEmpty()) {
            choosenP = allys.get(rand.nextInt(allys.size()));
            if(cmpt == 100)
                return "abandon";
            cmpt++;
        }


        Coord cS = choosenP.getCoord();

        List<Coord> allMoves = allPossibleMoves.get(choosenP);
        Coord cF = allMoves.get(rand.nextInt(allMoves.size()));

        return cS.toString() + cF.toString();
    }

    /** il y a une pause quand l'IA joue */
    private void pause() {
        try{
            Thread.sleep(50);
        } catch (Exception ignored){ }
    }

    /** renvoie un chaine de caractere du coup de l'Ia (pour un affichage clair)
     * @param coup le coup de l'Ia
     * */
    @Override
    public String coupToString(String coup) {
        return "> " + coup;
    }

    /** l'Ia accepte toujours la nulle*/
    @Override
    public boolean acceptDraw() {
        return true;
    }
}
