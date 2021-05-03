package appli;

import echiquier.*;
import pieces.*;

import java.util.ArrayList;
import java.util.Scanner;

import static echiquier.Echiquier.*;
import static echiquier.Regle.*;

public class Application {

    private static Joueur p1, p2, actif, passif;
    private static String usersLine, message, couleurActif, couleurPassif;
    private static final String DISPLAY = "mode de jeux : \n" +
                                    "- Player vs Player (\"pp\") \n" +
                                    "- Plaver vs Ia (\"pi\") \n" +
                                    "- Ia vs Ia (\"ii\") \n\n" +
                                    "Règles : \n" +
                                    "- pour proposer un match nul : \"nulle\" \n" +
                                    "- pour abandonner : \"abandon\"";

    //private static String actif, passif; // les différents joueurs
    private static String getMode(Scanner sc){
        System.out.print("Votre mode : > ");
        return sc.nextLine();
    }

    private static boolean finish(String coup, Scanner sc){
        switch (coup){
            case"nulle":
                if(passif.validDraw(sc)){
                    message = "match nul par accord !";
                    return true;
                }
                System.out.print("votre demande de nulle n'a pas été accepté, jouez : > ");
                usersLine = sc.nextLine();
                return false;
            case"abandon" :
                message = "les " + couleurPassif + "s gagnent par forfait !";
                return true;
            default: return false;
        }
    }

    private static boolean isValid(String mode){
        switch (mode) {
            case "pp":
            case "pi":
            case "ii":
                return true;
            default:return false;
        }
    }

    private static void fabriqueJoueur(String mode){
        switch (mode) {
            case "pp":  p1 = new Joueur("BLANC");
                        p2 = new Joueur("NOIR");
                        break;
            case "pi":  p1 = new Joueur("BLANC");
                        p2 = new IA("NOIR");
                        break;
            case "ii":  p1 = new IA("BLANC");
                        p2 = new IA("NOIR");
                        break;
        }
    }

    private static boolean partieContinue(ArrayList<IPiece> ennemies, ArrayList<IPiece> allys, Coord cR){
        if(isStaleMate(ennemies, allys, couleurPassif, cR)) {
            message = "Egalité par pat";
            return true;
        }
        if(Regle.checkForMate(couleurActif, cR, ennemies)){
            message="Les "+ actif.getCouleur() + "S ont perdu";
            return true;
        }
        if(Regle.impossibleMat(ennemies,allys)){
            message = "NULLE";
            return true;
        }
        return false;
    }

    private static void switchJoueur(){
        Joueur tmp = actif;
        actif = passif;
        passif = tmp;
        ///
        couleurActif = actif.getCouleur();
        couleurPassif = passif.getCouleur();
    }

    public static void main(String[] args) {

        Echiquier e = new Echiquier(new FabriquePiece());

        System.out.println(DISPLAY);

        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        String mode = getMode(sc);
        while(!isValid(mode)){
            System.out.println("#");
            mode = getMode(sc);
        }

        fabriqueJoueur(mode);

        actif = p1;
        passif = p2;
        couleurActif = p1.getCouleur();
        couleurPassif = p2.getCouleur();

        System.out.println(e.toString());
        while(true) {
            //actif.pause();
            Coord cR = locateKing(couleurActif);

            ArrayList<IPiece> piecesActif = getPieceFromColor(couleurActif);
            ArrayList<IPiece> piecesPassif = getPieceFromColor(couleurPassif);

            if(partieContinue(piecesPassif, piecesActif, cR))
                break;

            System.out.print(couleurActif + " joue ");
            usersLine = actif.getCoup(sc, piecesActif, piecesPassif, cR);

            if(finish(usersLine, sc))
                break;

            while(!actif.isInputValid(usersLine) || !actif.isSemanticValid(usersLine, cR, piecesPassif)){
                System.out.print("#");
                usersLine = actif.getCoup(sc, piecesActif, piecesPassif, cR);
            }

            e.deplacer(actif.getcS(), actif.getcF());

            actif.show();

            e.checkForPromote(couleurActif);

            System.out.println(e.toString());

            switchJoueur();
        }
        System.out.println(message);
        sc.close();
    }
}
