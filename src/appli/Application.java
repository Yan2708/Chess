package appli;

import echiquier.*;
import pieces.*;

import java.util.ArrayList;
import java.util.Scanner;

import static echiquier.Echiquier.*;
import static java.lang.Math.abs;
import static pieces.Couleur.*;

public class Application {

    private static final String lettres = "ABCDEFGH";
    private static final String chiffres = "12345678";
    private static Coord cS, cF;



    private static int getIntFromChar(char c){
        return Character.isDigit(c) ? abs(c - 56) : Character.toUpperCase(c) - 64;
    }


    private static void getCoordFromString(String c){
        cS = new Coord(getIntFromChar(c.charAt(1)), getIntFromChar(c.charAt(0)) - 1);
        cF = new Coord(getIntFromChar(c.charAt(3)), getIntFromChar(c.charAt(2)) - 1);
    }

    private static boolean isInputValid(String c){
        return c.length() == 4                       &&
                lettres.contains(c.substring(0,1))   &&
                chiffres.contains(c.substring(1,2))  &&
                lettres.contains(c.substring(2,3))   &&
                chiffres.contains(c.substring(3));
    }

    private static boolean isSemanticValid(String c, Coord cR, String couleur, ArrayList<Coord> cTile){
        getCoordFromString(c);
        String couleurOpp = couleur.equals("BLANC") ? "NOIR" : "BLANC";
        if(!cTile.isEmpty() && !cTile.contains(cF))
            return false;
        IPiece p = Echiquier.getPiece(cS);
        return p.getCouleur().equals(couleur) &&
                !Regle.isPiecePinned(p, cR, couleurOpp) &&
                Regle.isCoupValid(cF, p);
    }

    /**Récupère l'entrée de l'uttilisateur, son coup.
     * Cette méthode utilise la classe Scanner qui couplé à un flux comme system.in
     * permet d'extraire les informations données qui sont ensuite retourné dans un String
     *
     * @return              un String contenant les coups du joue
     */
    private static String getUsersLine(Scanner sc) {
        System.out.print("> ");
        return sc.nextLine();
    }

    public static void main(String[] args) throws RoiIntrouvableException {

        Echiquier e = new Echiquier(new FabriquePiece(),"4r3/8/8/6D1/1F6/8/8/7R");
        String actif = "BLANC";
        String passif = "NOIR";

        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.println(e.toString());
        while(true) {
            String usersLine = getUsersLine(sc);
            Coord cR = locateKing(actif);
            ArrayList<Coord> checkingTile = Regle.getAllCheckingTiles(cR, getPieceFromColor(passif));

            while (!isInputValid(usersLine) &&
                    !isSemanticValid(usersLine, cR, actif, checkingTile) ) {
                System.out.print("#");
                usersLine = getUsersLine(sc);
            }

            e.deplacer(cS, cF);
            System.out.println(e.toString());

            if(Regle.checkForMate(passif, locateKing(passif), getPieceFromColor(actif)))
                break;

            String tmp=actif;
            actif=passif;
            passif=tmp;

        }
        System.out.println("Les "+ passif + "S ont perdu");
        sc.close();
    }



}
