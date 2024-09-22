package ete.gamecontroller;

import ete.controles.Controles;
import ete.monde.Monde;
import ete.vue.Renderer;
import javafx.animation.AnimationTimer;

/**
 * Classe qui gère la boucle de jeu
 */
public class GameController {

    /**
     * Le monde qui gère la logique du jeu
     */
    private Monde monde;

    /**
     * Le renderer qui gère l'affichage
     */
    private Renderer renderer;

    /**
     * Les controles qui gèrent les entrées du joueur
     */
    private Controles controles;

    /**
     * Constructeur de GameController
     * @param monde le monde
     * @param renderer le renderer
     * @param controles les controles
     */
    public GameController(Monde monde, Renderer renderer, Controles controles){
        this.monde = monde;
        this.renderer = renderer;
        this.controles = controles;
    }

    /**
     * Démarre la boucle de jeu
     */
    public void startGame(){
        // Crée une boucle de jeu (Game Loop) en utilisant AnimationTimer
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                //Gestion des entrées du joueur
                controles.gestionControles();

                // Met à jour la logique du jeu
                monde.update();

                // Dessine le contenu du jeu
                renderer.render();
            }
        };
        // Démarre la boucle de jeu
        gameLoop.start();
    }
}
