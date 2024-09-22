package ete.monde;

import ete.attaques.EpeeCourte;

import static ete.outils.Constantes.*;

/**
 * Classe représentant le héros du jeu, avec ses données et ses animations (hérite de Personnage)
 */
public class Heros extends Personnage{

    /**
     * Mode dieu
     */
    public static boolean godMode = false;

    /**
     * Constructeur du héros (appelle le constructeur de Personnage, et initialise l'argent à 0)
     */
    public Heros() {
        super(URL_SPRITE_HEROS, 10, BASIC_SPEED_PLAYER, new EpeeCourte(), "Heros", 1000);
    }

    public void reinitialiser(){
        super.reinitialiser(10, BASIC_SPEED_PLAYER, new EpeeCourte());
    }

    @Override
    public void perdPv(int degat) {
        if(!godMode) {
            super.perdPv(degat);
        }
    }

    /**
     * Ajoute un coup à la liste des coups et lance l'animation d'attaque
     */
    @Override
    public void ajouterCoup() {
        if(canAttack()) {
            super.ajouterCoup();
            this.startAnimation(ETAT_ANIMATION_ATTAQUE);
        }
    }
}