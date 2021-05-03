package appli;

import echiquier.Coord;
import echiquier.IPiece;
import echiquier.Utils;

import java.util.ArrayList;
import java.util.Scanner;

import static echiquier.Echiquier.getPiece;
import static echiquier.Utils.getAllMoves;
import static java.lang.Math.abs;

public class Joueur {

    private final String couleur;
    protected Coord cS, cF; //, lastCF, lastCS;
    private static final String LETTRES = "ABCDEFGH"; // les lettres de l'echiquier
    private static final String CHIFFRES = "12345678";  // les chiffres de l'echiquier

    public Joueur(String couleur){
        this.couleur = couleur;
    }
    /**
     * Conversion d'un char représentant une partie de coordonnée en int
     * @param c le char
     * @return le chiffre correspondant
     */
    private int getIntFromChar(char c){
        return Character.isDigit(c) ? abs(c - 56) : Character.toUpperCase(c) - 64;
    }

    /**
     * converti un coup en coordonnées
     * @param c le coup
     */
    private void getCoordFromString(String c){
        cS = new Coord(getIntFromChar(c.charAt(1)), getIntFromChar(c.charAt(0)) - 1);
        cF = new Coord(getIntFromChar(c.charAt(3)), getIntFromChar(c.charAt(2)) - 1);
    }

    /**
     * Vérifie qu'une entrée est valide syntaxiquement
     * @param c l'entrée
     * @return  si l'entrée est valide
     */
    public boolean isInputValid(String c){
        return  c.length() == 4 &&
                LETTRES.contains(c.substring(0,1).toUpperCase())   &&
                CHIFFRES.contains(c.substring(1,2))  &&
                LETTRES.contains(c.substring(2,3).toUpperCase())   &&
                CHIFFRES.contains(c.substring(3));
    }

    /**
     * Vérifie qu'une entrée est une coup jouable
     * @param c l'entrée
     * @param cR la position du roi
     * @param enemies les pièces ennemies
     * @return si l'entrée est un coup légal ou non
     */
    public boolean isSemanticValid(String c, Coord cR, ArrayList<IPiece> enemies){
        String couleurPassif = couleur.equals("BLANC") ? "NOIR" : "BLANC";

        getCoordFromString(c);

        IPiece p = getPiece(cS);
        return p.getCouleur().equals(couleur) &&
                Utils.getAllMoves(p, cR, couleurPassif, enemies).contains(cF);
    }

    /**
     * Récupère l'entrée de l'uttilisateur, son coup.
     * @param sc le scanner
     * @param ennemies les pieces ennemies
     * @param cR la coordonnée du roi
     * @return un String contenant les coups du joue
     */
    public String getCoup(Scanner sc, ArrayList<IPiece> allys, ArrayList<IPiece> enemies, Coord cR) {
        System.out.print("> ");
        return sc.nextLine();
    }

    public String getCouleur(){
        return couleur;
    }

    public Coord getcS() {
        return cS;
    }

    public Coord getcF() {
        return cF;
    }

    public void pause() {
    }
}
