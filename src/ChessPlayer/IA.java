package ChessPlayer;


import coordonnee.Coord;
import echiquier.Couleur;
import echiquier.IPiece;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;




public class IA extends Joueur {

    public IA(Couleur couleur) {
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

        HashMap<IPiece, ArrayList<Coord>> allPossibleMoves = new HashMap<>();
        for(IPiece p : allys){
            allPossibleMoves.put(p, p.getAllMoves(cR, enemies));
        }

        Random rand = new Random();

        IPiece choosenP = allys.get(rand.nextInt(allys.size()));

        while(allPossibleMoves.get(choosenP).isEmpty())
            choosenP = allys.get(rand.nextInt(allys.size()));


        cS = choosenP.getCoord();

        ArrayList<Coord> allMoves = allPossibleMoves.get(choosenP);
        cF = allMoves.get(rand.nextInt(allMoves.size()));

        return "";
    }

    @Override
    public void pause() {
        try {
            Thread.sleep(200);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void show(){
        System.out.println("> " + cS.toString() + cF.toString());
    }

    @Override
    public boolean validDraw(Scanner sc) {
        return true;
    }
}
