package appli;

import echiquier.*;
import pieces.*;
import static echiquier.Utils.*;

import java.util.ArrayList;
import java.util.Scanner;

import static echiquier.Echiquier.*;
import static java.lang.Math.abs;
import static echiquier.Regle.*;

public class Application {

    private static final String LETTRES = "ABCDEFGH"; // les lettres de l'echiquier
    private static final String CHIFFRES = "12345678";  // les chiffres de l'echiquier
    private static Coord cS, cF; //, lastCF, lastCS;
    private static String actif, passif; // les différents joueurs

    /** Conversion d'un char représentant une partie de coordonnée en int
     *
     * @param c le char
     * @return  le chiffre correspondant
     */
    private static int getIntFromChar(char c){
        return Character.isDigit(c) ? abs(c - 56) : Character.toUpperCase(c) - 64;
    }

    /** converti un coup en coordonnées
     *
     * @param c le coup
     */
    private static void getCoordFromString(String c){
        cS = new Coord(getIntFromChar(c.charAt(1)), getIntFromChar(c.charAt(0)) - 1);
        cF = new Coord(getIntFromChar(c.charAt(3)), getIntFromChar(c.charAt(2)) - 1);
    }

//    private  static void saveCoord(){
//        lastCS = new Coord(cS.getX(), cS.getY());
//        lastCF = new Coord(cF.getX(), cF.getY());
//    }

    /** Vérifie qu'une entrée est valide syntaxiquement
     *
     * @param c l'entrée
     * @return  si l'entrée est valide
     */
    private static boolean isInputValid(String c){
        return  c.length() == 4 &&
                LETTRES.contains(c.substring(0,1).toUpperCase())   &&
                CHIFFRES.contains(c.substring(1,2))  &&
                LETTRES.contains(c.substring(2,3).toUpperCase())   &&
                CHIFFRES.contains(c.substring(3));
    }

    /** Vérifie qu'une entrée est une coup jouable
     *
     * @param c         l'entrée
     * @param cR        la position du roi
     * @param enemies   les pièces ennemies
     * @return          si l'entrée est un coup légal ou non
     */
    private static boolean isSemanticValid(String c, Coord cR, ArrayList<IPiece> enemies){
        getCoordFromString(c);
//        String couleurOpp = couleur.equals("BLANC") ? "NOIR" : "BLANC";
//
//        ArrayList<IPiece> checkingPieces = getAllCheckingPiece(cR, enemies); //un roi peut etre mis en echec par 2 pieces
//        ArrayList<Coord> checkingTiles = getAllCheckingTiles(cR, checkingPieces);
//        if(!checkingPieces.isEmpty() && !checkingTiles.contains(cF))
//            return false;
//
//
//        IPiece p = Echiquier.getPiece(cS);
//        return p.getCouleur().equals(couleur) &&
//                !Regle.isPiecePinned(p, cR, couleurOpp) &&
//                Regle.isCoupValid(cF, p);
        IPiece p = getPiece(cS);
        return p.getCouleur().equals(actif) &&
                getAllMoves(p, cR, passif, enemies).contains(cF);
    }

    /**Récupère l'entrée de l'uttilisateur, son coup.
     * Cette méthode utilise la classe Scanner qui couplé à un flux comme system.in
     * permet d'extraire les informations données qui sont ensuite retournées dans un String
     *
     * @param sc            le scanner
     * @return              un String contenant les coups du joue
     */
    private static String getUsersLine(Scanner sc) {
        System.out.print("> ");
        return sc.nextLine();
    }

    public static void main(String[] args) throws RoiIntrouvableException {

        Echiquier e = new Echiquier(new FabriquePiece());
        actif = "BLANC";
        passif = "NOIR";

        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.println(e.toString());
        while(true) {
            Coord cR = locateKing(actif);

            ArrayList<IPiece> piecesActif = getPieceFromColor(actif);
            ArrayList<IPiece> piecesPassif = getPieceFromColor(passif);

            if(isStaleMate(piecesPassif, piecesActif, passif, cR)){
                System.out.println("EGALITE PAR PAT !");
                break;
            }

            String usersLine = getUsersLine(sc);

//            while(true){
//                try{
//                    if(isInputValid(usersLine) &&
//                            (isSemanticValid(usersLine, cR, actif, piecesPassif))) //||
//                            //Regle.priseEnPassant(lastCS,lastCF,cS,cF)))
//                        break;
//                }catch(StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException ignored){}
//                System.out.print("#");
//                usersLine = getUsersLine(sc);
//            }

//            if(Regle.priseEnPassant(lastCS,lastCF,cS,cF))
//                Echiquier.changePiece(lastCF,getPiece(lastCF).changeToVide(lastCF.getX(), lastCF.getY()));


            while(isInputValid(usersLine) ? !isSemanticValid(usersLine, cR, piecesPassif) : true){
                System.out.print("#");
                usersLine = getUsersLine(sc);
            }


            e.deplacer(cS, cF);

            e.checkForPromote(actif);

            //saveCoord();
            System.out.println(e.toString());

            if(Regle.checkForMate(passif, locateKing(passif), piecesActif))
                break;

            String tmp=actif;
            actif=passif;
            passif=tmp;

        }
        System.out.println("Les "+ passif + "S ont perdu");
        sc.close();
    }



}
