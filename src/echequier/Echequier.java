package echequier;

import pieces.FabriquePiece;

public class Echequier {
    private static final int LIGNE = 8, COLONNE = 8;

    private IPiece[][] echequier;

    //utiliser la fen methode pour generer un echequier

    public Echequier() {
        echequier = new IPiece[LIGNE][COLONNE ];
        //A faire la suite (fabrique)

        for(int lig = 0; lig < LIGNE - 1; lig++)
            for(int col = 0; col < LIGNE - 1; col++)
                echequier[lig][col] = IFabriquePiece.getPiece();
    }

    @Override
    public String toString() {
        String SAUT = "    --- --- --- --- --- --- --- ---   \n";
        String LETTRE = "     a   b   c   d   e   f   g   h    \n";
        int compteur = COLONNE;
        StringBuilder sb = new StringBuilder(LETTRE + SAUT);

        for(IPiece[] ligne : echequier){
            sb.append(" " + compteur + " |");

            for(IPiece p: ligne) {
                try {
                    sb.append(" " + p.getSymbole() + "|");
                } catch (NullPointerException e) {
                    sb.append("   |");
                }
            }


            sb.append(" " + compteur-- + "\n" + SAUT);
        }

        sb.append(LETTRE);
        return sb.toString();
    }
}