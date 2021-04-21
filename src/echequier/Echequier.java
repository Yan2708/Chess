package echequier;


public class Echequier {




    private static final int LIGNE = 8, COLONNE = 8;

    private static final String BasicFen = "tcfdrfct/pppppppp/8/8/8/8/PPPPPPPP/TCFDRFCT";

    private IPiece[][] echequier;

    public Echequier(IFabriquePiece fabrique, String fen) {
        echequier = new IPiece[LIGNE][COLONNE ];
        String[] splittedFen = fen.split("/");

        for(int lig = 0, idx = 0; lig < LIGNE ; lig++, idx++) {
            String s = splittedFen[idx];
            String sequence = (s.matches(".*\\d.*")) ? ReformatFenSequence(s) : s;

            for (int col = 0, cpt = 0; col < COLONNE; col++, cpt++)
                echequier[lig][col] = fabrique.getPiece(sequence.charAt(cpt), lig, col);

        }
    }

    public Echequier(IFabriquePiece fabrique){
        this(fabrique, BasicFen);
    }


    private String ReformatFenSequence(String str){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < str.length(); i++) {
            Character c = str.charAt(i);
            if(Character.isDigit(c)){
                for(int y = 0; y < (int) c; y++)
                    sb.append("V");
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }


    public boolean estVide(int x, int y){
        return echequier[x][y].getPieceType().equals("VIDE");
    }

    //ajouter une erreur dans le cas ou le coup est injouable
    public void deplacer(int xFin, int yFin, IPiece p){
        int xS = p.getLigne();
        int yS = p.getColonne();

        if(p.estPossible(xFin,yFin) && voieLibre(p, xFin, yFin) && !nonValide(p, xFin, yFin)){
            //swap
            //echequier[p.getColonne()][p.getLigne()]=null;

            echequier[xS][yS] = p.changeToVide(xS, yS);
            echequier[xFin][yFin] = p;
            p.newPos(xFin, yFin);
        }
    }

    private int getLongueur(int xStart, int yStart, int xFinal, int yFinal){
        return (int)Math.sqrt((Math.pow(xFinal - xStart, 2) + Math.pow(yFinal - yStart, 2)));
    }

    private int[] getCoefDirecteur(int xStart, int yStart, int xFinal, int yFinal){
        return new int[]{(yFinal - yStart), (xFinal - xStart)};
    }

    private boolean voieLibre(IPiece p, int ligne, int colonne){
        if(p.getPieceType().equals("CAVALIER"))
            return true;

        int xS = p.getLigne();
        int yS = p.getColonne();
        int longueur = getLongueur(xS, yS, ligne, colonne);
        int[] vecteur = getCoefDirecteur(xS, yS, ligne, colonne);

        for(int i = 0; i < longueur - 1 ; i++, xS *= vecteur[1], yS *= vecteur[0])
            if(!p.estPossible(xS,yS) || !estVide(xS,yS))
                return false;

        return true;
    }

    private boolean nonValide(IPiece p, int ligne, int colonne){
        IPiece pA = echequier[ligne][colonne];
        return ((pA.getCouleur().equals(p.getCouleur())) || (pA.getPieceType().equals("ROI")));
    }

    //TEMPORAIRE
    public IPiece getPiece(int x, int y){
        return echequier[x][y];
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