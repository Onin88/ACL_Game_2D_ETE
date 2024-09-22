package ete.attaques;

/**
 * Classe pour les attaques au corps à corps qui hérite de Attaque
 */
public class CaCBasique extends Attaque{
    private static int DEGATS = 1; // en pv
    private static int PORTEE = 10; // en pixel
    private static int LARGEUR = 10; // en pixel
    private static int VITESSE = 100000; // en ms
    private static int COOLDOWN = 1000; // en ms
    private static String NOM = "CaCBasique";

    /**
     * Constructeur du Corps à Corps
     */
    public CaCBasique() {
        super(DEGATS, PORTEE, LARGEUR, VITESSE, COOLDOWN, NOM);
        urlSprite = "/sprites/cac.png";
    }
}
