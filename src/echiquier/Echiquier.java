package echiquier;


import pieces.FabriquePiece;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Echiquier {

    /** Un echiquier est constitué de 8 colonnes et 8 lignes soit 64 cases*/
    public static final int LIGNE = 8, COLONNE = 8;

    /** La notation Forsyth-Edwards sert à noter la position des pièces sur l'échiquier.
     * pour l'uttiliser on commence par la premiere ligne est on note l'initial de la piece
     * que l'on veut a la position donnée.
     * exemple : tcfdrct/ donne une tour en A8, un cavalier en B8, un fou en C8 etc
     * le " / " delimite les lignes et les nombres donnent les cases vides.
     * */
    private static final String BasicFen = "tcfdrfct/pppppppp/8/8/8/8/PPPPPPPP/TCFDRFCT"; //fen generique

    /** l'échiquier est représenté par un tableau 2d de pieces*/
    private static IPiece[][] echiquier;

    private IFabriquePiece fabrique;

    /** constructeur de l'échiquier
     *
     * @param fabrique          la fabrique d'objet pièce
     * @param fen               l'enregistrement fen
     * */
    public Echiquier(IFabriquePiece fabrique, String fen) {
        echiquier = new IPiece[LIGNE][COLONNE ];
        this.fabrique = fabrique;

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
     * @param cS        la coordonnée de départ
     * @param cF        la coordonnée d'arrivée
     */
    public void deplacer(Coord cS, Coord cF){
        assert(horsLimite(cS) && horsLimite(cF));
        IPiece p = getPiece(cS);
        changePiece(cS, fabrique.getPiece('V', cS.getX(), cS.getY()));
        changePiece(cF, p);
        p.newPos(cF.getX(), cF.getY());
    }

    /** change une pièce sur l'échiquier
     *
     * @param c     la coordonnée sur l'échiquier
     * @param p     la piece remplaçante
     */
    public static void changePiece(Coord c, IPiece p){
        echiquier[c.getX()][c.getY()] = p;
    }

    /** Renvoie toutes une liste comportant toutes les pieces de l'echiquier
     * de couleur demandée
     *
     * @param couleur               la couleur demandée
     * @return                      la liste de pieces
     */
    public static ArrayList<IPiece> getPieceFromColor(String couleur){
        //couleur = (couleur.equals("BLANC")) ? "NOIR" : "BLANC";
        ArrayList<IPiece> pieces = new ArrayList<>();
        for(IPiece[] ligne : echiquier)
            for(IPiece p: ligne){
                if(couleur.equals(p.getCouleur()))
                    pieces.add(p);
            }
        return pieces;
    }

    /** Renvoie la pièce aux coordonnées
     *
     * @param c             les coordonnées
     * @return              la pièce
     * */
    public static IPiece getPiece(Coord c){
        return echiquier[c.getX()][c.getY()];
    }

    /** Vérifie si le chemin entre 2 points n'a pas d'obstacle (pas l'arrivée)
     *
     * @param c                   coordonnées de la case d'arrivé
     * @param p                   la pièce à deplacer
     * */
    public static boolean voieLibre(IPiece p, Coord c){
        Coord cS = new Coord(p.getLigne(), p.getColonne());
        if(!Coord.isStraightPath(cS, c))
            return true;

        Coord pM = Coord.getPrimaryMove(cS, c);
        // on applique le mouvement primaire une premiere fois
        // pour ne pas tester sur la case de la piece
        cS.Add(pM);

        while(!cS.equals(c)) {
            if (!estVide(cS))
                return false;
            cS.Add(pM);
        }
        return true;
    }



    /** Vérifie si une case aux coordonnées est vide
     *
     * @param c                 coordonnées de la case
     * @return                  la case est vide
     * */
    public static boolean estVide(Coord c){
        return echiquier[c.getX()][c.getY()].getPieceType().equals("VIDE");
    }

    /** Vérifie si la l'arrivé d'une piece sur une case est valide.
     *
     * @param c                 coordonnées de la case
     * @param p                 la pièce à deplacer
     * */
    public static boolean isFinishValid(IPiece p, Coord c){
        IPiece pA = getPiece(c);
        return !pA.getCouleur().equals(p.getCouleur()) && !pA.getPieceType().equals("ROI");
    }

    /** Renvoie si une coordonnée est en dehors de l'echiquier ou non
     *
     * @param c         la coordonnée
     * @return          si c est en dehors ou non
     */
    public static boolean horsLimite(Coord c){
        int x = c.getX(), y = c.getY();
        return (x >= 0 && x < LIGNE) && (y >= 0 && y < COLONNE);
    }

    /** Recherche le roi sur l'echiquier
     *
     * @param couleur           la couleur du roi (NOIR ou BLANC)
     * @return                  les coordonnées du roi
     * @throws RoiIntrouvableException      si le roi n'est pas présent sur l'echiquier
     */
    public static Coord locateKing(String couleur) throws RoiIntrouvableException {
        for(IPiece[] ligne : echiquier)
            for(IPiece p : ligne)
                if(p.getPieceType().equals("ROI") && p.getCouleur().equals(couleur))
                    return new Coord(p.getLigne(),p.getColonne());
        throw new RoiIntrouvableException();
    }

    /** vérifie si des pions peuvent être promus
     *
     * @param couleur   la couleur des pièces à vérifier
     */
    public void checkForPromote(String couleur){
        int x = couleur.equals("BLANC") ? 0 : 7;
        for(IPiece p : echiquier[x])
            if(p.getPieceType().equals("PION")){
                int y = p.getColonne();
                char type = p.getCouleur().equals("NOIR")?'d':'D';
                echiquier[x][y] = fabrique.getPiece(type, x, y);
            }

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