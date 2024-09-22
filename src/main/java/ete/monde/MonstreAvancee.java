package ete.monde;

import ete.attaques.CaCAvancee;
import ete.outils.AStar;
import ete.outils.Node;
import javafx.application.Platform;

import java.util.List;

import static ete.outils.Constantes.*;

/**
 * Classe représentant un monstre avancée (Un monstre avec des déplacements avancés, avec recherche du héros, et qui a beaucoup de PV et d'attaque) (hérite de Monstre)
 */
public class MonstreAvancee extends Monstre {

    private AStar aStar;

    /**
     * Constructeur du monstre avancée (appelle le constructeur de Monstre avec les paramètres correspondants)
     */
    public MonstreAvancee() {
        super(URL_SPRITE_MONSTRE_AVANCEE, 3, BASIC_SPEED_MONSTRE_AVANCEE, new CaCAvancee(), "MonstreAvancee", 300);
    }

    @Override
    public double[] gestionDeplacement(double herosX, double herosY, int[][] mapArray) {
        // On initialise les noeuds
        /**
         * Noeud initial et final pour l'algorithme A*
         */
        Node initialNode = new Node((int) this.getPosX() / TILE_SIZE, (int) this.getPosY() / TILE_SIZE);
        Node finalNode = new Node((int) herosX / TILE_SIZE, (int) herosY / TILE_SIZE);

        // On initialise les lignes et colonnes
        /**
         * Nombre de lignes et de colonnes de la map
         */
        int lignes = mapArray.length;
        int colonnes = mapArray[0].length;

        // On initialise l'algorithme A*
        aStar = new AStar(lignes, colonnes, initialNode, finalNode);

        // On définit les obstacles
        aStar.setBlocks(mapArray);

        // On trouve le chemin
        List<Node> path = aStar.findPath();

        // On initialise le déplacement
        double[] deplacement = new double[]{0, 0};

        // On vérifie si le monstre peut se déplacer et si c'est le cas on génère le déplacement en fonction du chemin trouvé
        if(canDeplacer()) deplacement = deplacement(path);

        // On retourne le déplacement
        return (deplacement);
    }

    /**
     * Permet de déplacer le monstre en fonction du chemin trouvé
     * @param chemin Le chemin trouvé par l'algorithme A*
     * @return un tableau de double contenant les coordonnées x et y du déplacement
     */
    private double[] deplacement(List<Node> chemin) {

        // On initialise le déplacement
        double[] deplacement = new double[2];

        // On vérifie si le chemin n'est pas vide
        if (chemin.size() > 1) {

            // On affecte le déplacement
            deplacement[0] = ((chemin.get(1).getRow() * TILE_SIZE) - this.getPosX());
            deplacement[1] = ((chemin.get(1).getCol() * TILE_SIZE) - this.getPosY());

        // Sinon on ne bouge pas
        } else {
            deplacement[0] = 0;
            deplacement[1] = 0;
        }

        //affichageConsole(chemin);

        // On retourne le déplacement
        return deplacement;
    }
}
