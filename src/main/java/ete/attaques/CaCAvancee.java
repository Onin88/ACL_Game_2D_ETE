package ete.attaques;

public class CaCAvancee extends Attaque{

    private static int DEGATS = 2; // en pv
    private static int PORTEE = 20; // en pixel
    private static int LARGEUR = 15; // en pixel
    private static int VITESSE = 100000; // en ms
    private static int COOLDOWN = 2000; // en ms
    private static String NOM = "CaCAvancee";

    /**
     * Constructeur du Corps à Corps
     */
    public CaCAvancee() {
        super(DEGATS, PORTEE, LARGEUR, VITESSE, COOLDOWN, NOM);
        urlSprite = "/sprites/cac.png";
    }
}
