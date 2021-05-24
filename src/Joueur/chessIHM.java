package Joueur;

import coordonnee.Coord;
import echiquier.*;

import java.util.Scanner;

public class chessIHM {

    /** Seul 3 modes de jeu sont acceptés.*/
    private static boolean isValid(String mode){
        switch (mode) {
            case "pp": //joueur contre joueur
            case "pi": // joueur contre ordi
            case "ii": // ordi contre ordi
                return true;
            default:return false;
        }
    }

    /** Renvoie le mode choisi par l'utilisateur */
    public static String getMode(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Votre mode : > ");
        String mode = sc.nextLine();
        while(!isValid(mode)){
            System.out.print("#> ");
            mode = sc.nextLine();
        }
        return mode;
    }

    /**
     * Renvoie le coup de l'utilisateur.
     * Celui-ci passe par deux verification :
     * - la syntax
     * - la semantique
     * La methode prend en compte la nulle et l'abandon
     * @param j le joueur
     * @param e l'echiquier
     * @return  le coup valide
     */
    public static String getCoup(IChessJoueur j, Echiquier e){
        Scanner sc = new Scanner(System.in);
        String coup = sc.nextLine();

        while(!(coup.equals("nulle") || coup.equals("abandon")) &&
                !(syntaxValid(coup) && semanticValid(coup, j, e))){
            System.out.print("#> ");
            coup = sc.nextLine();
        }
        return coup;
    }

    /**
     * Vérifie si la sémantique du coup est valide.
     * A utiliser après inputValid.
     * @param coup  le coup
     * @param j     le joueur
     * @param e     l'echiquier
     * @return      le coup est correcte semantiquement
     */
    private static boolean semanticValid(String coup, IChessJoueur j, Echiquier e){
        int mid = coup.length() / 2;
        Coord cS = new Coord(coup.substring(0,mid));
        Coord cF = new Coord(coup.substring(mid));
        if(!Echiquier.inBound(cS) && !Echiquier.inBound(cF))
            return false;
        IPiece p = e.getPiece(cS);
        return Regle.isRightColor(p, j.getCouleur()) &&
                p.isCoupValid(cF, e);
    }

    /** Si l'input respecte le format d'un coup ("a2a3", b4c2", un pattern identique a chaque coup)*/
    private static boolean syntaxValid(String coup){
        if(coup.length() != 4)
            return false;

        return Character.isLetter(coup.charAt(0)) &&
                Character.isDigit(coup.charAt(1)) &&
                Character.isLetter(coup.charAt(2)) &&
                Character.isDigit(coup.charAt(3));
    }

    /**
     * Renvoie si un joueur propose accepte la proposition de nulle de son adversaire.
     * @param j le joueur adverse
     * @return  le joueur adverse accepte la nulle
     */
    public static boolean acceptDraw(IChessJoueur j) {
        Scanner sc = new Scanner(System.in);
        System.out.print("les " + j.getCouleur() + "s, acceptez vous la nulle ? (O/N) : > ");
        String answer = sc.nextLine();
        while (true) {
            switch (answer) {
                case "O": return true;
                case "N": return false;
            }
            System.out.print("#> ");
            answer = sc.nextLine();
        }
    }

}
