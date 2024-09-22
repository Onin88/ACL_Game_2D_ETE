package ete.monde;

import javafx.scene.image.Image;
import ete.attaques.CaCBasique;

import java.util.ArrayList;
import java.util.Objects;

import static ete.outils.Constantes.BASIC_SPEED_MONSTRE;
import static ete.outils.Constantes.URL_SPRITE_MONSTRE_BASIQUE;

/**
 * Classe représentant un monstre basique (Un monstre avec des déplacements basiques, qui va en direction du héros, et qui a peu de PV et d'attaque) (hérite de Monstre)
 */
public class MonstreBasique extends Monstre{

    /**
     * Constructeur du monstre basique (appelle le constructeur de Monstre avec les paramètres correspondants)
     */
    public MonstreBasique(){
        super(URL_SPRITE_MONSTRE_BASIQUE, 1, BASIC_SPEED_MONSTRE, new CaCBasique(), "MonstreBasique",0);
    }

    @Override
    public double[] gestionDeplacement(double herosX, double herosY, int[][] mapArray) {
        // Calculer la différence de position entre le monstre et le héros
        double diffX = herosX - getPosX();
        double diffY = herosY - getPosY();

        // Initialise le déplacement
        double newX = 0, newY = 0;

        // Se déplacer verticalement (vers le haut ou vers le bas)
        if (diffY > 0) {
            newY = vitesse / 2;
        } else if (diffY < 0) {
            newY = -vitesse / 2;
        }

        // Se déplacer horizontalement (à gauche ou à droite)
        if (diffX > 0) {
            newX = vitesse / 2;
        } else if (diffX < 0) {
            newX = -vitesse / 2;
        }

        // On retourne le déplacement (qui est la vitesse divisée par 2 pour éviter que le monstre ne se déplace trop vite, en fonction de la direction)
        return new double[]{newX, newY};
    }
}