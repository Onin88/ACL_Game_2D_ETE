package ete.attaques;

import javafx.geometry.Rectangle2D;

/**
 * Classe pour les attaques
 */
public abstract class Attaque {

    /**
     * Les dégats de l'attaque
     */
    protected int degats;

    /**
     * La portée de l'attaque (en px)
     */
    protected int portee;

    /**
     * La largeur de l'attaque (en px)
     */
    protected int largeur;

    /**
     * La vitesse de l'attaque (en ms)
     */
    private int vitesse;

    /**
     * Le temps de recharge de l'attaque (en ms)
     */
    private int cooldown;

    /**
     * Le nom de l'attaque
     */
    protected String nom;

    /**
     * Le gestionnaire de coups de l'attaque
     */
    protected GestionnaireCoups gestionnaireCoups;

    /**
     * Url du sprite de l'attaque
     */
    protected String urlSprite;

    /**
     * Coordonnées de l'attaque
     */
    protected Rectangle2D coordonnees;

    /**
     * Temps de la dernière attaque
     */
    protected long lastAttackTime;

    /**
     * Constructeur de l'attaque
     * @param degats Les dégats de l'attaque
     * @param portee La portée de l'attaque (en px)
     * @param largeur La largeur de l'attaque (en px)
     * @param vitesse La vitesse de l'attaque (en ms)
     * @param cooldown Le temps de recharge de l'attaque (en ms)
     * @param nom Le nom de l'attaque
     */
    public Attaque(int degats, int portee, int largeur, int vitesse, int cooldown, String nom) {
        this.degats = degats;
        this.portee = portee;
        this.largeur = largeur;
        this.vitesse = vitesse;
        this.cooldown = cooldown;
        this.nom = nom;
        this.gestionnaireCoups = new GestionnaireCoups();
        this.urlSprite = "";
        this.coordonnees = new Rectangle2D(0,0,portee,largeur);
    }

    public GestionnaireCoups getGestionnaireCoups() {
        return gestionnaireCoups;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getVitesse() {
        return vitesse;
    }

    public int getDegats() {
        return degats;
    }

    public String getNom() {
        return nom;
    }

    public String getUrlSprite() {
        return urlSprite;
    }

    public Rectangle2D getCoordonneesAttaque() {
        return coordonnees;
    }

    public void setLastAttackTime() {
        lastAttackTime = System.currentTimeMillis();
    }

    public boolean canAttack() {
        return System.currentTimeMillis() - lastAttackTime + vitesse > cooldown;
    }
}
