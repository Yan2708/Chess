package appli;

import Joueur.FabChessJoueur;

import Joueur.chessIHM;
import coordonnee.Coord;
import echiquier.*;
import pieces.*;

import java.util.List;

import static echiquier.Couleur.*;

/**
 * @author Stefan Radovanovic, Yannick Li, Zakaria Sellam
 */
public class Application {

    /** message de debut de partie*/
    private static final String START = "mode de jeux : \n" +
            "- Player vs Player (\"pp\") \n" +
            "- Plaver vs Ia (\"pi\") \n" +
            "- Ia vs Ia (\"ii\") \n\n" +
            "Règles : \n" +
            "- pour proposer un match nul : \"nulle\" \n" +
            "- pour abandonner : \"abandon\"";

    /** reference de joueur et de couleur*/
    private static IChessJoueur actif, passif;
    private static Couleur cActif, cPassif;

    /** message de fin de partie*/
    private static String endGameMessage;

    /** le dernier coup joué*/
    private static String coup;

    /** regroupement de toutes les conditions de fin de partie dans les echecs*/
    public static boolean partieEnd(Coord sC, List<IPiece> allys, List<IPiece> ennemies, Echiquier e){
        if(Regle.isStaleMate(sC, allys, ennemies, e)) {
            endGameMessage = "Egalité par pat";
            return true;
        }
        if(Regle.checkForMate(sC, allys, ennemies, e)){
            endGameMessage ="Les "+ actif.getCouleur() + "S ont perdu";
            return true;
        }
        if(Regle.impossibleMat(allys, ennemies)){
            endGameMessage = "NULLE";
            return true;
        }
        return false;
    }

    /** gestion de l'abandon et de la proposition de nulle*/
    private static boolean isEndByPlayer(Echiquier e, List<IPiece> allys, List<IPiece> enemies, Coord sC){
        switch (coup){
            case"nulle":
                if(passif.acceptDraw()){
                    endGameMessage = "match nul par accord !";
                    return true;
                }
                System.out.print("votre demande de nulle n'a pas été accepté, jouez : > ");
                coup = actif.getCoup(e, allys, enemies, sC);
                return false;
            case"abandon" :
                endGameMessage = "les " + cPassif + "s gagnent par forfait !";
                return true;
            default: return false;
        }
    }

    /** changement de tour, le joueur passif devient actif et pourra jouer*/
    private static void swithJoueur(){
        IChessJoueur tmp = actif;
        actif = passif;
        passif = tmp;

        cActif = actif.getCouleur();
        cPassif = passif.getCouleur();
    }

    public static void main(String[] args) {
        System.out.println(START);
        String mode = chessIHM.getMode();
        Echiquier e = new Echiquier(new FabPiece(), mode, new FabChessJoueur());

        actif = e.getJoueur(BLANC);
        passif = e.getJoueur(NOIR);

        cActif = actif.getCouleur();
        cPassif = passif.getCouleur();

        System.out.println(e.toString());

        while(true){
            Coord sC = e.locateSensiblePiece(cActif);
            List<IPiece> piecesActif = e.getPieceFromColor(cActif);
            List<IPiece> piecesPassif = e.getPieceFromColor(cPassif);

            if(partieEnd(sC, piecesActif, piecesPassif, e))
                break;

            System.out.print(cActif + " joue ");
            coup = actif.getCoup(e, piecesActif, piecesPassif, sC);

            if(isEndByPlayer(e, piecesActif, piecesPassif, sC))
                break;

            System.out.println(actif.coupToString(coup));

            e.deplacer(coup);

            e.checkForPromote(cActif);

            System.out.println(e.toString());

            swithJoueur();
        }

        System.out.println(endGameMessage);
    }

}
