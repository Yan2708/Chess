package echiquier;

import java.util.ArrayList;

public class Echiquier {

    /* Un echiquier est constitué de 8 colonnes et 8 lignes soit 64 cases */
    public static final int LIGNE = 8, COLONNE = 8;

    /* La notation Forsyth-Edwards sert à noter la position des pièces sur l'échiquier.
     * pour l'uttiliser on commence par la premiere ligne est on note l'initial de la piece
     * que l'on veut a la position donnée.
     * exemple : tcfdrct/ donne une tour en A8, un cavalier en B8, un fou en C8 etc
     * le " / " delimite les lignes et les nombres donnent les cases vides.
     */
    private static final String BasicFen = "tcfdrfct/pppppppp/8/8/8/8/PPPPPPPP/TCFDRFCT"; //fen generique

    /* l'échiquier est représenté par un tableau 2d de pieces */
    private static IPiece[][] echiquier;

    /* la fabrique de piece est sauvegardé en attribut pour pouvoir l'utiliser en dehors du constructeur */
    private final IFabriquePiece fabrique;

    /* l'échiquier doit etre composer de deux Rois (blanc et noir) */
    static class RoiIntrouvableException extends Exception{}

    /**
     * constructeur de l'échiquier
     * @param fabrique la fabrique d'objet pièce
     * @param fen l'enregistrement fen
     */
    public Echiquier(IFabriquePiece fabrique, String fen) {
        echiquier = new IPiece[LIGNE][COLONNE ];
        this.fabrique = fabrique;
        try{
            fillBoard(fabrique, fen);
            kingError("BLANC");     //un echiquier doit contenir un roi Blanc
            kingError("NOIR");      //et un roi Noir
        }catch (ArrayIndexOutOfBoundsException | RoiIntrouvableException err){
            fillBoard(fabrique, BasicFen);
        }
    }

    /**
     * rempli le l'echiquier de piece selon la fen en parametre
     * @param fabrique la fabrique d'objet pièce
     * @param fen l'enregistrement fen
     * @throws ArrayIndexOutOfBoundsException si la fen n'est pas correct
     */
    private void fillBoard(IFabriquePiece fabrique, String fen) throws ArrayIndexOutOfBoundsException{
        String[] splittedFen = fen.split("/");  //  le fen est divisé pour n'avoir qu'un tableau de ligne

        for (int lig = 0, idx = 0; idx < splittedFen.length; lig++, idx++) {
            String s = splittedFen[idx];

            // Si la sequence possede un nombre il faut la modifier pour quelle soit lisible par la suite
            // matches(".*\\d.*") permet de check si une chaine de caractère est composée d'au moins un int.
            // https://stackoverflow.com/a/18590949
            String sequence = (s.matches(".*\\d.*")) ? ReformatFenSequence(s) : s;

            for (int col = 0, cpt = 0; cpt < sequence.length(); col++, cpt++)
                echiquier[lig][col] = fabrique.getPiece(sequence.charAt(cpt), lig, col);
        }
    }

    /**
     * constructeur de l'échiquier avec l'enregistrement fen generique,
     * @param fabrique la fabrique d'objet pièce
     */
    public Echiquier(IFabriquePiece fabrique){
        this(fabrique, BasicFen);
    }

    /**
     * lorsque la sequence d'un enregistrement fen possede un entier il faut le remplacer par
     * l'initial "V" pour Piece VIDE, donc "drt3pp" devient "drtVVVpp"
     * @param str la sequence à formater
     * @return la sequence formatée
     */
    private String ReformatFenSequence(String str){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            Character c = str.charAt(i);
            if(Character.isDigit(c)){
                for(int y = 0; y < c - 48; y++)   // les entiers commence à 48 dans la table ascii
                    sb.append("V");
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Permet de deplacer une pièce selon les coordonnées en paramètre.
     * la piece qui se deplace ecrase la piece à la case d'arrivé
     * et son ancienne case devient une case VIDE.
     * @param cS la coordonnée de départ
     * @param cF la coordonnée d'arrivée
     */
    public void deplacer(Coord cS, Coord cF){
        assert(!inBound(cS) && !inBound(cF));
        IPiece p = getPiece(cS);
        changePiece(cS, fabrique.getPiece('V', cS.getX(), cS.getY()));  //piece Vide à cStart
        changePiece(cF, p);
        p.newPos(cF.getX(), cF.getY());
    }

    /** change une pièce sur l'échiquier
     * @param c la coordonnée sur l'échiquier
     * @param p la piece remplaçante
     */
    public static void changePiece(Coord c, IPiece p){
        echiquier[c.getX()][c.getY()] = p;
    }

    /**
     * Renvoie une liste comportant toutes les pieces de l'echiquier
     * de couleur demandée.
     * @param couleur la couleur demandée
     * @return la liste de pieces
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

    /**
     * Renvoie la pièce aux coordonnées en param.
     * @param c les coordonnées
     * @return la pièce
     */
    public static IPiece getPiece(Coord c){
        return echiquier[c.getX()][c.getY()];
    }

    /**
     * Vérifie si le chemin entre 2 points n'a pas d'obstacle (pas l'arrivée)
     * @param c coordonnées de la case d'arrivé
     * @param p la pièce à deplacer
     */
    public static boolean voieLibre(IPiece p, Coord c){
        Coord cS = Coord.coordFromPiece(p);
        if(!Coord.isStraightPath(cS, c))        //si le chemin n'est pas verticale ou horizontale
            return true;                        //il ne peut pas avoir d'obstacle

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

    /**
     * Vérifie si une case aux coordonnées est vide
     * @param c coordonnées de la case
     * @return la case est vide
     */
    public static boolean estVide(Coord c){
        return echiquier[c.getX()][c.getY()].getPieceType().equals("VIDE");
    }

    /**
     * Vérifie si la l'arrivé d'une piece sur une case est valide.
     * @param c coordonnées de la case
     * @param p la pièce à deplacer
     */
    public static boolean isFinishValid(IPiece p, Coord c){
        IPiece pA = getPiece(c);
        return !pA.getCouleur().equals(p.getCouleur()) && !pA.getPieceType().equals("ROI");
    }

    /**
     * Renvoie si une coordonnée est en dehors de l'echiquier ou non
     * @param c la coordonnée
     * @return si c est en dehors ou non
     */
    public static boolean inBound(Coord c){
        int x = c.getX(), y = c.getY();
        return (x >= 0 && x < LIGNE) && (y >= 0 && y < COLONNE);
    }

    /**
     * Recherche le roi sur l'echiquier
     * @param couleur la couleur du roi (NOIR ou BLANC)
     * @return les coordonnées du roi, null si aucun roi n'a été trouvé
     */
    public static Coord locateKing(String couleur) {
        for(IPiece[] ligne : echiquier)
            for(IPiece p : ligne)
                if(p.getPieceType().equals("ROI") && p.getCouleur().equals(couleur))
                    return Coord.coordFromPiece(p);
        return null;
    }

    /**
     * renvoie une exception si il y a pas de roi de couleur donné dans l'echiquier
     * @param couleur la couleur des pieces
     * @throws RoiIntrouvableException aucun roi trouvé
     */
    private static void kingError(String couleur) throws RoiIntrouvableException {
        if(locateKing(couleur) == null)
            throw new RoiIntrouvableException();
    }

    /**
     * vérifie si des pions peuvent être promus.
     * la promotion passe le pion en dame lorsque celui ci a atteint
     * les limites du tableau.
     * @param couleur la couleur des pièces à vérifier
     */
    public void checkForPromote(String couleur){
        int x = couleur.equals("BLANC") ? 0 : 7;
        for(IPiece p : echiquier[x])        // les pieces de la ligne de fin.
            if(p.getPieceType().equals("PION")){
                int y = p.getColonne();
                char type = p.getCouleur().equals("NOIR")?'d':'D';
                echiquier[x][y] = fabrique.getPiece(type, x, y);
            }

    }

    /**
     * Créer une chaîne de caractères comportant l'ensemble de l'échiquier.
     * @return la chaîne de caractères
     */
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