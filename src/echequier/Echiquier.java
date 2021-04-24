package echequier;


public class Echiquier {



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

    /** Getter de l'échiquier
     *
     * @return              l'echiquier
     * */
    public IPiece[][] getEchiquier(){
        return echiquier;
    }

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
                for(int y = 0; y < c - 48; y++)   // les entier commence à 48 dans la table ascii
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

//        if(p.estPossible(cF.getX(), cF.getY()) &&
//                voieLibre(p, cF) && !arriveNonValide(p, cF)){
            echiquier[cS.getX()][cS.getY()] = p.changeToVide(cS.getX(), cS.getX());
            echiquier[cF.getX()][cF.getY()] = p;
            p.newPos(cF.getX(), cF.getY());
       // }else System.out.println("Coup non valide");
    }



    /** Renvoie la pièce aux coordonnées
     *
     * @param c             les coordonnées
     * @return              la pièce
     * */
    public IPiece getPiece(Coord c){
        return echiquier[c.getY()][c.getX()];
    }

    /** Vérifie si une case aux coordonnées est vide
     *
     * @param c                 coordonnées de la case
     * @return                  la case est vide
     * */
    public boolean estVide(Coord c){
        return echiquier[c.getX()][c.getY()].getPieceType().equals("VIDE");
    }


    private static boolean horsLimite(Coord c){
        int x = c.getX(), y = c.getY();
        return (x >= 0 && x < LIGNE) && (y >= 0 && y < COLONNE);
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