package echiquier;

import java.util.LinkedList;

/**
 * Le corps du projet.
 * fonctione grace a la notation Forsyth-Edwards.
 * il doit contenir deux pieces sensible (2 rois par defaut).
 * @author Stefan Radovanovic, Yannick Li, Zakaria Sellam
 */
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

    /* les joueurs des pieces Noirs et Blances */
    private IChessJoueur j1, j2;

    /* l'échiquier est représenté par un tableau 2d de pieces */
    private IPiece[][] echiquier;

    /* la fabrique de piece est sauvegardé en attribut pour pouvoir l'utiliser en dehors du constructeur */
    private final IFabPiece fabrique;

    /* l'échiquier doit etre composer de deux Rois (blanc et noir) */
    static class NoSensiblePieceException extends Exception{}

    /**  */
    static class NonValidFenException extends Exception{}

    /**
     * constructeur de l'échiquier
     * @param fPiece la fabrique d'objet pièce
     * @param fen l'enregistrement fen
     * @param mode le mode chosi pour jouer
     * @param fJoueur fabrique de Joueur
     */
    public Echiquier(IFabPiece fPiece, String fen, String mode, IFabChessJoueur fJoueur) {
        setJoueur(mode, fJoueur);
        echiquier = new IPiece[LIGNE][COLONNE ];
        this.fabrique = fPiece;
        try{
            fillBoard(fPiece, fen);
            SensibleError();            //un echiquier doit contenir deux rois
        }catch (NonValidFenException  err){
            throw new IllegalArgumentException("La fen n'est pas correct");
        }catch (NoSensiblePieceException err){
            throw new IllegalArgumentException("Il manque deux pieces sensible à la fen");
        }
    }

    /** constructeur de l'échiquier avec l'enregistrement fen generique */
    public Echiquier(IFabPiece fabrique, String mode, IFabChessJoueur fJoueur){
        this(fabrique, BasicFen, mode, fJoueur);
    }

    /** constructeur par defaut d'un echequier */
    public Echiquier(IFabPiece fabrique, IFabChessJoueur fJoueur){
        this(fabrique, BasicFen, "pp", fJoueur);
    }

    /** constructeur de l'échiquier avec le mode par default (pour les test) */
    public Echiquier(String fen, IFabPiece fabrique, IFabChessJoueur fJoueur){
        this(fabrique, fen, "pp",fJoueur);
    }

    /**
     * setter des jouers de l'echiquier selon le mode.
     * si le mode par defaut est humain contre humain (raison du switch redondant)
     * @param mode le mode
     * @param fJoueur la fabrique de Joueur
     */
    private void setJoueur(String mode, IFabChessJoueur fJoueur){
        switch (mode) {
            case "pi":  j1 = fJoueur.getJoueur('H', Couleur.BLANC);
                        j2 = fJoueur.getJoueur('I', Couleur.NOIR);
                        break;
            case "ii":  j1 = fJoueur.getJoueur('I', Couleur.BLANC);
                        j2 = fJoueur.getJoueur('I', Couleur.NOIR);
                        break;
            default:    j1 = fJoueur.getJoueur('H', Couleur.BLANC);
                        j2 = fJoueur.getJoueur('H', Couleur.NOIR);
        }
    }

    /**
     * rempli le l'echiquier de piece selon la fen en parametre
     * @param fabrique la fabrique d'objet pièce
     * @param fen l'enregistrement fen
     * @throws ArrayIndexOutOfBoundsException si la fen n'est pas correct
     */
    private void fillBoard(IFabPiece fabrique, String fen)
            throws NonValidFenException {
        String[] splittedFen = fen.split("/");  //  le fen est divisé pour n'avoir qu'un tableau de ligne

        for (int lg = 0; lg < splittedFen.length; lg++) {
            String s = splittedFen[lg];

            // https://stackoverflow.com/a/18590949
            String sequence = (s.matches(".*\\d.*")) ? ReformatFenSequence(s) : s;

            if (sequence.length() != LIGNE) throw new NonValidFenException();

            for (int cl = 0; cl < COLONNE; cl++)
                echiquier[lg][cl] = fabrique.getPiece(sequence.charAt(cl), new Coord(lg, cl));
        }
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
     * renvoie la liste de toutes les coordonnées atteignables dans l'échiquier par une piece donnée.
     * @param p la piece
     * @return la liste des coordonnées atteignable
     */
    public LinkedList<Coord> allClassicMoves(IPiece p){
        LinkedList<Coord> allClassicMoves = new LinkedList<>();
        for(IPiece[] ligne : echiquier) {
            for (IPiece piece : ligne) {
                Coord c = piece.getCoord();
                if((p.isCoupValid(c, this)))
                    allClassicMoves.add(c);
            }
        }
        return allClassicMoves;
    }

    /**
     * Permet de deplacer une pièce selon les coordonnées en paramètre.
     * la piece qui se deplace ecrase la piece à la case d'arrivé
     * et son ancienne case devient une case VIDE.
     * @param cS la coordonnée de départ
     * @param cF la coordonnée d'arrivée
     */
    private void deplacer(Coord cS, Coord cF){
        assert(inBound(cS) && inBound(cF));
        IPiece p = getPiece(cS);
        changePiece(cS, fabrique.getPiece('V', cS));  //piece Vide à la coordonnée de depart
        changePiece(cF, p);
        p.newPos(cF);
    }

    /** deplace une piece selon le format de coup dans les echecs ("A2A3")*/
    public void deplacer(String coup){
        int mid = coup.length() / 2;
        Coord cS = new Coord(coup.substring(0, mid));
        Coord cF = new Coord(coup.substring(mid));
        deplacer(cS, cF);
    }

    /** change une pièce sur l'échiquier */
    private void changePiece(Coord c, IPiece p){
        echiquier[c.getX()][c.getY()] = p;
    }

    /**
     * Renvoie une liste comportant toutes les pieces de l'echiquier
     * de couleur demandée.
     * @param couleur la couleur demandée
     * @return la liste de pieces
     */
    public LinkedList<IPiece> getPieceFromColor(Couleur couleur){
        LinkedList<IPiece> pieces = new LinkedList<>();
        for(IPiece[] ligne : echiquier)
            for(IPiece p: ligne){
                if(Couleur.isRightColor(p, couleur))
                    pieces.add(p);
            }
        return pieces;
    }

    /** Renvoie la pièce aux coordonnées en param */
    public IPiece getPiece(Coord c){
        return echiquier[c.getX()][c.getY()];
    }

    /** renvoie le joueur selon sa couleur */
    public IChessJoueur getJoueur(Couleur c){
        switch (c){
            case BLANC : return j1;
            case NOIR : return j2;
            default: return null;
        }
    }

    /** Vérifie si une case aux coordonnées est vide */
    public boolean estVide(Coord c){
        return echiquier[c.getX()][c.getY()].estVide();
    }

    /** Renvoie si une coordonnée est en dehors de l'echiquier ou non */
    public static boolean inBound(Coord c){
        int x = c.getX(), y = c.getY();
        return (x >= 0 && x < LIGNE) && (y >= 0 && y < COLONNE);
    }

    /**
     * Recherche une piece sensible sur l'echiquier selon la couleur
     * @param couleur la couleur du roi (NOIR ou BLANC)
     * @return la coordonnées, null si aucune piece n'a été trouvé
     */
    public Coord locateSensiblePiece(Couleur couleur) {
        for(IPiece[] ligne : echiquier)
            for(IPiece p : ligne)
                if(p.estSensible() && Couleur.isRightColor(p, couleur))
                    return p.getCoord();
        return null;
    }

    /**
     * renvoie une exception si il y a pas de roi de couleur donné dans l'echiquier
     * @throws NoSensiblePieceException aucun roi trouvé
     */
    private void SensibleError() throws NoSensiblePieceException {
        if(locateSensiblePiece(Couleur.BLANC) == null &&
            locateSensiblePiece(Couleur.NOIR) == null)
            throw new NoSensiblePieceException();
    }

    /**
     * vérifie si une piece peut etre promu
     * https://www.apprendre-les-echecs-24h.com/blog/debuter-aux-echecs/promotion-aux-echecs/
     * @param couleur la couleur des pièces à vérifier
     */
    public void checkForPromote(Couleur couleur){
        int x = couleur == Couleur.BLANC ? 0 : LIGNE - 1, y = 0;
        for (IPiece p : echiquier[x])
            echiquier[x][y++] = p.autoPromote();
    }

    /** Créer une chaîne de caractères comportant l'ensemble de l'échiquier. */
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