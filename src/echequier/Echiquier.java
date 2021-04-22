package echequier;


public class Echiquier {

    /**permet de manipuler des coordonnées (x,y)
     * notation :
     * cS = cStart ----> coordonnées de depart
     * cF = cFinal ----> coordonnées d'arrivé */
    class Coord {
        private int x, y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    /** Un echiquier est constitué de 8 colonnes et 8 lignes soit 64 cases*/
    private static final int LIGNE = 8, COLONNE = 8;

    /** La notation Forsyth-Edwards sert à noter la position des pièces sur l'échiquier.
     * pour l'uttiliser on commence par la premiere ligne est on note l'initial de la piece
     * que l'on veut a la position donnée.
     * exemple : tcfdrct/ donne une tour en A8, un cavalier en B8, un fou en C8 etc
     * le " / " delimite les lignes et les nombres donnent les cases vides.
     * */
    private static final String BasicFen = "tcfdrfct/pppppppp/8/8/8/8/PPPPPPPP/TCFDRFCT"; //fen generique

    /** l'échiquier est représenté par un tableau 2d de pieces*/
    private IPiece[][] echiquier;

    /** constructeur de l'échiquier
     *
     * @param fabrique          la fabrique d'objet pièce
     * @param fen               l'enregistrement fen
     * */
    public Echiquier(IFabriquePiece fabrique, String fen) {
        echiquier = new IPiece[LIGNE][COLONNE ];
        String[] splittedFen = fen.split("/");  //  le fen est divisé pour n'avoir qu'un tableau de ligne

        for(int lig = 0, idx = 0; lig < LIGNE ; lig++, idx++) {
            String s = splittedFen[idx];
            String sequence = (s.matches(".*\\d.*")) ? ReformatFenSequence(s) : s;

            for (int col = 0, cpt = 0; col < COLONNE; col++, cpt++)
                echiquier[lig][col] = fabrique.getPiece(sequence.charAt(cpt), lig, col);

        }
    }

    /** constructeur de l'échiquier avec l'enregistrement fen generique
     *
     * @param fabrique          la fabrique d'objet pièce
     * */
    public Echiquier(IFabriquePiece fabrique){
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

    /** Permet de deplacer une pièce selon les coordonnées en paramètre.
     * Pour deplacer une piece il y a 3 condittions :
     * -    Le mouvement soit autorisé par la piece
     * -    que la voie soit libre (sans obstacle)
     * -    que la case d'arrivé ne soit pas le roi ou un pièce de même couleur
     *
     * @param xS                la ligne de depart
     * @param yS                la colonne de depart
     * @param xF                la ligne d'arrivée
     * @param yF                la colonne d'arrivée
     * */
    public void deplacer(int xS, int yS, int xF, int yF){
        // On utilise pas la classe Coord car la methode est public
        //TODO : ajouter une erreur dans le cas ou le coup est injouable
        Coord cS = new Coord(xS, yS);
        IPiece p = getPiece(cS);
        Coord cF = new Coord(xF, yF);

        if(p.estPossible(cF.x, cF.y) && voieLibre(p, cF) && !arriveNonValide(p, cF)){
            echiquier[cS.x][cS.y] = p.changeToVide(cS.x, cS.y);
            echiquier[cF.x][cF.y] = p;
            p.newPos(cF.x, cF.y);
        }else System.out.println("Coup non valide");
    }

    /** Vérifie si une case aux coordonnées est vide
     *
     * @param c                 coordonnées de la case
     * @return                  la case est vide
     * */
    private boolean estVide(Coord c){
        return echiquier[c.x][c.y].getPieceType().equals("VIDE");
    }

    /** Vérifie si le chemin entre 2 points n'a pas d'obstacle (pas l'arrivée)
     *
     * @param c                   coordonnées de la case d'arrivé
     * @param p                   la pièce à deplacer
     * */
    private boolean voieLibre(IPiece p, Coord c){
        if(p.getPieceType().equals("CAVALIER")) // le cavalier n'a pas d'"obstacle" en mouvement
            return true;

        Coord cS = new Coord(p.getLigne(), p.getColonne());
        Coord pM = getPrimaryMove(cS, c);
        // on applique le mouvement primaire une premiere fois
        // pour ne pas tester sur la case de la piece
        cS.x += pM.x; cS.y += pM.y;     

        for (int i = 0; i < getLongueur(cS, c); i++, cS.x++, cS.y++) {
            if(!estVide(cS))
                return false;
        }
        return true;
    }

    /** Vérifie si la l'arrivé d'une piece sur une case est valide.
     *
     * @param c                 coordonnées de la case
     * @param p                 la pièce à deplacer
     * */
    private boolean arriveNonValide(IPiece p, Coord c){
        IPiece pA = echiquier[c.x][c.y];
        return ((pA.getCouleur().equals(p.getCouleur())) || (pA.getPieceType().equals("ROI")));
    }

    /** Créer une chaîne de caractères comportant l'ensemble de l'échiquier.
     *
     * @return                  la chaîne de caractères
     * */
    @Override
    public String toString() {
        String SAUT = "    --- --- --- --- --- --- --- ---   \n";
        String LETTRE = "     a   b   c   d   e   f   g   h    \n";
        int compteur = COLONNE;
        StringBuilder sb = new StringBuilder(LETTRE + SAUT);

        for(IPiece[] ligne : echiquier){
            sb.append(" ").append(compteur).append(" |");

            for(IPiece p: ligne)
                sb.append(" " + p.dessiner() + " |");


            sb.append(" " + compteur-- + "\n" + SAUT);
        }

        sb.append(LETTRE);
        return sb.toString();
    }
}