package appli;

import echiquier.*;
import pieces.*;

import java.util.ArrayList;
import java.util.Scanner;

import static echiquier.Echiquier.*;
import static java.lang.Math.abs;
import static echiquier.Regle.*;

public class Application {

    private static final String lettres = "ABCDEFGH";
    private static final String chiffres = "12345678";
    private static Coord cS, cF, lastCF, lastCS;



    private static int getIntFromChar(char c){
        return Character.isDigit(c) ? abs(c - 56) : Character.toUpperCase(c) - 64;
    }


    private static void getCoordFromString(String c){
        cS = new Coord(getIntFromChar(c.charAt(1)), getIntFromChar(c.charAt(0)) - 1);
        cF = new Coord(getIntFromChar(c.charAt(3)), getIntFromChar(c.charAt(2)) - 1);
    }

    private  static void saveCoord(){
        lastCS=new Coord(cS.getX(), cS.getY());
        lastCF=new Coord(cF.getX(), cF.getY());
    }

    private static boolean isInputValid(String c){
        if(c.length() != 4)
            return false;
        return  lettres.contains(c.substring(0,1).toUpperCase())   &&
                chiffres.contains(c.substring(1,2))  &&
                lettres.contains(c.substring(2,3).toUpperCase())   &&
                chiffres.contains(c.substring(3));
    }

    private static boolean isSemanticValid(String c, Coord cR, String couleur, ArrayList<IPiece> enemies){
        getCoordFromString(c);
        String couleurOpp = couleur.equals("BLANC") ? "NOIR" : "BLANC";

        ArrayList<IPiece> checkingPieces = getAllCheckingPiece(cR, enemies); //un roi peut etre mis en echec par 2 pieces
        ArrayList<Coord> checkingTiles = getAllCheckingTiles(cR, checkingPieces);
        if(!checkingPieces.isEmpty() && !checkingTiles.contains(cF))
            return false;


        IPiece p = Echiquier.getPiece(cS);
        return p.getCouleur().equals(couleur) &&
                !Regle.isPiecePinned(p, cR, couleurOpp) &&
                Regle.isCoupValid(cF, p)
                && !Regle.priseEnDiag(p,cF);
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

        Echiquier e = new Echiquier(new FabriquePiece(),"tcfdrfct/p1pppppp/P7/8/2p5/8/1P1PPPPP/TCFDRFCT");
        String actif = "BLANC";
        String passif = "NOIR";
        String message = "Les "+ passif + "S ont perdu";

        @SuppressWarnings("resource")
        Scanner sc = new Scanner(System.in);
        System.out.println(e.toString());
        while(true) {
            String usersLine = getUsersLine(sc);
            Coord cR = locateKing(actif);

            ArrayList<IPiece> piecesActif = getPieceFromColor(actif);
            ArrayList<IPiece> piecesPassif = getPieceFromColor(passif);

            if(isStaleMate(piecesActif, cR, passif)){
                message = "EGALITE PAR PAT !";
                break;
            }

            while(true){
                try{
                    if(isInputValid(usersLine) &&
                            (isSemanticValid(usersLine, cR, actif, piecesPassif) ||
                            Regle.priseEnPassant(lastCS,lastCF,cS,cF)))
                        break;
                }catch(StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException ignored){}
                System.out.print("#");
                usersLine = getUsersLine(sc);
            }

            if(Regle.priseEnPassant(lastCS,lastCF,cS,cF))
                Echiquier.changePiece(lastCF,getPiece(lastCF).changeToVide(lastCF.getX(), lastCF.getY()));
            e.deplacer(cS, cF);
            Regle.promotion(cF);
            saveCoord();
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
