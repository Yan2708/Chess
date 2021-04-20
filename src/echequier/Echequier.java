package echequier;


import java.util.ArrayList;

public class Echequier {
    private static final int LIGNE = 8, COLONNE = 8;

    private static final String BasicFen = "tcfdrfct/pppppppp/8/8/8/8/PPPPPPPP/TCFDRFCT";

    private IPiece[][] echequier;

    public Echequier(IFabriquePiece fabrique) {
        echequier = new IPiece[LIGNE][COLONNE ];
        String[] fen = BasicFen.split("/");

        for(int lig = 0, idx = 0; lig < LIGNE ; lig++, idx++) {
            String s = fen[idx];
            String sequence = (s.matches(".*\\d.*")) ? ReformatFenSequence(s) : s;

            for (int col = 0, cpt = 0; col < COLONNE; col++, cpt++)
                echequier[lig][col] = fabrique.getPiece(sequence.charAt(cpt), col, lig);

        }
    }

    private String ReformatFenSequence(String str){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i++) {
            Character c = str.charAt(i);
            if(Character.isDigit(c)){
                for(int y = 0; y < Integer.parseInt(c.toString()); y++)
                    sb.append("V");
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public IPiece[][] getEchequier(){
        return echequier;
    }



    @Override
    public String toString() {
        String SAUT = "    --- --- --- --- --- --- --- ---   \n";
        String LETTRE = "     a   b   c   d   e   f   g   h    \n";
        int compteur = COLONNE;
        StringBuilder sb = new StringBuilder(LETTRE + SAUT);

        for(IPiece[] ligne : echequier){
            sb.append(" " + compteur + " |");

            for(IPiece p: ligne)
                sb.append(" " + p.dessiner() + " |");


            sb.append(" " + compteur-- + "\n" + SAUT);
        }

        sb.append(LETTRE);
        return sb.toString();
    }


}