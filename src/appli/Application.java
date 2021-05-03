package appli;

import echiquier.*;
import pieces.*;

import java.util.ArrayList;
import java.util.Scanner;

import static echiquier.Echiquier.*;
import static echiquier.Regle.*;

public class Application {

    private static Joueur p1, p2;

    //private static String actif, passif; // les différents joueurs
    private static String getMode(Scanner sc){
        System.out.print("mode de jeux : \n" +
                "- Player vs Player (pp) \n" +
                "- Plaver vs Ia (pi) \n" +
                "- Ia vs Ia (ii) \n" +
                ">");
        return sc.nextLine();
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

    public static void main(String[] args) {

        Echiquier e = new Echiquier(new FabriquePiece());
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        String mode = getMode(sc);
        while(!isValid(mode)){
            System.out.println("#");
            mode = getMode(sc);
        }

        fabriqueJoueur(mode);

        Joueur actif = p1;
        Joueur passif = p2;

        System.out.println(e.toString());

        while(true) {
            //actif.pause();

            String couleurActif = actif.getCouleur();
            String couleurPassif = passif.getCouleur();

            Coord cR = locateKing(couleurActif);

            ArrayList<IPiece> piecesActif = getPieceFromColor(couleurActif);
            ArrayList<IPiece> piecesPassif = getPieceFromColor(couleurPassif);

            if(isStaleMate(piecesPassif, piecesActif, couleurPassif, cR)){
                System.out.println("EGALITE PAR PAT !");
                break;
            }

            String usersLine = actif.getCoup(sc, piecesActif, piecesPassif, cR);

            while(!actif.isInputValid(usersLine) || !actif.isSemanticValid(usersLine, cR, piecesPassif)){
                System.out.print("#");
                usersLine = actif.getCoup(sc, piecesActif, piecesPassif, cR);
            }

            e.deplacer(actif.getcS(), actif.getcF());

            e.checkForPromote(couleurActif);

            //saveCoord();
            System.out.println(e.toString());

            if(Regle.checkForMate(couleurPassif, locateKing(couleurPassif), piecesActif))
                break;

            Joueur tmp = actif;
            actif = passif;
            passif = tmp;
        }
        System.out.println("Les "+ passif.getCouleur() + "S ont perdu");
        sc.close();
    }
}
