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

             // Si la sequence possede un nombre il faut la modifier pour quelle soit lisible par la suite
             // matches(".*\\d.*") permet de check si une chaine de caractère est composée d'au moins un int.
             // https://stackoverflow.com/a/18590949
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

    /** lorsque la sequence d'un enregistrement fen possede un entier il faut le remplacer par
     * l'initial "V" pour Piece VIDE, donc "drt3pp" devient "drtVVVpp"
     *
     * @param str               la sequence à formater
     * @return                  la sequence formatée
     * */
    private String ReformatFenSequence(String str){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
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

    /** Renvoie la pièce aux coordonnées
     *
     * @param c             les coordonnées
     * @return              la pièce
     * */
    private IPiece getPiece(Coord c){
        return echiquier[c.x][c.y];
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
        int longueur = getLongueur(cS, c);
        // on applique le mouvement primaire une premiere fois
        // pour ne pas tester sur la case de la piece
        cS.x += pM.x; cS.y += pM.y;     


        for (int i = 0; i < longueur - 1; i++, cS.x += pM.x, cS.y += pM.y) {
            if(!estVide(cS))
                return false;
        }
        return true;
    }

    /** Renvoie la longueur entre 2 points.
     *  /!\ que pour les diagonales et droites dont la longueur est toujours un entier naturel.
     *
     * @param cS                    coordonnées de la case de depart
     * @param cF                    coordonnées de la case d'arrivé
     * @return                      la longueur entre les 2 points
     * */
    private int getLongueur(Coord cS, Coord cF){
        return (int)Math.sqrt((Math.pow(cF.x - cS.x, 2) + Math.pow(cF.y - cS.y, 2)));
    }

    /** Renvoie le mouvement primaire entre deux points.
     * EST(1,0),NORD_EST(1,1),NORD(0,1),NORD_OUEST(-1,1),OUEST (-1,0),SUD_OUEST(-1,-1),SUD(0,-1),SUD_EST(1,-1)
     *
     * @param cS                    coordonnées de la case de depart
     * @param cF                    coordonnées de la case d'arrivé
     * @return                      le mouvement primaire dans un tableau
     * */
    private Coord getPrimaryMove(Coord cS, Coord cF){
        int x = (cF.x - cS.x);
        int y = (cF.y - cS.y);
        if(Math.abs(x)>1)
            x=x/Math.abs(x);
        if(Math.abs(y)>1)
            y=y/Math.abs(y);
        return new Coord(x,y);
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
        String SAUT = "    --- --- --- --- --- --- --- ---   \n"; // delimite les lignes
        String LETTRE = "     a   b   c   d   e   f   g   h    \n"; // symbolise les colonnes
        int compteur = COLONNE; // compteur de de ligne
        StringBuilder sb = new StringBuilder(LETTRE + SAUT);

        for(IPiece[] ligne : echiquier){
            sb.append(" ").append(compteur).append(" |");

            for(IPiece p: ligne)
                sb.append(" ").append(p.dessiner()).append(" |");

            sb.append(" ").append(compteur--).append("\n").append(SAUT);
        }

        sb.append(LETTRE);
        return sb.toString();
    }
}