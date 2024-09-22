package ete.monde;

import ete.attaques.Attaque;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static ete.outils.Constantes.*;
import static ete.outils.Constantes.DIRECTION_DROITE;
import static java.lang.Math.abs;

/**
 * Classe abstraite représentant un monstre avec un comportement de déplacement (hérite de Personnage)
 */
public abstract class Monstre extends Personnage {

    /**
     * Cooldown de déplacement du monstre
     */
    private int cooldownDeplacement;

    /**
     * Dernier déplacement du monstre
     */
    private long lastDeplacement;

    /**
     * Constructeur du monstre (appelle le constructeur de Personnage, et initialise le cooldown de déplacement)
     * @param urlSprite url du sprite du monstre
     * @param pv points de vie du monstre
     * @param vitesse vitesse du monstre
     * @param attaque attaque du monstre
     * @param nomMonstre nom du monstre
     * @param cooldownDeplacement cooldown de déplacement du monstre
     */
    public Monstre(String urlSprite, int pv, float vitesse, Attaque attaque, String nomMonstre, int cooldownDeplacement) {
        super(urlSprite, pv, vitesse, attaque, nomMonstre, 500);
        this.cooldownDeplacement = cooldownDeplacement;
        this.lastDeplacement = System.currentTimeMillis();
    }

    /**
     * Gère le déplacement du monstre (en fonction de son comportement)
     * @param herosX position x du héros
     * @param herosY position y du héros
     * @return un tableau de double contenant les coordonnées x et y du déplacement
     */
    public abstract double[] gestionDeplacement(double herosX, double herosY, int[][] mapArray);

    /**
     * Gère l'animation du monstre (Hit ou en mouvement)
     */
    @Override
    protected void loadSprites() {
        // On définit le chemin des images
        String path = "/sprites/" + getNomPersonnage();

        // On déclare les directions
        ArrayList<String> directions = new ArrayList<>(List.of(DIRECTION_HAUT, DIRECTION_BAS, DIRECTION_GAUCHE, DIRECTION_DROITE));

        // Pour chaque direction
        for (String direction : directions) {

            // Pour chaque image de l'animation
            for (int i = 1; i <= NB_IMAGES_ANIMATION; i++) {

                // On ajoute à la liste des images statiques
                imagesIdle.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path + "_statique/" + direction + "/" + i + ".png"))));

                // On ajoute à la liste des images de dégats
                imagesHit.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(path + "_degat/" + direction + "/" + i + ".png"))));
            }
        }
    }

    /**
     * Déplace le monstre et change sa direction
     * @param x Coordonnée X à ajouter
     * @param y Coordonnée Y à ajouter
     */
    @Override
    public void deplacer(double x, double y) {
        // On met à jour la dernière direction du personnage en fonction de son déplacement (la direction est celle qui a le plus de déplacement en abscisse ou en ordonnée)
        lastDirection = abs(x) >= abs(y) ? (x > 0 ? DIRECTION_DROITE : DIRECTION_GAUCHE) : (y > 0 ? DIRECTION_BAS : DIRECTION_HAUT);

        // On met à jour le cooldown de déplacement
        lastDeplacement = System.currentTimeMillis();

        // On déplace le personnage
        this.posX += x;
        this.posY += y;
    }

    /**
     * Gère l'animation du monstre (Hit ou en mouvement)
     */
    @Override
    public void run() {
        // Boucle infinie
        while (getPv() > 0) {

            // On attend 50ms sauf si le monstre prend des dégats
            try {
                Thread.sleep(TIME_BETWEEN_ANIMATION_MONSTRE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // On vérifie que les images sont bien chargées
            if (imagesHit.size() >= 28 && imagesIdle.size() >= 28) {

                // On réinitialise l'image
                Image image = null;

                // On change l'image du héros en fonction de son état
                switch (etatAnimation) {
                    case "Statique":
                        image = getNextImage(getImage(), getNextImageDirection(imagesIdle));
                        break;
                    case "Degat":
                        image = getNextImage(getImage(), getNextImageDirection(imagesHit));
                        break;
                }
                // Si le temps de l'animation est dépassé, on remet le héros en mode statique
                if (timeAnimation < System.currentTimeMillis())
                    etatAnimation = "Statique";

                // On set l'image
                setImage(image);
            }
        }
    }

    /**
     * Vérifie si le monstre peut se déplacer
     * @return true si le monstre peut se déplacer, false sinon
     */
    public boolean canDeplacer() {
        return System.currentTimeMillis() - lastDeplacement > cooldownDeplacement;
    }
}