package ete.controles;

import ete.monde.Monde;
import ete.monde.SalleMonstre;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

import static ete.outils.Constantes.*;

/**
 * Classe représentant les contrôles du jeu (déplacements, attaques, etc...)
 */
public class Controles {

    /**
     * Le monde du jeu
     */
    private Monde monde;

    /**
     * Ensemble des touches actuellement enfoncées
     */
    private static final Set<KeyCode> keysCurrentlyDown = new HashSet<>();

    /**
     * Constructeur de la classe Controles (affection du monde)
     * @param m Le monde
     */
    public Controles(Monde m) {
        this.monde = m;
    }

    /**
     * Gère les déplacements du joueur en fonction des touches enfoncées
     */
    public void gestionControles() {
        if(!monde.estDansUnMenu()) {
            //Gestion des déplacements du joueur

            // HAUT
            if (isDown(CONTROLE_HAUT) || isDown(CONTROLE_HAUT2)) {
                monde.deplacerHeros(0, -monde.getHeros().getVitesse());
                monde.getHeros().setLastDirection(DIRECTION_HAUT);
            }
            // BAS
            if (isDown(CONTROLE_BAS) || isDown(CONTROLE_BAS2)) {
                monde.deplacerHeros(0, monde.getHeros().getVitesse());
                monde.getHeros().setLastDirection(DIRECTION_BAS);
            }
            // DROITE
            if (isDown(CONTROLE_DROITE) || isDown(CONTROLE_DROITE2)) {
                monde.deplacerHeros(monde.getHeros().getVitesse(), 0);
                monde.getHeros().setLastDirection(DIRECTION_DROITE);
            }
            // GAUCHE
            if (isDown(CONTROLE_GAUCHE) || isDown(CONTROLE_GAUCHE2)) {
                monde.deplacerHeros(-monde.getHeros().getVitesse(), 0);
                monde.getHeros().setLastDirection(DIRECTION_GAUCHE);
            }


            // Gestion de la vitesse du joueur en fonction de la touche de sprint
            if (isDown(CONTROLE_SPRINT)) {
                monde.getHeros().setVitesse(BASIC_SPEED_PLAYER * (float) 2);
            } else {
                monde.getHeros().setVitesse(BASIC_SPEED_PLAYER);
            }


            // Gestions de l'attaque principale
            if (isDown(CONTROLE_TIR)) {
                if (!monde.getHeros().haveCoups()) {
                    monde.getHeros().ajouterCoup();
                }
            }


            // Debug : reset position
            if(isDown(KeyCode.R)){
                ((SalleMonstre) monde.getCurrentSalle()).clearMonstres();
            }


            // Touche d'interaction E
            if (isDown(KeyCode.E)) {
                monde.reinitialiserMonde();
            }
        } else {

            // Clear les touches quand on est dans un menu
            keysCurrentlyDown.clear();
        }
    }

    /**
     * Ajoute une touche à la liste des touches actuellement enfoncées
     * @param keyCode La touche à ajouter
     */
    public void setKeysCurrentlyDown(KeyCode keyCode) {
        keysCurrentlyDown.add(keyCode);
    }

    /**
     * Supprime une touche de la liste des touches actuellement enfoncées
     * @param keyCode La touche à supprimer
     */
    public void removeKeysCurrentlyDown(KeyCode keyCode) {
        keysCurrentlyDown.remove(keyCode);
    }

    /**
     * Vérifie si une touche est actuellement enfoncée
     * @param keyCode La touche à vérifier dans la liste des touches actuellement enfoncées
     * @return true si la touche est actuellement enfoncée, false sinon
     */
    public boolean isDown(KeyCode keyCode) {
        return keysCurrentlyDown.contains(keyCode);
    }
}
