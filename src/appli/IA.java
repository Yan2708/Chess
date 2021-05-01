package appli;

import echiquier.Coord;
import echiquier.IPiece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

import static echiquier.Utils.getAllMoves;

public class IA extends Joueur {

    public IA(String couleur) {
        super(couleur);
    }

    @Override
    public boolean isInputValid(String c) {
        return true;
    }

    @Override
    public boolean isSemanticValid(String c, Coord cR, ArrayList<IPiece> enemies) {
        return true;
    }

    @Override
    public String getCoup(Scanner sc, ArrayList<IPiece> allys, ArrayList<IPiece> enemies, Coord cR) {
        String couleurPassif = getCouleur().equals("BLANC") ? "NOIR" : "BLANC";

        HashMap<IPiece, ArrayList<Coord>> allPossibleMoves = new HashMap<>();
        for(IPiece p : allys){
            allPossibleMoves.put(p, getAllMoves(p, cR, couleurPassif, enemies));
        }

        Random rand = new Random();

        IPiece choosenP = allys.get(rand.nextInt(allys.size()));
        ArrayList<Coord> moves = allPossibleMoves.get(choosenP);

        while(allPossibleMoves.get(choosenP).isEmpty())
            choosenP = allys.get(rand.nextInt(allys.size()));


        cS = new Coord(choosenP.getLigne(), choosenP.getColonne());

        ArrayList<Coord> allMoves = allPossibleMoves.get(choosenP);
        cF = allMoves.get(rand.nextInt(allMoves.size()));

        return "";
    }

    @Override
    public void pause() {
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
    }
}
