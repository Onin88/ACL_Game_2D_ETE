package ete.attaques;

import static ete.outils.Constantes.TIME_ANIMATION;

/**
 * Classe d'une épée courte qui hérite de la classe Attaque
 */
public class EpeeCourte extends Attaque{
    private static final int DEGATS = 1;
    private static final int PORTEE = 25;
    private static final int LARGEUR = 20;
    private static final int VITESSE = TIME_ANIMATION;
    private static final double COOLDOWN_SECONDES = 1;
    private static final int COOLDOWN = (int) (COOLDOWN_SECONDES*1000);
    private static final String NOM = "EpeeCourte";


    /**
     * Constructeur d'une épée courte
     */
    public EpeeCourte() {
        super(DEGATS, PORTEE, LARGEUR, VITESSE, COOLDOWN, NOM);
        urlSprite = "/sprites/epee_courte/";
    }
}
